/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.sql;

import app.lmalecic.dal.ArticleRepository;
import app.lmalecic.dal.RepositoryFactory;
import app.lmalecic.dal.annotations.AnnotationReader;
import app.lmalecic.model.Article;
import app.lmalecic.model.Category;
import app.lmalecic.model.Creator;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author lmalecic
 */
public class SqlArticleRepository extends SqlEntityRepository<Article> implements ArticleRepository {

    private static final String CREATE = "{ call createArticle (?, ?, ?, ?, ?, ?, ?, ?) }";
    private static final String GET = "{ call getArticle (?) }";
    private static final String GET_ALL = "{ call getAllArticles }";
    private static final String UPDATE = "{ call updateArticle (?, ?, ?, ?, ?, ?, ?, ?) }";
    private static final String DELETE = "{ call deleteArticle (?) }";
    
    private static final String GET_BY_GUID = "{ call getArticleByGuid (?) }";
    
    private static final String ADD_CATEGORY_TO_ARTICLE = "{ call addCategoryToArticle (?, ?) }";
    private static final String REMOVE_CATEGORY_FROM_ARTICLE = "{ call removeCategoryFromArticle (?, ?) }";
    private static final String GET_ARTICLE_CATEGORIES = "{ call getArticleCategories (?) }";

    public SqlArticleRepository() {
        super(new EntityProcCalls(
                CREATE,
                GET,
                GET_ALL,
                UPDATE,
                DELETE));
    }

    @Override
    public void encode(CallableStatement statement, Article entity) throws Exception {
        statement.setString(AnnotationReader.getColumnName(Article.class, "title"), entity.getTitle());
        statement.setString(AnnotationReader.getColumnName(Article.class, "link"), entity.getLink());
        statement.setString(AnnotationReader.getColumnName(Article.class, "description"), entity.getDescription());
        statement.setObject(AnnotationReader.getColumnName(Article.class, "datePublished"), entity.getDatePublished());
        statement.setInt(AnnotationReader.getColumnName(Article.class, "creator"), entity.getCreator().getId());
        statement.setString(AnnotationReader.getColumnName(Article.class, "guid"), entity.getGuid());
        statement.setBytes(AnnotationReader.getColumnName(Article.class, "image"), entity.getImage());
    }

    @Override
    public Article decode(ResultSet resultSet) throws Exception {
        var repo = RepositoryFactory.get();
        
        var creatorId = resultSet.getInt(AnnotationReader.getColumnName(Article.class, "creator"));
        var existingCreator = repo.getCreatorRepo().get(creatorId);
        var creator = existingCreator.isPresent() ? existingCreator.get() : new Creator(creatorId, "NOT IMPLEMENTED");
        
        Article article = new Article(
            resultSet.getInt(AnnotationReader.getColumnName(Article.class, "id")),
            resultSet.getString(AnnotationReader.getColumnName(Article.class, "title")),
            resultSet.getString(AnnotationReader.getColumnName(Article.class, "link")),
            creator,
            resultSet.getObject(AnnotationReader.getColumnName(Article.class, "datePublished"), LocalDateTime.class),
            resultSet.getString(AnnotationReader.getColumnName(Article.class, "description")),
            resultSet.getString(AnnotationReader.getColumnName(Article.class, "guid"))
        );
        
        article.setCategories(this.getCategories(article));
        article.setImage(resultSet.getBytes(AnnotationReader.getColumnName(Article.class, "image")));
        
        return article;
    }

    @Override
    public Optional<Article> getByGuid(String guid) throws Exception {
        try(Connection connection = DataSourceSingleton.getInstance().getConnection();
           CallableStatement statement = connection.prepareCall(GET_BY_GUID)
        ) {    
            statement.setString(1, guid);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(decode(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    @Override
    public void addCategory(Article article, Category category, boolean updateModel) throws Exception {
        try (
            Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(ADD_CATEGORY_TO_ARTICLE)
        ) {
            statement.setInt("ArticleId", article.getId());
            statement.setInt("CategoryId", category.getId());
            statement.executeUpdate();
            
            if (updateModel) {
                article.addCategory(category);
            }
        }
    }

    @Override
    public void removeCategory(Article article, Category category, boolean updateModel) throws Exception {
        try (
            Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(REMOVE_CATEGORY_FROM_ARTICLE)
        ) {
            statement.setInt("ArticleId", article.getId());
            statement.setInt("CategoryId", category.getId());
            statement.executeUpdate();
            
            if (updateModel) {
                article.removeCategory(category);
            }
        }
    }

    @Override
    public List<Category> getCategories(Article article) throws Exception {
        List<Category> categories = new ArrayList<Category>();
        
        try (
            Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(GET_ARTICLE_CATEGORIES)
        ) {
            statement.setInt("ArticleId", article.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categories.add(new Category(
                    resultSet.getInt("CategoryId"),
                    resultSet.getString("CategoryName")
                ));
            }
        }
        
        return categories;
    }
}
