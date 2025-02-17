package com.meet.aibot.controller;

import com.meet.aibot.dto.LoginRequest;
import com.meet.aibot.dto.RegistrationRequest;
import com.meet.aibot.models.User;
import com.meet.aibot.utils.CookieUtil;
import com.meet.aibot.utils.JwtUtil;
import com.meet.aibot.service.UserService;
import com.meet.aibot.utils.UserUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;

    @PostMapping("/signup")
    public ResponseEntity<User> userSignUp(@Valid @RequestBody RegistrationRequest request, HttpServletResponse res) {
        System.out.println(request);
        try {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setChats(new ArrayList<>());
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
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@Valid @RequestBody LoginRequest request, HttpServletResponse res) {
        try {

            User user = userService.verifyUser(request.getEmail(), request.getPassword());
            String id = user.getId();
            user.setPassword(null);
            String token = "";
            if (id != null) {
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
            } else {
                throw new RuntimeException("User not found");
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/auth-status")
    public ResponseEntity<User> authStatus(HttpServletRequest req) {
        // Fetch the corresponding User from your service
        User user = userUtil.getUser();

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        user.setPassword(null);
        return ResponseEntity.ok(user);
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
