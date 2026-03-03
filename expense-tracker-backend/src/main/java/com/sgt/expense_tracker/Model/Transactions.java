package com.sgt.expense_tracker.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class Transactions {
int id,user_id,category_id,activeYN;
double amount;
LocalDate dateOfTransaction;
String notes,transaction_type,category_name;
}
