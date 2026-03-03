package com.sgt.expense_tracker.Repository;

import com.sgt.expense_tracker.Model.User;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(UserRepository.class);
    public String isLinkForEmailActive(String email){
//        logger.info("user is impatient");
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap("select token from resetPasswordTokens where email=? and usedYN=0 and current_timestamp()-expiryTime<=0", email);
//            logger.info(map);
            System.out.println(map.get("token"));

            return map.get("token").toString();
        } catch (EmptyResultDataAccessException e) {
            return "none";
        }

    }
    public void updateExpiry(LocalDateTime expiry,String token){
        jdbcTemplate.update("update resetPasswordTokens set expiryTime=? where token=?",expiry,token);
        logger.info("updated expiry");
    }
    public User checkIfEmailExists(String email) {
        try {
            User s = jdbcTemplate.queryForObject("select id,name,username,email,phone,activeYN,password from user where email=? and activeYN=1", (rs, rowNum) -> {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getLong("phone"));
                user.setPassword(rs.getString("password"));
                user.setActiveYN(rs.getInt("activeYN"));
                return user;
            }, email);
            return s;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User checkIfDuplicateUsernameExists(String username) {
        try {
            User s = jdbcTemplate.queryForObject("select id,name,username,email,phone,password,activeYN,password from user where username=? and activeYN=1;", (rs, rowNum) -> {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getLong("phone"));
                user.setPassword(rs.getString("password"));
                user.setActiveYN(rs.getInt("activeYN"));
                return user;
            }, username);
            return s;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public void register(String name, String username, String email, String password, long phone) {
        jdbcTemplate.update("insert into user(name,username,email,password,phone) values(?,?,?,?,?);", name, username, email, password, phone);
        logger.info("registered for now");
    }

    public User findByEmail(String email) {
        User u = jdbcTemplate.queryForObject("select * from user where email=? and activeYN=1", (rs, rowNum) -> {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getLong("phone"));
            user.setPassword(rs.getString("password"));
            user.setActiveYN(rs.getInt("activeYN"));
            return user;
        }, email);
//        logger.info("actual password: "+u.getPassword());
//        logger.info("given password: "+password);
        return u;
    }

    public void saveToken(String email,String token, LocalDateTime expiry) {
        jdbcTemplate.update("insert into resetPasswordTokens(email,token,expiryTime) values(?,?,?)",email, token,expiry);
    }

    public boolean  checkIfTokenIsValid(String token) {
        logger.info("repo pe toh aaya");
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap("select current_timestamp()-expiryTime as diff from resetPasswordTokens where token=? and usedYN=0", token);
//            logger.info(map);
            System.out.println(map.get("diff"));
            return Float.parseFloat(map.get("diff").toString()) <= 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
    public void changePassword(String password,String token){
        String email=getEmailFromToken(token);
        logger.info(email);
        jdbcTemplate.update("update user set password=? where email=?",password,email);
        logger.info("reset done");
    }
    public String getEmailFromToken(String token){
        Map<String, Object> map = jdbcTemplate.queryForMap("select email from resetPasswordTokens where token=?", token);
        return map.get("email").toString();
    }
    public void updateUsedToken(String token){
        jdbcTemplate.update("update resetPasswordTokens set usedYN=1 where token=?",token);
    }
}
