/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.sql;

import app.lmalecic.dal.UserAlreadyExistsException;
import app.lmalecic.dal.RepositoryFactory;
import app.lmalecic.dal.UserRepository;
import app.lmalecic.dal.annotations.AnnotationReader;
import app.lmalecic.model.Role;
import app.lmalecic.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author lmalecic
 */
public class SqlUserRepository implements UserRepository {

    private static final String CREATE = "{ call createUser (?, ?, ?, ?, ?) }";
    private static final String UPDATE = "{ call updateUser (?, ?, ?, ?, ?) }";
    private static final String DELETE = "{ call deleteUser (?) }";
    private static final String GET_BY_NAME = "{ call getUserByUsername (?) }";
    private static final String GET_ALL = "{ call getAllUsers }";
    
    public SqlUserRepository() {
    }

    @Override
    public int create(User user) throws Exception {
        try (
            Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(CREATE)
        ) {
            statement.registerOutParameter("NewId", Types.INTEGER);
            encode(statement, user);
            statement.executeUpdate();
            
            int id = statement.getInt("NewId");
            user.setId(id);
            
            return id;
        }
    }
    
    @Override
    public void update(User user) throws Exception {
        try(Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(UPDATE)) {
            
            statement.setInt(1, user.getId());
            encode(statement, user);
            
            statement.executeUpdate();
        }
    }
    
    @Override
    public void delete(User user) throws Exception {
        try(Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(DELETE)
        ) {
            statement.setString(1, user.getUsername());
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<User> getByUsername(String username) throws Exception {
        try(
           Connection connection = DataSourceSingleton.getInstance().getConnection();
           CallableStatement statement = connection.prepareCall(GET_BY_NAME)
        ) {    
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(decode(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    @Override
    public List<User> getAll() throws Exception {
        List<User> entities = new ArrayList<User>();

        try(Connection connection = DataSourceSingleton.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(GET_ALL);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                entities.add(decode(resultSet));
            }
        }
        
        return entities;
    }

    @Override
    public Optional<User> login(String username, String password) throws Exception {
        var repo = RepositoryFactory.get();
        for (var user : repo.getUserRepo().getAll()) {
            if (!user.getUsername().equals(username)) continue;
            String providedHash = hashPassword(password, user.getPwdSalt());
            if (providedHash.equals(user.getPwdHash())) {
                
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public User register(String username, String password) throws UserAlreadyExistsException, Exception {
        var repo = RepositoryFactory.get().getUserRepo();
        if (repo.getByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        
        String salt = getSalt();
        String passwordHash = hashPassword(password, salt);
        User user = new User(username, passwordHash, salt);
        user.setRole(Role.USER);
        repo.create(user);
        return user;
    }

    @Override
    public void encode(CallableStatement statement, User entity) throws Exception {
        statement.setString(AnnotationReader.getColumnName(User.class, "username"), entity.getUsername());
        statement.setString(AnnotationReader.getColumnName(User.class, "pwdHash"), entity.getPwdHash());
        statement.setString(AnnotationReader.getColumnName(User.class, "pwdSalt"), entity.getPwdSalt());
        statement.setInt(AnnotationReader.getColumnName(User.class, "role"), entity.getRole().ordinal());
    }

    @Override
    public User decode(ResultSet resultSet) throws Exception {
        var repo = RepositoryFactory.get();
        
        User user = new User(
            resultSet.getInt(AnnotationReader.getColumnName(User.class, "id")),
            resultSet.getString(AnnotationReader.getColumnName(User.class, "username")),
            resultSet.getString(AnnotationReader.getColumnName(User.class, "pwdHash")),
            resultSet.getString(AnnotationReader.getColumnName(User.class, "pwdSalt"))
        );
        
        int roleIndex = resultSet.getInt(AnnotationReader.getColumnName(User.class, "role"));
        Role role = Role.values()[roleIndex];
        user.setRole(role != null ? role : Role.USER);
        
        return user;
    }
    
    public static String getSalt() throws Exception {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
