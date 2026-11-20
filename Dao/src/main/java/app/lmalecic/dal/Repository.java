/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.lmalecic.dal;

import app.lmalecic.model.Article;

/**
 *
 * @author lmalecic
 */
public interface Repository {
    ArticleRepository getArticleRepo();
    CategoryRepository getCategoryRepo();
    CreatorRepository getCreatorRepo();
    UserRepository getUserRepo();
    
    void deleteAllData() throws Exception;
}
