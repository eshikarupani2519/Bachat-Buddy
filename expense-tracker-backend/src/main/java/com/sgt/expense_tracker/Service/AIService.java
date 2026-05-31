package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Model.Category;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AIService {
    @Autowired
    ChatModel chatModel;
    public String suggestCategory(String notes, List<Category> categories){
        List<String> categoryNames=new ArrayList<>();
        for(int i=0;i<categories.size();i++){
            categoryNames.add(categories.get(i).getName());
        }
        String prompt= """
                Categorize this notes:"{notes}"
                Available categories:{categoryNames}
                 - First try to match the transaction with the MOST SEMANTICALLY SIMILAR category from the list.
                 - Similar meaning counts as a match.
                 - Only create a NEW category if none of the existing categories are even loosely related.
                 - If creating a new category, keep it ONE WORD.
                 - Return only the category name.
                """;
//        creating template of prompt which will be later populated with parameters
        PromptTemplate promptTemplate=new PromptTemplate(prompt);
        Map<String,Object> params=Map.of(
                "notes",notes,
                "categoryNames",categoryNames
        );
        return chatModel.call(promptTemplate.create(params)).getResult().getOutput().getText();
    }
}
//write a sql query to get amount,categorywise to show in pie chart(typewise)
//write a sql query to list expenses monthwise for past year or current finantial year to display in line graph
//for monthly savings for current financial year to display in line chart and subdivided bar graph for income and expenses