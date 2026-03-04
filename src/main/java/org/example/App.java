package org.example;

import org.example.service.ExpenseService;
import org.example.util.FileManager;
import org.example.model.Expense;
import java.util.*;

public class App {

    public static void main(String[] args) throws Exception {

        ExpenseService service=new ExpenseService();

        List<String> lines=FileManager.readFile("expenses.txt");
        List<Expense> expenses=service.parseExpenses(lines);

        service.saveToDB(expenses);

        System.out.println("Data saved into database.");

        System.out.println("Total Amount Of Each Person To Paid  : ");

        Map<Integer, ArrayList<Integer>> grouped = service.groupProductByPerson();

        service.calculateTotal();

//        System.out.println("------Enter the number Of CRUD Operation you want to Apply ------");
//        Scanner sc=new Scanner(System.in);
//        int oper=sc.nextInt();
//
//        for(int i=0;i<oper;i++){
//            System.out.println("Press : 1 - To Delete Order     |    Press : 2 - To update Order");
//            int opt=sc.nextInt();
//            switch (opt){
//                case 1:
//                    System.out.print("Enter the Expense Id to delete - ");
//                    int expenseId=sc.nextInt();
//                    service.deleteExpense(expenseId);
//                    service.calculateTotal();
//                    break;
//
//                case 2:
//                    System.out.println("Enter the Expense Id , CustomerId , Category , amout , to update - ");
//                    expenseId=sc.nextInt();
//                    int customerId=sc.nextInt();
//                    String category=sc.next();
//                    int amount=sc.nextInt();
//                    Expense expense=new Expense(customerId,category,amount);
//                    service.updateExpense(expense,expenseId);
//                    service.calculateTotal();
//                    break;
//            }
//        }
    }
}