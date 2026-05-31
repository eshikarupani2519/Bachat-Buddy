package com.sgt.expense_tracker.Repository;

import com.sgt.expense_tracker.Mapper.CategoryMapper;
import com.sgt.expense_tracker.Model.Category;
import com.sgt.expense_tracker.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean checkIfUserIdExists(int id){
        try {
            User u = jdbcTemplate.queryForObject("select * from user where id=? and activeYN=1", (rs, rowNum) -> {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getLong("phone"));
                user.setPassword(rs.getString("password"));
                user.setActiveYN(rs.getInt("activeYN"));
                return user;
            }, id);
            return true;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }

    }
    public void addCategory(int user_id,String name,String description,String icon_url,String transaction_type){
        jdbcTemplate.update("insert into category(user_id,name,description,icon_url,transaction_type) values(?,?,?,?,?)",user_id,name,description,icon_url,transaction_type);
    }
    public List<Category> getCategories(int user_id){
        return jdbcTemplate.query("select * from category where user_id=? and activeYN=1",new CategoryMapper(),user_id);
    }
    public List<Category> getCategoryById(int id){
        return jdbcTemplate.query("select * from category where id=? and activeYN=1",new CategoryMapper(),id);
    }
    public void editCategory(int id,String name,String description,String icon_url,String transaction_type,int user_id){
        jdbcTemplate.update("update category set name=?,description=?,icon_url=?,transaction_type=? where id=? and user_id=?",name,description,icon_url,transaction_type,id,user_id);
    }
    public void deleteCategory(int id,int user_id){
        jdbcTemplate.update("update category set activeYN=0 where id=? and user_id=?",id,user_id);
    }
    public int getCategoryByName(int user_id,String name,String type){
        try {
            Category cat= jdbcTemplate.queryForObject("select * from category where user_id=? and name=? and transaction_type=? and activeYN=1",new CategoryMapper(),user_id,name,type);
            return cat.getId();
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }

    }
}
