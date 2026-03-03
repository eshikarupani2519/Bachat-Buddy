package com.sgt.expense_tracker.Mapper;

import com.sgt.expense_tracker.Model.Transactions;

import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TransactionMapper implements RowMapper {
    @Override
    public @Nullable Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transactions transactions=new Transactions();
        transactions.setUser_id(rs.getInt("user_id"));
        transactions.setAmount(rs.getDouble("amount"));
        transactions.setCategory_id(rs.getInt("category_id"));
        transactions.setDateOfTransaction(LocalDate.parse(rs.getString("dateOfTransaction")));
        transactions.setNotes(rs.getString("notes"));
        transactions.setId(rs.getInt("id"));
        transactions.setActiveYN(rs.getInt("activeYN"));
        transactions.setTransaction_type(rs.getString("transaction_type"));
        transactions.setCategory_name(rs.getString("name"));
        return transactions;
    }
}
