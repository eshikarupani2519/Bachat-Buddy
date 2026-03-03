package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Model.Transactions;
import com.sgt.expense_tracker.Repository.TransactionRepository;
import com.sgt.expense_tracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    public void create(Transactions transactions,String email){
        int user_id=userRepository.findByEmail(email).getId();
        double amount=transactions.getAmount();
        int category_id=transactions.getCategory_id();
        String notes=transactions.getNotes();
        LocalDate dateOfTransaction=transactions.getDateOfTransaction();
        transactionRepository.create(user_id,amount,category_id,dateOfTransaction,notes);
    }
    public List<Transactions> get(String email,String category,LocalDate start,LocalDate end,String type,String column,String direction,Long pageNo,Integer rowsPerPage){
        int user_id=userRepository.findByEmail(email).getId();
        return transactionRepository.get(user_id,category,start,end,type,column,direction,pageNo,rowsPerPage);
    }
    public void readFile(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        if(file.getOriginalFilename().endsWith(".csv")){
//            BufferedReader br=new BufferedReader(new InputStreamReader(file.getInputStream()));
//            System.out.println(br.readLine());
            Scanner sc=new Scanner(file.getInputStream());
            while (sc.hasNext()){
                System.out.println(sc.nextLine());
            }
        }
        else throw new RuntimeException("Please upload a csv file");
    }
}
