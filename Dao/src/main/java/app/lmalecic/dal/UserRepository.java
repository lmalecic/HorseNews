/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.lmalecic.dal;

import app.lmalecic.model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author lmalecic
 */
public interface UserRepository extends Encodable<User> {
    int create(User user) throws Exception;
    void update(User user) throws Exception;
    void delete(User user) throws Exception;
    Optional<User> getByUsername(String username) throws Exception;
    List<User> getAll() throws Exception;
    Optional<User> login(String username, String password) throws Exception;
    User register(String username, String password) throws UserAlreadyExistsException, Exception;
}
