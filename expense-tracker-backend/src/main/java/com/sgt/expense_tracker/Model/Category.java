package com.sgt.expense_tracker.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    int id,user_id,activeYN;
    String name,description,icon_url,transaction_type;

}
