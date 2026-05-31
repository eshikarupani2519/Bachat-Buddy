package com.sgt.expense_tracker.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class FileResponse {
    String amount;
    String notes,category,type;
    String date;
    List<String> errors;
}
