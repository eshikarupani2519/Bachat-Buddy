package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Exceptions.EmailNotRegisteredException;
import com.sgt.expense_tracker.Model.Category;
import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Repository.CategoryRepository;
import com.sgt.expense_tracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    public boolean checkIfUserIdExists(int id){
        return categoryRepository.checkIfUserIdExists(id);
    }
    public void addCategory(Category category,String email) throws EmailNotRegisteredException{
        User user=userRepository.findByEmail(email);
        int user_id=user.getId();
        String name= category.getName();
        String description= category.getDescription();
        String icon_url= category.getIcon_url();
        String transaction_type=category.getTransaction_type();

        categoryRepository.addCategory(user_id,name,description,icon_url,transaction_type);
    }
    public List<Category> getCategories(String email){
        User user=userRepository.findByEmail(email);
          return  categoryRepository.getCategories(user.getId());
    }
    public List<Category> getCategoryById(int id){
        return  categoryRepository.getCategoryById(id);
    }
    public void editCategory(int id,Category category,String email){
        String name=category.getName();
        String description=category.getDescription();
        String icon_url=category.getIcon_url();
        String transaction_type=category.getTransaction_type();
        categoryRepository.editCategory(id,name,description,icon_url,transaction_type,userRepository.findByEmail(email).getId());
    }
    public void deleteCategory(int id,String email){
        categoryRepository.deleteCategory(id,userRepository.findByEmail(email).getId());
    }
}
