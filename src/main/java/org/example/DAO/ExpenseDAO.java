package org.example.DAO;

import org.example.config.DatabaseConfig;
import org.example.model.Expense;

import java.sql.*;
import java.util.*;

public class ExpenseDAO {

    public void saveExpense(Expense expense){
        String query = "INSERT INTO expense (customerId, category, amount) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, expense.getCustomerId());
            ps.setString(2, expense.getCategory());
            ps.setDouble(3, expense.getAmount());
            ps.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteExpense(int expenseId) {
        String query = "DELETE FROM expense WHERE expenseId=?";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, expenseId);
            ps.executeUpdate();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void updateExpense(Expense expense,int expenseId) {
        String updateQuery = "UPDATE expense SET customerId=?, category=?, amount=? WHERE expenseId=?";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(updateQuery)) {

            ps.setInt(1, expense.getCustomerId());
            ps.setString(2, expense.getCategory());
            ps.setDouble(3, expense.getAmount());
            ps.setInt(4, expenseId);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                String insertQuery = "INSERT INTO expense (expenseId, customerId, category, amount) VALUES (?, ?, ?, ?)";

                try (PreparedStatement insertPs = con.prepareStatement(insertQuery)) {
                    insertPs.setInt(1, expenseId);
                    insertPs.setInt(2, expense.getCustomerId());
                    insertPs.setString(3, expense.getCategory());
                    insertPs.setDouble(4, expense.getAmount());
                    insertPs.executeUpdate();
                }
                System.out.println("Inserted new expense.");
            } else {
                System.out.println("Updated existing expense.");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public double getAmount(int expenseId){

        String query = "SELECT amount FROM expense WHERE expenseId=?";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, expenseId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("amount");
            }
            else {
                return 0.0;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return 0.0;
        }
    }

    public Map<Integer, ArrayList<Integer>> getExpenseIdOfPersons(){

        Map<Integer, ArrayList<Integer>> grouped = new HashMap<>();

        String query = "SELECT expenseId, customerId FROM expense";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Integer expenseId = (Integer) rs.getInt("expenseId");
                Integer customerId = (Integer) rs.getInt("customerId");

                // if customer not present → create new list
                if (!grouped.containsKey(customerId)) {
                    grouped.put(customerId, new ArrayList<>());
                }

                // add expenseId to that customer
                grouped.get(customerId).add(expenseId);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return grouped;
    }
}