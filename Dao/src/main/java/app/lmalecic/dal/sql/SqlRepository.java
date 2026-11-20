/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.sql;

import app.lmalecic.dal.ArticleRepository;
import app.lmalecic.dal.CategoryRepository;
import app.lmalecic.dal.CreatorRepository;
import app.lmalecic.dal.Repository;
import app.lmalecic.dal.UserRepository;
import java.sql.CallableStatement;
import java.sql.Connection;

public class SqlRepository implements Repository {
    
    private static final String DELETE_ALL_DATA = "{ call deleteAllData() }";
    
    private final SqlArticleRepository articles = new SqlArticleRepository();
    private final SqlCategoryRepository categories = new SqlCategoryRepository();
    private final SqlCreatorRepository creators = new SqlCreatorRepository();
    private final SqlUserRepository users = new SqlUserRepository();
    
    @Override
    public ArticleRepository getArticleRepo() {
         return articles;
    }

    @Override
    public CategoryRepository getCategoryRepo() {
        return categories;
    }

    @Override
    public CreatorRepository getCreatorRepo() {
        return creators;
    }

    @Override
    public void deleteAllData() throws Exception {
        try (
            Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(DELETE_ALL_DATA)
        ) {
            statement.executeUpdate();
        }
    }

    @Override
    public UserRepository getUserRepo() {
        return users;
    }
}
