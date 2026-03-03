package com.sgt.expense_tracker.Mapper;

import com.sgt.expense_tracker.Model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {


    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category=new Category();
        category.setUser_id(rs.getInt("user_id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setIcon_url(rs.getString("icon_url"));
        category.setTransaction_type(rs.getString("transaction_type"));
        category.setId(rs.getInt("id"));
        category.setActiveYN(rs.getInt("activeYN"));
        return category;
    }
}
