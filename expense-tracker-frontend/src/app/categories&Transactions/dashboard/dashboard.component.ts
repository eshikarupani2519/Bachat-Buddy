import { Component, OnInit } from '@angular/core';
import { Chart, ChartItem } from 'chart.js/auto';
import { CategoriesService } from '../categories.service';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

    // Explicitly declare variables to hold the text responses from your Spring Boot controller
    categorywiseExpenseInsight: string = 'Loading insights...';
    categorywiseIncomeInsight: string = 'Loading insights...';
    monthwiseExpenseInsight: string = 'Loading insights...';
    monthwiseIncomeInsight: string = 'Loading insights...';
    incomeVsExpenseInsight: string = 'Loading insights...';
    savingsDynamicInsight: string = 'Loading insights...';

    constructor(private dashboardService: CategoriesService) { }

    ngOnInit() {
        let categorywiseExpense: any = [];
        let categorywiseIncome: any = [];
        let monthwiseExpense: any = [];
        let monthwiseIncome: any = [];
        let incomevsexpense: any = [];

        this.dashboardService.getDashboard().subscribe({
            next: (res: any) => {
                

                // 1. Assign values coming from the database response
                categorywiseExpense = res.body.categorywiseExpense;
                categorywiseIncome = res.body.categorywiseIncome;
                monthwiseExpense = res.body.monthwiseExpense;
                monthwiseIncome = res.body.monthwiseIncome;
                incomevsexpense = res.body.savingData;

                // =====================================================================
                // 🔥 FIX: Build payload and fire AI tracking after data arrives
                // =====================================================================
                const analysisPayload = {
                    categorywiseExpense: categorywiseExpense,
                    categorywiseIncome: categorywiseIncome,
                    monthwiseExpense: monthwiseExpense,
                    monthwiseIncome: monthwiseIncome,
                    savingData: incomevsexpense
                };

                this.dashboardService.analyzeDashboard(analysisPayload).subscribe({
                    next: (aiInsights: any) => {
                       

                        // 🔥 UPDATED TO MATCH THE SNAKE_CASE KEYS RETURNED BY THE AI
                        this.categorywiseExpenseInsight = aiInsights.categorywiseExpenseInsight;
                        this.categorywiseIncomeInsight = aiInsights.categorywiseIncomeInsight;
                        this.monthwiseExpenseInsight = aiInsights.monthwiseExpenseInsight;
                        this.monthwiseIncomeInsight = aiInsights.monthwiseIncomeInsight;
                        this.incomeVsExpenseInsight = aiInsights.incomeVsExpenseInsight;
                        this.savingsDynamicInsight = aiInsights.savingsDynamicInsight;
                    },
                    error: (aiErr: any) => {
                        alert("Failed to fetch analytical streams from AI");
                    }
                });
                // =====================================================================

                // --- Chart 1: Categorywise Expense ---
                let categorywiseExpenselabels = categorywiseExpense.map((item: any) => item.name);
                let categorywiseExpensevalues = categorywiseExpense.map((item: any) => item.amount);
                let ctx = document.getElementById('categorywiseExpenseChart') as ChartItem;

                if (ctx) {
                    new Chart(ctx, {
                        type: 'pie',
                        data: {
                            labels: categorywiseExpenselabels,
                            datasets: [{
                                label: 'Category Expense',
                                data: categorywiseExpensevalues,
                                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#ff5656', '#6fff56', '#7c6da7', '#ff8196', '#56fff7', '#e6ff56', '#9201a2', '#ff00bf', '#0a9000'],
                                borderWidth: 1
                            }]
                        }
                    });
                }

                // --- Chart 2: Categorywise Income ---
                ctx = document.getElementById('categorywiseIncomeChart') as ChartItem;
                let categorywiseIncomelabels = categorywiseIncome.map((item: any) => item.name);
                let categorywiseIncomevalues = categorywiseIncome.map((item: any) => item.amount);
                if (ctx) {
                    new Chart(ctx, {
                        type: 'pie',
                        data: {
                            labels: categorywiseIncomelabels,
                            datasets: [{
                                label: 'Category Income',
                                data: categorywiseIncomevalues,
                                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#ff5656', '#6fff56', '#7c6da7', '#ff8196', '#56fff7', '#e6ff56', '#9201a2', '#ff00bf', '#0a9000'],
                                borderWidth: 1
                            }]
                        }
                    });
                }

                // --- Chart 3: Monthwise Expense ---
                ctx = document.getElementById('monthwiseExpenseChart') as ChartItem;
                let monthwiseExpenselabels = monthwiseExpense.map((item: any) => item.months);
                let monthwiseExpensevalues = monthwiseExpense.map((item: any) => item.amount);
                if (ctx) {
                    new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: monthwiseExpenselabels,
                            datasets: [{
                                label: 'Monthwise expense',
                                data: monthwiseExpensevalues,
                                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#ff5656', '#6fff56', '#7c6da7', '#ff8196', '#56fff7', '#e6ff56', '#9201a2', '#ff00bf', '#0a9000'],
                                borderWidth: 1
                            }]
                        }
                    });
                }

                // --- Chart 4: Monthwise Income ---
                ctx = document.getElementById('monthwiseIncomeChart') as ChartItem;
                let monthwiseIncomelabels = monthwiseIncome.map((item: any) => item.months);
                let monthwiseIncomevalues = monthwiseIncome.map((item: any) => item.amount);
                if (ctx) {
                    new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: monthwiseIncomelabels,
                            datasets: [{
                                label: 'Monthwise income',
                                data: monthwiseIncomevalues,
                                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#ff5656', '#6fff56', '#7c6da7', '#ff8196', '#56fff7', '#e6ff56', '#9201a2', '#ff00bf', '#0a9000'],
                                borderWidth: 1
                            }]
                        }
                    });
                }

                // --- Chart 5: Income vs Expense ---
                ctx = document.getElementById('incomevsexpenseChart') as ChartItem;
                let incomevsexpenselabels = incomevsexpense.map((item: any) => item.months);
                let incomevsexpenseExpense: any[] = incomevsexpense.map((item: any) => item.amount_spent);
                let incomevsexpenseIncome: any[] = incomevsexpense.map((item: any) => item.amount_gained);
                if (ctx) {
                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: incomevsexpenselabels,
                            datasets: [
                                {
                                    label: 'Monthwise expense',
                                    data: incomevsexpenseExpense,
                                    backgroundColor: ['#FF6384'],
                                    borderWidth: 1
                                },
                                {
                                    label: 'Monthwise income',
                                    data: incomevsexpenseIncome,
                                    backgroundColor: ['#36A2EB'],
                                    borderWidth: 1
                                }
                            ]
                        },
                        options: {
                            scales: {
                                x: { stacked: true },
                                y: { stacked: true }
                            },
                            responsive: true
                        }
                    });
                }

                // --- Chart 6: Savings Dynamic ---
                ctx = document.getElementById('savingsChart') as ChartItem;
                let savingslabels = incomevsexpense.map((item: any) => item.months);
                let savingsvalues = incomevsexpense.map((item: any) => item.amount_gained - item.amount_spent);
                if (ctx) {
                    new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: savingslabels,
                            datasets: [{
                                label: 'Monthwise savings',
                                data: savingsvalues,
                                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#ff5656', '#6fff56', '#7c6da7', '#ff8196', '#56fff7', '#e6ff56', '#9201a2', '#ff00bf', '#0a9000'],
                                borderWidth: 1
                            }]
                        }
                    });
                }
            },
            error: (err: any) => {
                alert("Failed to load dashboard ");
            }
        });
    }
}