package com.katsadourose.puzzleapi.transaction_engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {
    private TransactionManager transactionManager;
    private ConcurrentHashMap<Integer, String> storageMap;

    @BeforeEach
    void setUp() {
        transactionManager = new TransactionManager();
        storageMap = new ConcurrentHashMap<>();
    }

    @Test
    void testExecuteCommand() {
        SaveCommand<String, Integer> saveCommand = new SaveCommand<>(storageMap, "Test", 1);
        transactionManager.executeCommand(saveCommand);
        assertEquals("Test", storageMap.get(1));
    }

    @Test
    void testRollback_SaveCommand() {
        SaveCommand<String, Integer> saveCommand = new SaveCommand<>(storageMap, "Test", 1);
        transactionManager.executeCommand(saveCommand);
        transactionManager.rollback();
        assertNull(storageMap.get(1));
    }

    @Test
    void testRollback_DeleteCommand() {
        storageMap.put(1, "Test");
        DeleteCommand<String, Integer> deleteCommand = new DeleteCommand<>(storageMap, 1);
        transactionManager.executeCommand(deleteCommand);
        transactionManager.rollback();
        assertEquals("Test", storageMap.get(1));
    }
}