/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.lmalecic.utilities;

import app.lmalecic.factory.UrlConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author lmalecic
 */
public class FileUtils {

    private static final String UPLOAD = "Upload";
    private static final String SELECT = "Select";

    private FileUtils() {
    }

    public static void createFromBytes(String destination, byte[] bytes) throws IOException {
       createDirHierarchy(destination);
       Files.write(Paths.get(destination), bytes);
    }

    public static Optional<File> uploadFile(String description, String... extensions) {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        chooser.setDialogTitle(UPLOAD);
        chooser.setApproveButtonText(UPLOAD);
        chooser.setApproveButtonToolTipText(UPLOAD);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            return selectedFile.exists() && Arrays.asList(extensions).contains(extension.toLowerCase()) ? Optional.of(selectedFile) : Optional.empty();
        }
        return Optional.empty();
    }

    public static void copy(String source, String destination) throws IOException {
        createDirHierarchy(destination);
        Files.copy(Paths.get(source), Paths.get(destination));
    }

    private static void createDirHierarchy(String destination) throws IOException {
        String dir = destination.substring(0, destination.lastIndexOf("/"));
        if (!Files.exists(Paths.get(dir))) {
            Files.createDirectories(Paths.get(dir));
        }
    }

    public static void copyFromUrl(String source, String destination) throws MalformedURLException, IOException {
        createDirHierarchy(destination);
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(source);
        try (InputStream is = con.getInputStream()) {
            Files.copy(is, Paths.get(destination));
        }
    }

    public static Optional<File> saveFile(String description, String defaultFileName, String... extensions) {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogTitle(SELECT);
        chooser.setApproveButtonText(SELECT);
        chooser.setApproveButtonToolTipText(SELECT);
        
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            if (selected.isDirectory()) {
                selected = new File(selected, defaultFileName);
            }
            
            String extension = selected.getName().substring(selected.getName().lastIndexOf(".") + 1);
            if (extension.isEmpty() || !Arrays.asList(extensions).contains(extension.toLowerCase())) {
                selected = new File(selected.getAbsolutePath() + "." + extensions[0]);
            }
            
            if (selected.exists()) {
                var overwrite = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
                if (overwrite != JOptionPane.YES_OPTION) {
                    return Optional.empty();
                }
            }
            
            return Optional.of(selected);
        }
        
        return Optional.empty();
    }
    
}
