package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.entity.Expense;
import java.util.List;

public interface ExpenseService {

    List<Expense> getAllExpenses();

    Expense getExpenseById(Long id);

    Expense saveExpenseDetails(Expense expense);

    Expense updateExpenseDetails(Long id, Expense expense);

    void deleteExpenseById(Long id);
}
