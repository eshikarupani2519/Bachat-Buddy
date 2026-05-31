package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Repository.DashboardRepository;
import com.sgt.expense_tracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    DashboardRepository dashboardRepository;
    @Autowired
    UserRepository userRepository;
    public Map<String, Object> get(String email){
        return dashboardRepository.get(userRepository.findByEmail(email).getId());
    }
}
