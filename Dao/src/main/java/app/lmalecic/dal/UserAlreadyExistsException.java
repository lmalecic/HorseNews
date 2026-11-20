package app.lmalecic.dal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lmalecic
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("A user with the given username already exists!");
    }
}
