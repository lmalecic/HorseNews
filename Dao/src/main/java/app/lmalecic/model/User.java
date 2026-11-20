/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.model;

import app.lmalecic.dal.annotations.ColumnName;
import java.util.Objects;

/**
 *
 * @author lmalecic
 */
public class User implements Entity {
    
    @ColumnName("Id")
    private int id;
    
    @ColumnName("Username")
    private String username;
    
    @ColumnName("PwdHash")
    private String pwdHash;
    
    @ColumnName("PwdSalt")
    private String pwdSalt;
    
    @ColumnName("RoleIndex")
    private Role role;
    
    public User() {
    }
    
    public User(String username, String pwdHash, String pwdSalt) {
        this();
        this.username = username;
        this.pwdHash = pwdHash;
        this.pwdSalt = pwdSalt;
    }
    
    public User(int id, String username, String pwdHash, String pwdSalt) {
        this(username, pwdHash, pwdSalt);
        this.id = id;
    }
    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPwdHash() {
        return this.pwdHash;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }
    
    public String getPwdSalt() {
        return this.pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
    }
    
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return this.getUsername();
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.username, other.username);
    }
    
}
