/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.lmalecic.dal;

import java.sql.CallableStatement;
import java.sql.ResultSet;

/**
 *
 * @author lmalecic
 */
public interface Encodable<E> {
    abstract void encode(CallableStatement statement, E entity) throws Exception;
    abstract E decode(ResultSet resultSet) throws Exception;
}
