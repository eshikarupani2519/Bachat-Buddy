package com.sgt.expense_tracker.Repository;

import com.sgt.expense_tracker.Model.CategoryWiseDashboardData;
import com.sgt.expense_tracker.Model.MonthwiseDashboardData;
import com.sgt.expense_tracker.Model.SavingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DashboardRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public Map<String,Object> get(int user_id){
        Map<String,Object> ans=new HashMap<>();
        List<CategoryWiseDashboardData> categorywiseExpense = jdbcTemplate.query("SELECT \n" +
                "    c.name, SUM(t.amount) AS amount\n" +
                "FROM\n" +
                "    category c\n" +
                "        LEFT JOIN\n" +
                "    transaction t ON c.id = t.category_id\n" +
                "WHERE\n" +
                "    c.transaction_type = 'expense' and c.user_id=? and c.activeYN=1 and t.activeYN=1\n" +
                "GROUP BY c.name",(ResultSet rs,int rownum)->{
            CategoryWiseDashboardData cd=new CategoryWiseDashboardData();
            cd.setName(rs.getString("name"));
            cd.setAmount(rs.getDouble("amount"));
            return cd;
        },user_id);
        ans.put("categorywiseExpense",categorywiseExpense);
        System.out.println(categorywiseExpense);
        List<CategoryWiseDashboardData> categorywiseIncome=jdbcTemplate.query("SELECT \n" +
                "    c.name, SUM(t.amount) AS amount\n" +
                "FROM\n" +
                "    category c\n" +
                "        LEFT JOIN\n" +
                "    transaction t ON c.id = t.category_id\n" +
                "WHERE\n" +
                "    c.transaction_type = 'income' and c.user_id=? and c.activeYN=1 and t.activeYN=1\n" +
                "GROUP BY c.name",(ResultSet rs,int rownum)->{
            CategoryWiseDashboardData cd=new CategoryWiseDashboardData();
            cd.setName(rs.getString("name"));
            cd.setAmount(rs.getDouble("amount"));
            return cd;
        },user_id);
        ans.put("categorywiseIncome",categorywiseIncome);
        List<MonthwiseDashboardData> monthwiseExpense=jdbcTemplate.query("SELECT \n" +
                "     MONTH(t.dateOfTransaction) as months,SUM(t.amount) AS amount\n" +
                "FROM\n" +
                "    category c\n" +
                "        LEFT JOIN\n" +
                "    transaction t ON c.id = t.category_id\n" +
                "WHERE\n" +
                "    c.transaction_type = 'expense' and c.user_id=? and c.activeYN=1 and t.activeYN=1\n" +
                "GROUP BY months order by months",(ResultSet rs,int rownum)->{
            MonthwiseDashboardData cd=new MonthwiseDashboardData();
            cd.setMonths(rs.getInt("months"));
            cd.setAmount(rs.getDouble("amount"));
            return cd;
        },user_id);
        ans.put("monthwiseExpense",monthwiseExpense);
        List<MonthwiseDashboardData> monthwiseIncome=jdbcTemplate.query("SELECT \n" +
                "     MONTH(t.dateOfTransaction) as months,SUM(t.amount) AS amount\n" +
                "FROM\n" +
                "    category c\n" +
                "        LEFT JOIN\n" +
                "    transaction t ON c.id = t.category_id\n" +
                "WHERE\n" +
                "    c.transaction_type = 'income' and c.user_id=? and c.activeYN=1 and t.activeYN=1\n" +
                "GROUP BY months order by months",(ResultSet rs,int rownum)->{
            MonthwiseDashboardData cd=new MonthwiseDashboardData();
            cd.setMonths(rs.getInt("months"));
            cd.setAmount(rs.getDouble("amount"));
            return cd;
        },user_id);
        ans.put("monthwiseIncome",monthwiseIncome);
        List<SavingData> savingData=jdbcTemplate.query(
                "SELECT \n" +
                        "     date_format(t.dateOfTransaction,'%b %Y') as months,SUM(CASE WHEN c.transaction_type='expense' THEN t.amount ELSE 0 END) AS amount_spent,\n" +
                        "     SUM(CASE WHEN c.transaction_type='income' THEN t.amount ELSE 0 END) AS amount_gained\n" +
                        "FROM\n" +
                        "    category c\n" +
                        "        LEFT JOIN\n" +
                        "    transaction t ON c.id = t.category_id\n" +
                        "WHERE\n" +
                        "     c.user_id=? and c.activeYN=1 and t.activeYN=1 and t.dateOfTransaction >=DATE_SUB(NOW(), INTERVAL 1 YEAR) and \n" +
                        "     t.dateOfTransaction<=now()\n" +
                        "GROUP BY months order by MONTH(months),YEAR(months);",(ResultSet rs,int rownum)->{
            SavingData sd=new SavingData();
            sd.setAmount_gained(rs.getDouble("amount_gained"));
            sd.setAmount_spent(rs.getDouble("amount_spent"));
            sd.setMonths(rs.getString("months"));
            return sd;
        },user_id);
        ans.put("savingData",savingData);
        System.out.println(ans);

        return ans;
    }
}
