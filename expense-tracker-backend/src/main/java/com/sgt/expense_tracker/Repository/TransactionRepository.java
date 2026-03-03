package com.sgt.expense_tracker.Repository;

import com.sgt.expense_tracker.Mapper.TransactionMapper;
import com.sgt.expense_tracker.Model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public void create(int user_id, double amount, int category_id, LocalDate dateOfTransaction,String notes){
        jdbcTemplate.update("insert into transaction(user_id,amount,category_id,dateOfTransaction,notes) values (?,?,?,?,?)",user_id,amount,category_id,dateOfTransaction,notes);
    }
    public List<Transactions> get(int user_id, String category, LocalDate start, LocalDate end, String type,String column,String direction,Long pageNo,Integer rowsPerPage){
        List<Object> params=new ArrayList<>();
        try{
            params.add(user_id);
            StringBuilder sql=new StringBuilder("select t.id,t.user_id,t.amount,t.dateOfTransaction,t.notes,c.name,c.transaction_type,t.category_id,t.activeYN from transaction  t inner join category  c on t.category_id=c.id where t.user_id = ? ");
            if(category!=null) {
                sql.append(" and c.name=?");
                params.add(category);
            }
            if(end!=null) {
                start=start==null?end.minusDays(30):start;
                sql.append(" and t.dateOfTransaction between ? and ?");
                params.add(start);params.add(end);
            }
            else if(start!=null){
                end = LocalDate.now();
                sql.append(" and t.dateOfTransaction between ? and ?");
                params.add(start);params.add(end);
            }
            if(type!=null) {
                sql.append(" and c.transaction_type=?");
                params.add(type);
            }

            if(column!=null && direction!=null){
                sql.append(" order by "+column+" "+direction);

            }
//            if(pageNo!=null && rowsPerPage!=null){
//                sql.append(" limit "+rowsPerPage+" offset "+(pageNo-1)*rowsPerPage+";select CEIL(count(*)/"+rowsPerPage+") as noOfPages from transaction");
//            }
            System.out.println("params:"+params);
            System.out.println(sql.toString());
//            we used string builder jaise multiple strings na banein
            List<Transactions> transactions=jdbcTemplate.query(sql.toString(),new TransactionMapper(),params.toArray());

            return transactions;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
