package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardController {
    @Autowired
    DashboardService dashboardService;
    @GetMapping("/getDashboard")
    public ResponseEntity<Map<String, ?>> get(Authentication auth){
        try{
             Map<String, Object> dash= dashboardService.get(auth.getName());
            return ResponseEntity.ok().body(Map.of("body",dash));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }
}
