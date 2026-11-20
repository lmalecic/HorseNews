/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.sql;

import app.lmalecic.dal.EntityRepository;
import app.lmalecic.model.Entity;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author lmalecic
 */
abstract class SqlEntityRepository<E extends Entity> implements EntityRepository<E> {
    
    private EntityProcCalls procCalls;
    
    public SqlEntityRepository(EntityProcCalls procCalls) {
        this.procCalls = procCalls;
    }
    
    @Override
    public int create(E entity) throws Exception {
        try (
            Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(procCalls.create)
        ) {
            statement.registerOutParameter("NewId", Types.INTEGER);
            encode(statement, entity);
            statement.executeUpdate();
            
            int id = statement.getInt("NewId");
            entity.setId(id);
            
            return id;
        }
    }
    
    @Override
    public Optional<E> get(int id) throws Exception {
        try(Connection connection = DataSourceSingleton.getInstance().getConnection();
           CallableStatement statement = connection.prepareCall(procCalls.get)
        ) {    
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(decode(rs));
                }
            }
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<E> getAll() throws Exception {
        List<E> entities = new ArrayList<E>();

        try(Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(procCalls.getAll);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                entities.add(decode(resultSet));
            }
        }
        
        return entities;
    }
    
    @Override
    public void update(E entity) throws Exception {
        try(Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(procCalls.update)) {
            
            statement.setInt(1, entity.getId());
            encode(statement, entity);
            
            statement.executeUpdate();
        }
    }
    
    @Override
    public void delete(int id) throws Exception {
        try(Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(procCalls.delete)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    
//    @Override
//    public List<Integer> createMany(List<E> entities) throws Exception {
//        List<Integer> createdIds = new ArrayList<Integer>();
//        entities.forEach(entity -> {
//            try {
//                int newEntityId = this.create(entity);
//                createdIds.add(newEntityId);
//            } catch (Exception ex) {
//                Logger.getLogger(SqlEntityRepository.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//        
//        return createdIds;
//    }
}
