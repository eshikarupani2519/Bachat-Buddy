package com.sgt.expense_tracker.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class Transactions {
int id,user_id,category_id,activeYN;
double amount;
LocalDate dateOfTransaction;
String notes;
    String transaction_type;
    String category_name;

}
