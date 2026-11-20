/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.lmalecic.dal;

import app.lmalecic.model.Category;
import java.util.Optional;

/**
 *
 * @author lmalecic
 */
public interface CategoryRepository extends EntityRepository<Category> {
    Optional<Category> getByName(String name) throws Exception;
}
