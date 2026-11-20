/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp;

import app.lmalecic.dal.RepositoryFactory;
import app.lmalecic.horsenewsapp.window.MainWindow;
import app.lmalecic.horsenewsapp.window.LoginWindow;
import app.lmalecic.model.Role;
import app.lmalecic.model.User;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lmalecic
 */
public class HorseNewsApp {
    
    private static MainWindow MainWindow;
    private static LoginWindow LoginWindow;

    private static Optional<User> user;

    public static Optional<User> getUser() {
        return HorseNewsApp.user;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacDarkLaf.setup();
        
        initAdmin();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            HorseNewsApp.LoginWindow = new LoginWindow();
            HorseNewsApp.LoginWindow.setVisible(true);
        });
    }
    
    public static void performSignOut() {
        HorseNewsApp.user = Optional.empty();
        HorseNewsApp.MainWindow.dispose();
        
        HorseNewsApp.LoginWindow = new LoginWindow();
        HorseNewsApp.LoginWindow.setVisible(true);
    }
    
    public static void performLogIn(User user) {
        HorseNewsApp.user = Optional.of(user);
        HorseNewsApp.LoginWindow.dispose();
        
        HorseNewsApp.MainWindow = new MainWindow();
        HorseNewsApp.MainWindow.setVisible(true);
    }
    
    public static void initAdmin() {
        try {
            var userRepo = RepositoryFactory.get().getUserRepo();
            
            if (userRepo.getByUsername("admin").isEmpty()) {
                User admin = userRepo.register("admin", "admin");
                admin.setRole(Role.ADMINISTRATOR);
                userRepo.update(admin);
            }
        } catch (Exception ex) {
            Logger.getLogger(HorseNewsApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
