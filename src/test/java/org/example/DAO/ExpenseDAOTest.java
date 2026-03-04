package org.example.DAO;

import org.example.config.DatabaseConfig;
import org.example.model.Expense;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseDAOTest {

    private ExpenseDAO dao;

    @BeforeAll
    static void setupDatabase() throws Exception {

        // Use H2 in-memory DB
        DatabaseConfig.setTestDatabase("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        try (Connection con = DatabaseConfig.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("""
                CREATE TABLE expense (
                    expenseId INT AUTO_INCREMENT PRIMARY KEY,
                    customerId INT,
                    category VARCHAR(255),
                    amount DOUBLE
                )
            """);
        }
    }

    @BeforeEach
    void setUp() {
        dao = new ExpenseDAO();
    }

    @Test
    void testSaveAndGetAmount() {

        Expense expense = new Expense(1, "Food", 200.0);
        dao.saveExpense(expense);

        double amount = dao.getAmount(1);

        assertEquals(200.0, amount);
    }

    @Test
    void testUpdateExpense() {

        Expense expense = new Expense(1, "Travel", 300.0);
        dao.updateExpense(expense, 1);

        double amount = dao.getAmount(1);

        assertEquals(300.0, amount);
    }

    @Test
    void testDeleteExpense() {

        dao.deleteExpense(1);

        double amount = dao.getAmount(1);

        assertEquals(0.0, amount);
    }

    @Test
    void testGrouping() {

        Expense expense1 = new Expense(2, "Shopping", 400.0);
        Expense expense2 = new Expense(2, "Food", 150.0);

        dao.saveExpense(expense1);
        dao.saveExpense(expense2);

        Map<Integer, ArrayList<Integer>> grouped = dao.getExpenseIdOfPersons();

        assertTrue(grouped.containsKey(2));
        assertEquals(2, grouped.get(2).size());
    }
}