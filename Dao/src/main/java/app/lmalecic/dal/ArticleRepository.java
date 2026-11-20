/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.lmalecic.dal;

import app.lmalecic.model.Article;
import app.lmalecic.model.Category;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author lmalecic
 */
public interface ArticleRepository extends EntityRepository<Article> {
    Optional<Article> getByGuid(String guid) throws Exception;
    void addCategory(Article article, Category category, boolean updateModel) throws Exception;
    void removeCategory(Article article, Category category, boolean updateModel) throws Exception;
    List<Category> getCategories(Article article) throws Exception;
}
