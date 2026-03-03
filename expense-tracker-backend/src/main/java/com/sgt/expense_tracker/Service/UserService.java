package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Exceptions.*;
import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailSendService emailSendService;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public void register(User user) throws InvalidEmailException, EmailAlreadyExistsException, UsernameAlreadyExistsException {


        String email = user.getEmail();

        String name = user.getName();
        String username = user.getUsername();
        String password = user.getPassword();
        Long phone = user.getPhone();
        if (!isEmailValid(email))
            throw new InvalidEmailException();
        if (checkIfEmailExists(email))
            throw new EmailAlreadyExistsException();
        if (checkIfDuplicateUsernameExists(username))
            throw new UsernameAlreadyExistsException();
        String hashedPassword = encodePassword(password);
        userRepository.register(name, username, email, hashedPassword, phone);
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public boolean isEmailValid(String email) {
        String regex = "^[A-Za-z0-9._+-]+@[A-Za-z0-9._+-]+\\.[a-z]{2,}$";
        if (email == null) return false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkIfEmailExists(String email) {
        User u = userRepository.checkIfEmailExists(email);
        return u != null;
    }

    public boolean checkIfDuplicateUsernameExists(String username) {
        User u = userRepository.checkIfDuplicateUsernameExists(username);
        return u != null;
    }

    public int login(User user) throws EmailNotRegisteredException, WrongPasswordException {
        String email = user.getEmail();
        String password = user.getPassword();
        if (isEmailValid(email)) {
            if (checkIfEmailExists(email)) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                User u = userRepository.findByEmail(email);
                if (!encoder.matches(password, u.getPassword())) {
                    logger.info(password);
                    logger.info(u.getPassword());
                    throw new WrongPasswordException();

                }
                else return u.getId();
            } else throw new EmailNotRegisteredException();
        }
        return -1;
    }

    public void resetPassword(User user) throws EmailNotRegisteredException {
        String email = user.getEmail();
        if (checkIfEmailExists(email)) {
           String token= userRepository.isLinkForEmailActive(email);
if(token.equals("none")) {
    token = generateToken(email);
    LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);
    userRepository.saveToken(email, token, expiry);
}
else{
    LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);
    userRepository.updateExpiry(expiry,token);
}
            if(userRepository.checkIfTokenIsValid(token))
            emailSendService.sendEmail(email, token);
        } else throw new EmailNotRegisteredException();
    }

    public String generateToken(String email) {
        String token=UUID.randomUUID().toString();
        logger.info(token);
        return token;
    }

    public void changePassword(User user, String token) throws TokenExpiredException{
//        String email = user.getEmail();
        String password = user.getPassword();
        logger.info(password);
        logger.info("service pe toh aaya");
//        System.out.println(userRepository.checkIfTokenIsValid(token, email));
        if(userRepository.checkIfTokenIsValid(token)){
            userRepository.changePassword(encodePassword(password),token);
            userRepository.updateUsedToken(token);
        }
        else throw new TokenExpiredException();
    }
    public void validateToken(String token) throws TokenExpiredException{
        if( !userRepository.checkIfTokenIsValid(token))
            throw new TokenExpiredException();
    }
}
