/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.lmalecic.dal;

import app.lmalecic.model.Entity;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author lmalecic
 */
public interface EntityRepository<E extends Entity> extends Encodable<E> {
    
    int create(E entity) throws Exception;
    Optional<E> get(int id) throws Exception;
    List<E> getAll() throws Exception;
    void update(E entity) throws Exception;
    void delete(int id) throws Exception;
    
}
