
package com.tlk.dao;

import java.util.List;



/**
 *
 * @author MACBOOK Pro
 * @param <EntityType>
 * @param <KeyType>
 */
abstract public class TlkDAO<EntityType, KeyType>{
    abstract public void insert(EntityType entity);
    abstract public void update(EntityType entity);
    abstract public void delete(KeyType id);
    abstract public EntityType selectByID(KeyType id);
    abstract public List<EntityType> selectAll();
    abstract protected List<EntityType> selectBySql(String sql, Object...args);
}