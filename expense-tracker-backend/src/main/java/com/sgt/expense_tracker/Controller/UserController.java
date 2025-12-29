package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody User user){
        try {
            userService.register(user);
            return ResponseEntity.ok().body(Map.of("body","Registered successfully"));
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(Map.of("body","Some error occurred"));
        }
    }
}
