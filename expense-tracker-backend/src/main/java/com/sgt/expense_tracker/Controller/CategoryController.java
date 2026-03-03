package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Model.Category;
import com.sgt.expense_tracker.Repository.UserRepository;
import com.sgt.expense_tracker.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class CategoryController {
    Logger logger= LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/categories")
    public ResponseEntity<Map<String,String>> addCategory(@RequestBody Category category, Authentication auth){
//        System.out.println(userRepository.findByEmail(auth.getName()).getId());
        try{
            logger.info(String.valueOf(category));
            categoryService.addCategory(category,auth.getName());
            return ResponseEntity.ok().body(Map.of("body","Added category successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getCategories( Authentication auth){
        try{
          return ResponseEntity.ok().body(Map.of("body",categoryService.getCategories(auth.getName())))  ;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable(name = "id") int id){
        try{
            return ResponseEntity.ok().body(Map.of("body",categoryService.getCategoryById(id)))  ;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Map<String,String>> editCategory(@PathVariable (name = "id") int id, @RequestBody Category category,Authentication auth){
        try{
            categoryService.editCategory(id,category,auth.getName());
            return ResponseEntity.ok().body(Map.of("body","Updated category successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable(name = "id") int id,Authentication auth){
        try{
            categoryService.deleteCategory(id,auth.getName());
            return ResponseEntity.ok().body(Map.of("body","Deleted category successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }
}
