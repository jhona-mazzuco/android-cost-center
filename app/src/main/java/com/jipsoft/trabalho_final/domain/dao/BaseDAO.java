package com.jipsoft.trabalho_final.domain.dao;

import android.database.sqlite.SQLiteDatabase;

import com.jipsoft.trabalho_final.core.DBConnection;

public class BaseDAO<T> {

    protected static SQLiteDatabase database = DBConnection.getInstace();

    protected T relationshipRepository;

    public BaseDAO(T relationship) {
        relationshipRepository = relationship;
    }

    public T getRelationshipRepository() {
        return relationshipRepository;
    };
}
