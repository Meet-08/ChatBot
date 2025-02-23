package com.meet.aibot.controller;

import com.meet.aibot.dto.LoginRequest;
import com.meet.aibot.dto.LoginResponse;
import com.meet.aibot.dto.RegistrationRequest;
import com.meet.aibot.dto.RegistrationResponse;
import com.meet.aibot.models.User;
import com.meet.aibot.service.UserService;
import com.meet.aibot.utils.CookieUtil;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserUtil userUtil;

    @PostMapping("/signup")
    public ResponseEntity<User> userSignUp(@Valid @RequestBody RegistrationRequest request, HttpServletResponse res) {
        try {
            RegistrationResponse registrationResponse = userService.registerUser(request);

            // Create a cookie with the JWT token from the service layer
            Cookie cookie = CookieUtil.createCookie("auth_token",
                    registrationResponse.getToken(),
                    24 * 60 * 60,  // maxAge is in seconds
                    true,
                    false,
                    "/");
            res.addCookie(cookie);

            return new ResponseEntity<>(registrationResponse.getUser(), HttpStatus.OK);
        } catch (Exception e) {
            // Log the error using a proper logger in production code
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@Valid @RequestBody LoginRequest request, HttpServletResponse res) {
        try {
            // Delegate the user verification and token generation to the service layer
            LoginResponse loginResponse = userService.loginUser(request);

            // Create a cookie to hold the JWT token
            Cookie cookie = CookieUtil.createCookie("auth_token",
                    loginResponse.getToken(),
                    24 * 60 * 60,
                    true,
                    false,
                    "/");
            res.addCookie(cookie);

            return new ResponseEntity<>(loginResponse.getUser(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> userLogout(HttpServletResponse res) {
        res.addCookie(CookieUtil.deleteCookie("auth_token"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/auth-status")
    public ResponseEntity<User> authStatus(HttpServletRequest req) {
        User user = userUtil.getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
