/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.dal.sql;

/**
 *
 * @author lmalecic
 */
public class EntityProcCalls {
    public final String create;
    public final String get;
    public final String getAll;
    public final String update;
    public final String delete;

    public EntityProcCalls(String create, String get, String getAll, String update, String delete) {
        this.create = create;
        this.get = get;
        this.getAll = getAll;
        this.update = update;
        this.delete = delete;
    }
}
