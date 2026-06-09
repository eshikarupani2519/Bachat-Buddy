package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Service.AIService;
import com.sgt.expense_tracker.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {
    @Autowired
    DashboardService dashboardService;
    @Autowired
    AIService aiService;
    @GetMapping("/getDashboard")
    public ResponseEntity<Map<String, ?>> get(Authentication auth){
        try{
             Map<String, Object> dash= dashboardService.get(auth.getName());
            return ResponseEntity.ok().body(Map.of("body",dash));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }
    @PostMapping("/analyze")
    @SuppressWarnings("unchecked") // Suppresses warnings when casting generic map lists safely
    public ResponseEntity<Map<String, Object>> getDashboardAiInsights(@RequestBody Map<String, Object> payload) {
        try {
            // 1. Safely extract the raw nested lists from your incoming Angular map payload
            // Using empty ArrayLists as fallbacks to prevent NullPointerExceptions
            List<Map<String, Object>> categorywiseExpense = (List<Map<String, Object>>) payload.getOrDefault("categorywiseExpense", new ArrayList<>());
            List<Map<String, Object>> categorywiseIncome = (List<Map<String, Object>>) payload.getOrDefault("categorywiseIncome", new ArrayList<>());
            List<Map<String, Object>> monthwiseExpense = (List<Map<String, Object>>) payload.getOrDefault("monthwiseExpense", new ArrayList<>());
            List<Map<String, Object>> monthwiseIncome = (List<Map<String, Object>>) payload.getOrDefault("monthwiseIncome", new ArrayList<>());

            // Angular sends 'savingData'. We map it into both our comparison and dynamics list arguments
            List<Map<String, Object>> savingData = (List<Map<String, Object>>) payload.getOrDefault("savingData", new ArrayList<>());

            // 2. Pass them directly to your loose-typed Map service function
            // (Passing placeholder/empty values for notes and categories since they aren't utilized in your template parameters yet)
            Map<String, Object> insightsResult = aiService.getDashboardInsights(
                    "",                 // String notes
                    new ArrayList<>(),  // List<Category> categories
                    categorywiseExpense,
                    categorywiseIncome,
                    monthwiseExpense,
                    monthwiseIncome,
                    savingData,         // incomeVsExpense
                    savingData          // savingsDynamic
            );

            // 3. Return the generated key-value insight string map back to your UI
            return ResponseEntity.ok(insightsResult);

        } catch (Exception e) {
            // Strict error boundary fallback maps
            Map<String, Object> errorMap = Map.of(
                    "error", "Failed to compile runtime analytical streams",
                    "details", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }
}
