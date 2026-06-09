package com.sgt.expense_tracker.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgt.expense_tracker.Model.Category;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.MapOutputConverter;
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
    public Map<String, Object> getDashboardInsights(String notes, List<Category> categories,
        List<Map<String, Object>> categorywiseExpense,
        List<Map<String, Object>> categorywiseIncome,
        List<Map<String, Object>> monthwiseExpense,
        List<Map<String, Object>> monthwiseIncome,
        List<Map<String, Object>> incomeVsExpense,
        List<Map<String, Object>> savingsDynamic // Added the 6th dataset explicitly
) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                MapOutputConverter outputConverter = new MapOutputConverter();

                String prompt = """
                        You are a premium financial analytics AI. Analyze these 6 separate user datasets and generate an individual insight paragraph for each chart.
                        
                                        Data Context:
                                        1. Categorywise Expense (Pie): {categorywiseExpense}
                                        2. Categorywise Income (Pie): {categorywiseIncome}
                                        3. Monthwise Expense Trend (Line): {monthwiseExpense}
                                        4. Monthwise Income Trend (Line): {monthwiseIncome}
                                        5. Income vs Expense Comparison (Bar): {incomeVsExpense}
                                        6. Savings Dynamic Trend (Line): {savingsDynamic}
                        
                                        CRITICAL INSTRUCTIONS:
                                        - Write exactly ONE short, actionable paragraph (2-3 sentences max) for each of the 6 charts.
                                        - Focus on specific patterns, operational anomalies, or target milestones.
                                        - Do not use markdown syntax inside the paragraph values. Keep the tone elite and grounded.
                        
                                        {format}
                        
                                        ⚠️ STATED FORMAT OVERRIDE RULE:
                                        You MUST ignore your default naming conventions and return the JSON map using EXACTLY these 6 keys. Do not add '_insight', do not use snake_case, do not change capitalization:
                                        - "categorywiseExpenseInsight"
                                        - "categorywiseIncomeInsight"
                                        - "monthwiseExpenseInsight"
                                        - "monthwiseIncomeInsight"
                                        - "incomeVsExpenseInsight"
                                        - "savingsDynamicInsight"
                """;

                PromptTemplate promptTemplate = new PromptTemplate(prompt);

                Map<String, Object> params = Map.of(
                        "categorywiseExpense", objectMapper.writeValueAsString(categorywiseExpense),
                        "categorywiseIncome", objectMapper.writeValueAsString(categorywiseIncome),
                        "monthwiseExpense", objectMapper.writeValueAsString(monthwiseExpense),
                        "monthwiseIncome", objectMapper.writeValueAsString(monthwiseIncome),
                        "incomeVsExpense", objectMapper.writeValueAsString(incomeVsExpense),
                        "savingsDynamic", objectMapper.writeValueAsString(savingsDynamic),
                        "format", outputConverter.getFormat()
                );

                String rawResponse = chatModel.call(promptTemplate.create(params)).getResult().getOutput().getText();
                return outputConverter.convert(rawResponse);

            } catch (Exception e) {
                return Map.of("error", "Failed to compute all 6 chart analytics: " + e.getMessage());
            }
    }

}
//write a sql query to get amount,categorywise to show in pie chart(typewise)
//write a sql query to list expenses monthwise for past year or current finantial year to display in line graph
//for monthly savings for current financial year to display in line chart and subdivided bar graph for income and expenses