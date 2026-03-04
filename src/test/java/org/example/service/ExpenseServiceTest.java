package org.example.service;

import org.example.DAO.ExpenseDAO;
import org.example.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    private ExpenseDAO dao;
    private ExpenseService service;

    @BeforeEach
    void setUp() {
        dao = mock(ExpenseDAO.class);
        service = new ExpenseService(dao);
    }

    @Test
    void testParseExpenses() {

        List<String> lines = Arrays.asList(
                "1,Food,200.0",
                "2,Travel,300.0"
        );

        List<Expense> expenses = service.parseExpenses(lines);

        assertEquals(2, expenses.size());
        assertEquals(1, expenses.get(0).getCustomerId());
        assertEquals("Food", expenses.get(0).getCategory());
        assertEquals(200.0, expenses.get(0).getAmount());
    }

    @Test
    void testSaveToDB() {

        Expense expense = new Expense(1, "Food", 500.0);
        List<Expense> list = Collections.singletonList(expense);

        service.saveToDB(list);

        verify(dao, times(1)).saveExpense(expense);
    }

    @Test
    void testGroupProductByPerson() {

        Map<Integer, ArrayList<Integer>> mockData = new HashMap<>();
        mockData.put(1, new ArrayList<>(Arrays.asList(101, 102)));

        when(dao.getExpenseIdOfPersons()).thenReturn(mockData);

        Map<Integer, ArrayList<Integer>> result = service.groupProductByPerson();

        assertEquals(1, result.size());
        assertTrue(result.containsKey(1));
        assertEquals(2, result.get(1).size());
    }
}