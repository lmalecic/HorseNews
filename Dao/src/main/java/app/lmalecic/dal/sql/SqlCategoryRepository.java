/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.sql;

import app.lmalecic.dal.CategoryRepository;
import app.lmalecic.dal.annotations.AnnotationReader;
import app.lmalecic.model.Category;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

/**
 *
 * @author lmalecic
 */
public class SqlCategoryRepository extends SqlEntityRepository<Category> implements CategoryRepository {

    private static final String CREATE = "{ call createCategory (?, ?) }";
    private static final String GET = "{ call getCategory (?) }";
    private static final String GET_ALL = "{ call getAllCategories }";
    private static final String UPDATE = "{ call updateCategory (?, ?) }";
    private static final String DELETE = "{ call deleteCategory (?) }";
    
    private static final String GET_BY_NAME = "{ call getCategoryByName (?) }";
    
    public SqlCategoryRepository() {
        super(new EntityProcCalls(
                CREATE,
                GET,
                GET_ALL,
                UPDATE,
                DELETE));
    }

    @Override
    public void encode(CallableStatement statement, Category entity) throws Exception {
        statement.setString(AnnotationReader.getColumnName(Category.class, "name"), entity.getName());
    }

    @Override
    public Category decode(ResultSet resultSet) throws Exception {
        var article = new Category(
            resultSet.getInt(AnnotationReader.getColumnName(Category.class, "id")),
            resultSet.getString(AnnotationReader.getColumnName(Category.class, "name"))
        );
        
        return article;
    }

    @Override
    public Optional<Category> getByName(String name) throws Exception {
        try(
           Connection connection = DataSourceSingleton.getInstance().getConnection();
           CallableStatement statement = connection.prepareCall(GET_BY_NAME)
        ) {    
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(decode(rs));
                }
            }
        }
        
        return Optional.empty();
    }
    
}
