package org.example.service;

import org.example.DAO.ExpenseDAO;
import org.example.model.Expense;
import java.util.*;
public class ExpenseService {

    private ExpenseDAO dao;

    public ExpenseService() {

    }
    public ExpenseService(ExpenseDAO dao) {
        this.dao = dao;
    }

    public List<Expense> parseExpenses(List<String> lines) {
        List<Expense> expenses = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            expenses.add(new Expense(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    Double.parseDouble(parts[2])));
        }

        return expenses;
    }

    public void saveToDB(List<Expense> expenses) {
        for (Expense expense : expenses) {
            dao.saveExpense(expense);
        }
    }

    public Map<Integer, ArrayList<Integer>> groupProductByPerson() {
        return dao.getExpenseIdOfPersons();
    }
    public void calculateTotal() {

        Map<Integer, ArrayList<Integer>> expenseOfEachPerson = dao.getExpenseIdOfPersons();

        System.out.println("Expenses Of Each Person are - : ");
        System.out.println("--------------------------------");

        for (Map.Entry<Integer, ArrayList<Integer>> entry : expenseOfEachPerson.entrySet()) {

            double sum = 0;

            for (Integer expenseId : entry.getValue()) {
                sum += dao.getAmount(expenseId);
            }

            System.out.println("Customer: " + entry.getKey() + " Total: " + sum);
        }
    }
}