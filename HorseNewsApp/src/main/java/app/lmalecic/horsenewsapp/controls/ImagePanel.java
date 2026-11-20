/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp.controls;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author lmalecic
 */
public class ImagePanel extends JPanel {
    private static final String DEFAULT_IMG_PATH = "/defaultArticleImage.png";
    private BufferedImage image;
    private boolean defaultImage = false;
    
    public ImagePanel() {
        this.setDefaultImage();
    }
    
    public void setImage(URL imageUrl) throws IOException {
        this.image = ImageIO.read(imageUrl);
        this.defaultImage = false;
        this.repaint();
    }
    
    public void setImage(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, "Byte array is null or empty!");
            return;
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        this.image = ImageIO.read(bais);
        this.defaultImage = false;

        this.repaint();
    }
    
    public void setDefaultImage() {
        try {
            this.setImage(this.getClass().getResource(DEFAULT_IMG_PATH));
            this.defaultImage = true;
        } catch (IOException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, "Failed to set default image!", ex);
        }
    }
    
    public byte[] getImageBytes() throws IOException {
        if (this.image == null) {
            return new byte[0];
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(this.image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }
    
    public boolean isDefault() {
        return this.defaultImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) return;

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        // Determine the scale to fill the panel
        double scale = Math.max((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);

        // Calculate dimensions for cropping
        int scaledWidth = (int) (imgWidth * scale);
        int scaledHeight = (int) (imgHeight * scale);
        int x = (panelWidth - scaledWidth) / 2;
        int y = (panelHeight - scaledHeight) / 2;

        // Draw the image, scaled and cropped to fill and center
        g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
    }

}
