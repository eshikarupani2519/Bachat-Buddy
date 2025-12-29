package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void register(User user){
//check validity of email,already exists,hash password,call repo


    }
}
