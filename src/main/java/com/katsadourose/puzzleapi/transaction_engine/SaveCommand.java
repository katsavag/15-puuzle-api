package com.katsadourose.puzzleapi.transaction_engine;


import java.util.concurrent.ConcurrentHashMap;

public class SaveCommand<T, S> implements TransactionCommand{
    private final ConcurrentHashMap<S, T> storageMap;
    private final T entity;
    private final S id;

    public SaveCommand(ConcurrentHashMap<S, T> storageMap, T entity, S id) {
        this.storageMap = storageMap;
        this.entity = entity;
        this.id = id;
    }

    @Override
    public void execute() {
        storageMap.put(id, entity);
    }

    @Override
    public void undo() {
        storageMap.remove(id);
    }
}
