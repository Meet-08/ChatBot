package com.meet.aibot.controller;

import com.meet.aibot.dto.LoginRequest;
import com.meet.aibot.dto.RegistrationRequest;
import com.meet.aibot.models.User;
import com.meet.aibot.utils.CookieUtil;
import com.meet.aibot.utils.JwtUtil;
import com.meet.aibot.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@Valid @RequestBody RegistrationRequest request, HttpServletResponse res) {
        try {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            String token = jwtUtil.generateToken(request.getEmail(), user.getId());
            Cookie cookie = CookieUtil.createCookie("auth_token",
                    token,
                    24 * 60 * 60 * 1000,
                    true,
                    false,
                    "/");
            res.addCookie(cookie);
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(401));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@Valid @RequestBody LoginRequest  request, HttpServletResponse res) {
        try {
            String id = userService.verifyUser(request.getEmail(), request.getPassword());
            String token = "";
            if (id != null){
                token = jwtUtil.generateToken(request.getEmail(), id);
                // Create a cookie to hold the JWT
//                Cookie cookie = new Cookie("auth_token", token);
//                cookie.setHttpOnly(true);
//                cookie.setSecure(false);   // Use true in production when using HTTPS
//                cookie.setPath("/");
//                cookie.setMaxAge(24 * 60 * 60 * 1000);
                Cookie cookie = CookieUtil.createCookie("auth_token",
                                                        token,
                                                       24 * 60 * 60 * 1000,
                                                       true,
                                                        false,
                                                        "/");
                res.addCookie(cookie);
            }else {
                throw new RuntimeException("User not found");
            }
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
