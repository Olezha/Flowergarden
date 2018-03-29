/*
 * Copyright 2008-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flowergarden.repository;

import java.io.Serializable;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 *
 * @author Oliver Gierke
 * @author Eberhard Wolff
 *
 * @author Oleh Shklyar (refactoring for self needs)
 */
public interface CrudRepository<T, ID extends Serializable> {

    /**
     * Saves or update a given entity.
     * Use the returned instance for further operations
     * as the save operation might have changed the entity instance.
     *
     * @param entity
     * @return the saved entity
     */
    <S extends T> S saveOrUpdate(S entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     */
    T findOne(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     */
    void delete(ID id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     */
    boolean exists(ID id);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    int count();
}
