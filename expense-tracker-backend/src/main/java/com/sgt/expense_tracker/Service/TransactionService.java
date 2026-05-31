package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Controller.TransactionController;
import com.sgt.expense_tracker.Model.FileResponse;
import com.sgt.expense_tracker.Model.Transactions;
import com.sgt.expense_tracker.Repository.CategoryRepository;
import com.sgt.expense_tracker.Repository.TransactionRepository;
import com.sgt.expense_tracker.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class TransactionService {
    Logger logger= LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AIService aiService;
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
    public List<FileResponse> readFile(MultipartFile file,String email) throws IOException {
        List<FileResponse> fileResponses=new ArrayList<>();
        double amount = 0;
        LocalDate date = LocalDate.parse("1901-01-01");
        System.out.println(file.getOriginalFilename());
        List<List<String>> errors=new ArrayList<>(new ArrayList<>());
        List<List<String>> fileList=new ArrayList<>();
        if(file.getOriginalFilename().endsWith(".csv")){
//            BufferedReader br=new BufferedReader(new InputStreamReader(file.getInputStream()));
//            System.out.println(br.readLine());
            Scanner sc=new Scanner(file.getInputStream());
//            below line is to skip headers
            sc.nextLine();
            int length=-1;

            while (sc.hasNext()){

                errors.add(new ArrayList<>());
                fileList.add(new ArrayList<>());
                length++;
                String row=sc.nextLine();
                String[] rowData=row.split(",");
                fileList.add(Arrays.asList(rowData));
                logger.info(rowData[0]+" "+rowData[1]+" "+rowData[2]+" "+rowData[3]+" "+rowData[4]);
//                for number format exception
                try {
                    if (Double.parseDouble(rowData[0]) < 0) {
                        errors.get(length).add("Invalid amount");


                    }
                    else amount = Double.parseDouble(rowData[0]);
                }
                catch (NumberFormatException e){
                        errors.get(length).add("Invalid amount");

                    }
                try{
                    if(date.isAfter(LocalDate.now()))errors.get(length).add("Invalid date");
                    else date= LocalDate.parse(rowData[3]);
                }
                catch (DateTimeParseException e){
                    errors.get(length).add("Invalid date");
                }
                String type="expense";

                    if(!rowData[4].equals("expense") && !rowData[4].equals("income"))
                        errors.get(length).add("Invalid transaction type");
                    else type=rowData[4];

                String categoryName=rowData[2];
                int category_id=-1;
                if(errors.get(length).isEmpty()) {
                    System.out.println("errors for "+length+"th record:"+errors.get(length));
                    if(!categoryName.trim().isEmpty() && categoryName!=null)
                     category_id = categoryRepository.getCategoryByName(userRepository.findByEmail(email).getId(), categoryName, type);
                    else {
                        categoryName=aiService.suggestCategory(rowData[1],categoryRepository.getCategories(userRepository.findByEmail(email).getId()));
                        logger.info("category suggested by ai:"+categoryName);
                    }
                    if (category_id >= 0) {
                        transactionRepository.create(userRepository.findByEmail(email).getId(), amount, category_id, date, rowData[1]);
                    }
                    else {
                        logger.info("category not found,creating one..");
                        categoryRepository.addCategory(userRepository.findByEmail(email).getId(), categoryName, null, null,type);
                        int category_id_new = categoryRepository.getCategoryByName(userRepository.findByEmail(email).getId(), categoryName, type);
                        transactionRepository.create(userRepository.findByEmail(email).getId(), amount, category_id_new, date, rowData[1]);
                    }
                }
                FileResponse f=new FileResponse();

                f.setAmount(rowData[0]);
                f.setCategory(categoryName);
                f.setDate(rowData[3]);
                f.setNotes(rowData[1]);
                f.setType(type);
                f.setErrors(errors.get(length));
                fileResponses.add(f);

                }




        }
        else throw new RuntimeException("Please upload a csv file");

        return fileResponses;
    }
    public Transactions getTransactionById(int id,String email){
        int user_id=userRepository.findByEmail(email).getId();
      return   transactionRepository.getTransactionById(id,user_id);
    }
    public void editTransaction(int id,String email,Transactions t){
        int user_id=userRepository.findByEmail(email).getId();
        int category_id=t.getCategory_id();
        double amount=t.getAmount();
        LocalDate date=t.getDateOfTransaction();
        String notes=t.getNotes();

        transactionRepository.editTransaction(id,user_id,amount,category_id,date,notes);
    }

    public void delete(int id, String email) {
        transactionRepository.delete(id,userRepository.findByEmail(email).getId());
    }
}
