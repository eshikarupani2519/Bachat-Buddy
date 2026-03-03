package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Exceptions.*;
import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.slf4j.Logger;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok().body(Map.of("body", "Registered successfully"));
        } catch (InvalidEmailException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body", "Email is invalid"));
        } catch (EmailAlreadyExistsException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body", "Email already exists"));
        } catch (UsernameAlreadyExistsException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body", "Username already exists"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body", "Some error occurred"));
        }
    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) throws EmailNotRegisteredException, WrongPasswordException {
//        try {
//            int id=userService.login(user);
//            return ResponseEntity.ok().body(Map.of("body", id));
//
//        } catch (EmailNotRegisteredException | WrongPasswordException e) {
//            return ResponseEntity.ok().body(Map.of("body", e.getMessage()));
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return ResponseEntity.badRequest().body(Map.of("body", "Some error occurred"));
//
//        }
//    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody User user) {
        try {
            userService.resetPassword(user);
            return ResponseEntity.ok().body(Map.of("body", "Password reset email sent"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));
        }
    }

    @PatchMapping("/changePassword/{token}")
    public ResponseEntity<Map<String, String>> changePassword(@PathVariable(name = "token")String token,@RequestBody User user) {
        try {
            logger.info("controller pe toh aaya");
            userService.changePassword(user, token);
            return ResponseEntity.ok().body(Map.of("body", "Password reset successfully"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));
        }
    }
    @PostMapping("/validateToken/{token}")
    public ResponseEntity<Map<String,String>> validateToken(@PathVariable(name = "token") String token){
        try {
            userService.validateToken(token);
            return ResponseEntity.ok().body(Map.of("body","success"));
        } catch (TokenExpiredException e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }
}

