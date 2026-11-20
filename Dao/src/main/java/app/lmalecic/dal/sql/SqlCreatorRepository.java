/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.sql;

import app.lmalecic.dal.CreatorRepository;
import app.lmalecic.dal.annotations.AnnotationReader;
import app.lmalecic.model.Creator;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

/**
 *
 * @author lmalecic
 */
public class SqlCreatorRepository extends SqlEntityRepository<Creator> implements CreatorRepository {

    private static final String CREATE = "{ call createCreator (?, ?) }";
    private static final String GET = "{ call getCreator (?) }";
    private static final String GET_ALL = "{ call getAllCreators }";
    private static final String UPDATE = "{ call updateCreator (?, ?) }";
    private static final String DELETE = "{ call deleteCreator (?) }";
    
    private static final String GET_BY_NAME = "{ call getCreatorByName (?) }";
    
    public SqlCreatorRepository() {
        super(new EntityProcCalls(
                CREATE,
                GET,
                GET_ALL,
                UPDATE,
                DELETE));
    }

    @Override
    public void encode(CallableStatement statement, Creator entity) throws Exception {
        statement.setString(AnnotationReader.getColumnName(Creator.class, "name"), entity.getName());
    }

    @Override
    public Creator decode(ResultSet resultSet) throws Exception {
        var article = new Creator(
            resultSet.getInt(AnnotationReader.getColumnName(Creator.class, "id")),
            resultSet.getString(AnnotationReader.getColumnName(Creator.class, "name"))
        );
        
        return article;
    }

    @Override
    public Optional<Creator> getByName(String name) throws Exception {
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
