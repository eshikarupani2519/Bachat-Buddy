package com.sgt.expense_tracker.Controller;


import com.sgt.expense_tracker.Model.FileResponse;
import com.sgt.expense_tracker.Model.Transactions;
import com.sgt.expense_tracker.Repository.CategoryRepository;
import com.sgt.expense_tracker.Repository.UserRepository;
import com.sgt.expense_tracker.Service.AIService;
import com.sgt.expense_tracker.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    AIService aiService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody Transactions transactions, Authentication auth) {
        try {
            transactionService.create(transactions, auth.getName());
            return ResponseEntity.ok().body(Map.of("body", "Inserted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> get(Authentication auth, @RequestParam(name = "category",required = false) String category, @RequestParam(name = "start",required = false) LocalDate start, @RequestParam(name = "end",required = false) LocalDate end,@RequestParam(name = "type",required = false) String type,
     @RequestParam(name = "column",defaultValue = "dateOfTransaction") String column,@RequestParam(name = "direction",defaultValue = "desc") String direction ,
      @RequestParam(name = "pageNo",required = false) Long pageNo,@RequestParam(name = "rowsPerPage",required = false ) Integer rowsPerPage ) {
        try {
            List<Transactions> transactions = transactionService.get(auth.getName(),category,start,end,type,column,direction,pageNo,rowsPerPage);
            
            return ResponseEntity.ok().body(Map.of("body", transactions));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));

        }
    }
    @PostMapping("/bulk-upload")
    public ResponseEntity<Map<String,?>> bulkUpload(@RequestParam(name = "file") MultipartFile file,Authentication auth){
        System.out.println(file.getOriginalFilename());
        try {
            List<FileResponse> fileResponses= transactionService.readFile(file,auth.getName());
            System.out.println(fileResponses);
            return ResponseEntity.ok().body(Map.of("body",fileResponses));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));
        }
    }
    @GetMapping("/ai/{note}")
    public String suggestCategory(@PathVariable(name = "note") String note,Authentication auth){
        return aiService.suggestCategory(note,categoryRepository.getCategories(userRepository.findByEmail(auth.getName()).getId()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,?>> getTransactionById(@PathVariable(name = "id") int id,Authentication auth){
        try {
            String email=auth.getName();
            Transactions t=transactionService.getTransactionById(id,email);

            return ResponseEntity.ok().body(Map.of("body",t));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));
        }

        }
        @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> editTransaction(@PathVariable(name = "id") int id,Authentication auth,@RequestBody Transactions body){
        try{
            transactionService.editTransaction(id,auth.getName(),body);
            return ResponseEntity.ok().body(Map.of("body","Updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));
        }
        }
        @PatchMapping("/{id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable(name = "id") int id,Authentication authentication){
            try{
                transactionService.delete(id,authentication.getName());
                return ResponseEntity.ok().body(Map.of("body","Deleted successfully"));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("body", e.getMessage()));
            }
        }

}

