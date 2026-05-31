import { Component } from '@angular/core';
import { Chart, ChartItem } from 'chart.js/auto';
import { CategoriesService } from '../categories.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
    constructor(private dashboardService:CategoriesService){}
ngOnInit(){
  let categorywiseExpense:any = [
//  {name:"Food", amount_spent:2000},
//  {name:"Travel", amount_spent:500},
//  {name:"Shopping", amount_spent:1500}
 
];
 let categorywiseIncome:any = [
//  {name:"Food", amount_spent:2000},
//  {name:"Travel", amount_spent:500},
//  {name:"Shopping", amount_spent:1500}
 
];
 let monthwiseExpense:any = [
//   {months:1, amount_spent:2000},
//  {months:2, amount_spent:500},
//  {months:3, amount_spent:1500},
//  {months:4, amount_spent:150},
//  {months:5, amount_spent:2500},
//  {months:6, amount_spent:1100},
//  {months:7, amount_spent:1800},
//   {months:8, amount_spent:1200},
//    {months:9, amount_spent:1400},
//     {months:10, amount_spent:1900},
//      {months:11, amount_spent:200},
//       {months:12, amount_spent:1800}
];
let monthwiseIncome:any = [
//  {months:1, amount_spent:2000},
//  {months:2, amount_spent:500},
//  {months:3, amount_spent:1500},
//  {months:4, amount_spent:150},
//  {months:5, amount_spent:2500},
//  {months:6, amount_spent:1100},
//  {months:7, amount_spent:1800},
//   {months:8, amount_spent:1200},
//    {months:9, amount_spent:1400},
//     {months:10, amount_spent:1900},
//      {months:11, amount_spent:200},
//       {months:12, amount_spent:1800}
];
let incomevsexpense :any= [
//  {months:1, amount_spent:2000,amount_gained:2000},
//  {months:2, amount_spent:500,amount_gained:2000},
//  {months:3, amount_spent:1500,amount_gained:2000},
//  {months:4, amount_spent:150,amount_gained:2000},
//  {months:5, amount_spent:2500,amount_gained:2000},
//  {months:6, amount_spent:1100,amount_gained:2000},
//  {months:7, amount_spent:1800,amount_gained:2000},
//   {months:8, amount_spent:1200,amount_gained:2000},
//    {months:9, amount_spent:1400,amount_gained:2000},
//     {months:10, amount_spent:1900,amount_gained:2000},
//      {months:11, amount_spent:200,amount_gained:2000},
//       {months:12, amount_spent:1800,amount_gained:2000}
];
this.dashboardService.getDashboard().subscribe({
    next:(res:any)=>{
        console.log(res.body);
        categorywiseExpense=res.body.categorywiseExpense;
        categorywiseIncome=res.body.categorywiseIncome;
        monthwiseExpense=res.body.monthwiseExpense;
        monthwiseIncome=res.body.monthwiseIncome;
        incomevsexpense=res.body.savingData;
        console.log(categorywiseExpense);
        let categorywiseExpenselabels = categorywiseExpense.map((item:any) => item.name);
let categorywiseExpensevalues = categorywiseExpense.map((item:any) => item.amount);
console.log(categorywiseExpenselabels);
let ctx = document.getElementById('categorywiseExpenseChart') as ChartItem;

if (ctx) {
  new Chart(ctx, {
      type: 'pie',   // bar, line, doughnut etc
      data: {
          labels: categorywiseExpenselabels,
          datasets: [{
              label: 'Category Expense',
              data: categorywiseExpensevalues,
              backgroundColor: [
                  '#FF6384',
                  '#36A2EB',
                  '#FFCE56',
                  '#ff5656',
                  '#6fff56',
                  '#7c6da7',
                  '#ff8196',
                  '#56fff7',
                  '#e6ff56',
                  '#9201a2',
                  '#ff00bf',
                  '#0a9000',
              ],
              borderWidth: 1
          }]
      }
  });
}
ctx = document.getElementById('categorywiseIncomeChart') as ChartItem;
let categorywiseIncomelabels = categorywiseIncome.map((item:any) => item.name);
let categorywiseIncomevalues = categorywiseIncome.map((item:any) => item.amount);
if (ctx) {
  new Chart(ctx, {
      type: 'pie',   // bar, line, doughnut etc
      data: {
          labels: categorywiseIncomelabels,
          datasets: [{
              label: 'Category Income',
              data: categorywiseIncomevalues,
              backgroundColor: [
                  '#FF6384',
                  '#36A2EB',
                  '#FFCE56',
                  '#ff5656',
                  '#6fff56',
                  '#7c6da7',
                  '#ff8196',
                  '#56fff7',
                  '#e6ff56',
                  '#9201a2',
                  '#ff00bf',
                  '#0a9000',
              ],
              borderWidth: 1
          }]
      }
  });
}
ctx = document.getElementById('monthwiseExpenseChart') as ChartItem;
let monthwiseExpenselabels = monthwiseExpense.map((item:any) => item.months);
let monthwiseExpensevalues = monthwiseExpense.map((item:any) => item.amount);
if (ctx) {
  new Chart(ctx, {
      type: 'line',   // bar, line, doughnut etc
      data: {
          labels: monthwiseExpenselabels,
          datasets: [{
              label: 'Monthwise expense',
              data: monthwiseExpensevalues,
              backgroundColor: [
                 '#FF6384',
                  '#36A2EB',
                  '#FFCE56',
                  '#ff5656',
                  '#6fff56',
                  '#7c6da7',
                  '#ff8196',
                  '#56fff7',
                  '#e6ff56',
                  '#9201a2',
                  '#ff00bf',
                  '#0a9000',
              ],
              borderWidth: 1
          }]
      }
  });
}
ctx = document.getElementById('monthwiseIncomeChart') as ChartItem;
let monthwiseIncomelabels = monthwiseIncome.map((item:any) => item.months);
let monthwiseIncomevalues = monthwiseIncome.map((item:any) => item.amount);
if (ctx) {
  new Chart(ctx, {
      type: 'line',   // bar, line, doughnut etc
      data: {
          labels: monthwiseIncomelabels,
          datasets: [{
              label: 'Monthwise income',
              data: monthwiseIncomevalues,
              backgroundColor: [
                  '#FF6384',
                  '#36A2EB',
                  '#FFCE56',
                  '#ff5656',
                  '#6fff56',
                  '#7c6da7',
                  '#ff8196',
                  '#56fff7',
                  '#e6ff56',
                  '#9201a2',
                  '#ff00bf',
                  '#0a9000',
              ],
              borderWidth: 1
          }]
      }
  });
}
ctx = document.getElementById('incomevsexpenseChart') as ChartItem;
let incomevsexpenselabels=incomevsexpense.map((item:any)=>item.months) ;
let incomevsexpenseExpense :any[]= incomevsexpense.map((item:any) => item.amount_spent);
let incomevsexpenseIncome:any[] = incomevsexpense.map((item:any) => item.amount_gained);
console.log(incomevsexpenseExpense)
console.log(incomevsexpenseIncome)
if (ctx) {
  new Chart(ctx, {
      type: 'bar',   // bar, line, doughnut etc
      data: {
          labels: incomevsexpenselabels,
          datasets: [{
              label: 'Monthwise expense',
              data: incomevsexpenseExpense,
              backgroundColor: [
                  '#FF6384',
                //   '#36A2EB',
                //   '#FFCE56',
                //   '#ff5656',
                //   '#6fff56',
                //   '#7c6da7',
                //   '#ff8196',
                //   '#56fff7',
                //   '#e6ff56',
                //   '#9201a2',
                //   '#ff00bf',
                //   '#0a9000',
              ],
              borderWidth: 1
          },
            {
              label: 'Monthwise income',
              data: incomevsexpenseIncome,
              backgroundColor: [
                //   '#FF6384',
                  '#36A2EB',
                //   '#FFCE56',
                //   '#ff5656',
                //   '#6fff56',
                //   '#7c6da7',
                //   '#ff8196',
                //   '#56fff7',
                //   '#e6ff56',
                //   '#9201a2',
                //   '#ff00bf',
                //   '#0a9000',
              ],
              borderWidth: 1
          }
        ]
      },
        options: {
        scales: {
            x: {
                stacked: true, // Key property to stack the bars on the X-axis
            },
            y: {
                stacked: true  // Key property to stack the bars on the Y-axis and show the total
            }
        },
        // plugins: {
        //     title: {
        //         display: true,
        //         text: 'Subdivided Bar Diagram Example'
        //     }
        // },
        responsive: true,
    }
  });
}
ctx = document.getElementById('savingsChart') as ChartItem;

let savingslabels = incomevsexpense.map((item:any) => item.months);
let savingsvalues = incomevsexpense.map((item:any) => item.amount_gained-item.amount_spent);
if (ctx) {
  new Chart(ctx, {
      type: 'line',   // bar, line, doughnut etc
      data: {
          labels: savingslabels,
          datasets: [{
              label: 'Monthwise savings',
              data: savingsvalues,
              backgroundColor: [
                  '#FF6384',
                  '#36A2EB',
                  '#FFCE56',
                  '#ff5656',
                  '#6fff56',
                  '#7c6da7',
                  '#ff8196',
                  '#56fff7',
                  '#e6ff56',
                  '#9201a2',
                  '#ff00bf',
                  '#0a9000',
              ],
              borderWidth: 1
          }]
      }
  });
}
    },
    error:(err:any)=>{
        console.log(err);
    }

})

}

}


