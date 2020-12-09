package com.jipsoft.trabalho_final.domain.dao;

import java.util.List;

public interface BasicCrudMethod<T, R, K> {

    void createTable();

    void create(T entity);

    List<T> find(R id);

    T findById(K id);

    List<T> findQuery(R idRelationship, K id);

    void update(T entity);

    void remove(K id);

}
