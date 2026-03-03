package com.sgt.expense_tracker.Controller;


import com.sgt.expense_tracker.Model.Transactions;
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
    public void bulkUpload(@RequestParam(name = "file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
        try {
            transactionService.readFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


