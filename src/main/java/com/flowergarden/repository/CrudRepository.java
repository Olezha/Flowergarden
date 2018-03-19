package com.flowergarden.repository;

import java.io.Serializable;

public interface CrudRepository<T, ID extends Serializable> {

    <S extends T> S saveOrUpdate(S entity);

    T findOne(ID id);
    Iterable<T> findAll();

    void delete(ID id);
    void delete(T entity);
    void deleteAll();

    boolean exists(ID id);
    int count();
}
