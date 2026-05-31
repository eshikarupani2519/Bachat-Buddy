package com.sgt.expense_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication
@EnableScheduling
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}
//google password:pzuv hnon adrl bawr
}
