/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.lmalecic.horsenewsapp.views;

import app.lmalecic.dal.Repository;
import app.lmalecic.dal.RepositoryFactory;
import app.lmalecic.horsenewsapp.HorseNewsApp;
import app.lmalecic.horsenewsapp.views.model.ArticleTableModel;
import app.lmalecic.horsenewsapp.views.model.CategoriesListModel;
import app.lmalecic.horsenewsapp.views.model.CreatorComboBoxModel;
import app.lmalecic.model.Article;
import app.lmalecic.model.ArticleArchive;
import app.lmalecic.model.Category;
import app.lmalecic.model.Role;
import app.lmalecic.utilities.FileUtils;
import app.lmalecic.utilities.JAXBUtils;
import app.lmalecic.utilities.MessageUtils;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;
import javax.xml.bind.JAXBException;

/**
 *
 * @author lmalecic
 */
public class ArticlesView extends javax.swing.JPanel {

    private Repository repository;
    
    private ArticleTableModel articleTableModel;
    private Optional<Article> selectedArticle = Optional.empty();
    
    private CreatorComboBoxModel creatorComboBoxModel;
    
    private CategoriesListModel articleCategoriesListModel = new CategoriesListModel();
    private CategoriesListModel availableCategoriesListModel = new CategoriesListModel();
    private List<Category> allCategories;
    
    private List<JTextComponent> validationFields;
    private HashMap<JTextComponent, JLabel> errorLabels;
    
    private boolean isDragNDropEnabled = false;
    
    /**
     * Creates new form ArticlesView
     */
    public ArticlesView() {
        initComponents();
    }
    
    private void init() {
        this.initUser();
        this.initValidation();
        this.hideErrors();
        this.initRepository();
        this.initCreators();
        this.initCategories();
        this.initTable();
        this.initDragNDrop();
        this.clearForm();
    }
    
    private void initUser() {
        var user = HorseNewsApp.getUser();
        boolean isAdmin = user.isEmpty() ? false
                : HorseNewsApp.getUser().get().getRole().equals(Role.ADMINISTRATOR);
        
        this.isDragNDropEnabled = isAdmin;
        
        this.saveButton.setVisible(isAdmin);
        this.deleteButton.setVisible(isAdmin);
        this.createButton.setVisible(isAdmin);
        this.changeImageButton.setVisible(isAdmin);
    }
    
    private void initCategories() {
        new Thread(() -> {
            try {
                this.allCategories = this.repository.getCategoryRepo().getAll();
                this.articleCategoriesList.setModel(this.articleCategoriesListModel);
                this.availableCategoriesList.setModel(this.availableCategoriesListModel);
                
                this.availableCategoriesListModel.set(this.allCategories
                    .stream()
                    .collect(Collectors.toList())
        );
            } catch (Exception ex) {
                Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error", "Failed to get categories!");
            }
        }).start();
    }
    
    private void initCreators() {
        new Thread(() -> {
            try {
                this.creatorComboBoxModel = new CreatorComboBoxModel(this.repository.getCreatorRepo().getAll());
                this.creatorComboBox.setModel(this.creatorComboBoxModel);
            } catch (Exception ex) {
                Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error", "Failed to get creators!");
            }
        }).start();
    }
    
    private void initRepository() {
        this.repository = RepositoryFactory.get();
    }
    
    private void initValidation() {
        this.validationFields = Arrays.asList(
                this.articleTitleField,
                this.articleLinkField,
                this.descriptionArea
        );
        
        this.errorLabels = HashMap.newHashMap(this.validationFields.size());
        
        this.errorLabels.put(this.articleTitleField, this.articleTitleErrorLabel);
        this.errorLabels.put(this.articleLinkField, this.articleLinkErrorLabel);
        this.errorLabels.put(this.descriptionArea, this.articleDescriptionErrorLabel);
    }
    
    private void initTable() {
        new Thread(() -> {
            try {
                this.articlesTable.setAutoCreateRowSorter(true);
                this.articleTableModel = new ArticleTableModel(this.repository.getArticleRepo().getAll());
                this.articlesTable.setModel(this.articleTableModel);
            } catch (Exception ex) {
                Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error", "Failed to get articles!");
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        articleTitleField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        creatorComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        descriptionTextAreaPanel = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        datePublishedSpinner = new javax.swing.JSpinner();
        jScrollPane3 = new javax.swing.JScrollPane();
        articleCategoriesList = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        articlesTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        availableCategoriesList = new javax.swing.JList<>();
        createButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        removeCategoriesButton = new javax.swing.JButton();
        addCategoriesButton = new javax.swing.JButton();
        articleImagePanel = new app.lmalecic.horsenewsapp.controls.ImagePanel();
        changeImageButton = new javax.swing.JButton();
        articleTitleErrorLabel = new javax.swing.JLabel();
        articleDescriptionErrorLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        articleLinkField = new javax.swing.JTextField();
        articleLinkErrorLabel = new javax.swing.JLabel();
        xmlExportButton = new javax.swing.JButton();
        entityXmlExportButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(990, 690));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                articlesView_componentShown(evt);
            }
        });

        jPanel1.setMaximumSize(new java.awt.Dimension(978, 678));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setLabelFor(articleTitleField);
        jLabel1.setText("Article Title");

        articleTitleField.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setLabelFor(creatorComboBox);
        jLabel2.setText("Creator");

        creatorComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        creatorComboBox.setMaximumSize(new java.awt.Dimension(256, 32767));

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setLabelFor(descriptionArea);
        jLabel3.setText("Description");

        descriptionTextAreaPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        descriptionArea.setColumns(20);
        descriptionArea.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        descriptionArea.setLineWrap(true);
        descriptionArea.setRows(5);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setMaximumSize(new java.awt.Dimension(321, 2147483647));
        descriptionTextAreaPanel.setViewportView(descriptionArea);

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setText("Categories");

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setLabelFor(datePublishedSpinner);
        jLabel5.setText("Date Published");

        datePublishedSpinner.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        datePublishedSpinner.setModel(new javax.swing.SpinnerDateModel());
        datePublishedSpinner.setMaximumSize(new java.awt.Dimension(172, 32767));

        articleCategoriesList.setMaximumSize(new java.awt.Dimension(207, 50));
        jScrollPane3.setViewportView(articleCategoriesList);

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Available categories");

        articlesTable.setFont(new java.awt.Font("JetBrains Mono NL", 0, 12)); // NOI18N
        articlesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        articlesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        articlesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                articleTable_mouseClicked(evt);
            }
        });
        articlesTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                articlesTable_keyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(articlesTable);

        availableCategoriesList.setMaximumSize(new java.awt.Dimension(213, 50));
        jScrollPane4.setViewportView(availableCategoriesList);

        createButton.setBackground(new java.awt.Color(0, 102, 255));
        createButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        createButton.setText("Create");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButton_actionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(204, 0, 0));
        deleteButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton_actionPerformed(evt);
            }
        });

        saveButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButton_actionPerformed(evt);
            }
        });

        removeCategoriesButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        removeCategoriesButton.setText(">>");
        removeCategoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCategoriesButton_actionPerformed(evt);
            }
        });

        addCategoriesButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        addCategoriesButton.setText("<<");
        addCategoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoriesButton_actionPerformed(evt);
            }
        });

        articleImagePanel.setBackground(new java.awt.Color(40, 40, 40));

        changeImageButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        changeImageButton.setText("Change");
        changeImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeImageButton_actionPerformed(evt);
            }
        });

        javax.swing.GroupLayout articleImagePanelLayout = new javax.swing.GroupLayout(articleImagePanel);
        articleImagePanel.setLayout(articleImagePanelLayout);
        articleImagePanelLayout.setHorizontalGroup(
            articleImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(articleImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(changeImageButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        articleImagePanelLayout.setVerticalGroup(
            articleImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(articleImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(changeImageButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        articleTitleErrorLabel.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        articleTitleErrorLabel.setForeground(new java.awt.Color(204, 0, 0));
        articleTitleErrorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        articleTitleErrorLabel.setText("[ ! ]");

        articleDescriptionErrorLabel.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        articleDescriptionErrorLabel.setForeground(new java.awt.Color(204, 0, 0));
        articleDescriptionErrorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        articleDescriptionErrorLabel.setText("[ ! ]");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setLabelFor(articleLinkField);
        jLabel7.setText("Link");

        articleLinkField.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        articleLinkField.setMaximumSize(new java.awt.Dimension(321, 2147483647));

        articleLinkErrorLabel.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        articleLinkErrorLabel.setForeground(new java.awt.Color(204, 0, 0));
        articleLinkErrorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        articleLinkErrorLabel.setText("[ ! ]");

        xmlExportButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        xmlExportButton.setText("Export as XML");
        xmlExportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xmlExportButton_actionPerformed(evt);
            }
        });

        entityXmlExportButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        entityXmlExportButton.setText("Export");
        entityXmlExportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entityXmlExportButton_actionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(removeCategoriesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(addCategoriesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(articleImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(creatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(datePublishedSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(articleTitleErrorLabel))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(221, 221, 221)
                                        .addComponent(articleDescriptionErrorLabel))
                                    .addComponent(descriptionTextAreaPanel)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(articleLinkErrorLabel))
                                    .addComponent(articleLinkField, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(entityXmlExportButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(articleTitleField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(xmlExportButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(articleTitleErrorLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(articleTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(creatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(datePublishedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(articleDescriptionErrorLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(createButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(entityXmlExportButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(deleteButton)
                                    .addComponent(articleLinkField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(descriptionTextAreaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(articleLinkErrorLabel)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(articleImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(removeCategoriesButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addCategoriesButton)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xmlExportButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void changeImageButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeImageButton_actionPerformed
        var file = FileUtils.uploadFile("Images", "jpg", "jpeg", "png");
        if (file.isEmpty()) {
            return;
        }
        
        try {
            byte[] imgBytes = Files.readAllBytes(file.get().toPath());
            this.articleImagePanel.setImage(imgBytes);
        } catch (IOException ex) {
            Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Image upload error", "Failed to upload image!");
        }
    }//GEN-LAST:event_changeImageButton_actionPerformed

    private void removeCategoriesButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCategoriesButton_actionPerformed
        this.removeCategoriesFromArticle(this.articleCategoriesList.getSelectedValuesList());
    }//GEN-LAST:event_removeCategoriesButton_actionPerformed

    private void addCategoriesButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoriesButton_actionPerformed
        this.addCategoriesToArticle(this.availableCategoriesList.getSelectedValuesList());
    }//GEN-LAST:event_addCategoriesButton_actionPerformed

    private void createButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButton_actionPerformed
        if (!this.formValid()) {
            return;
        }
        
        try {
            var datePublished = (Date)this.datePublishedSpinner.getModel().getValue();
            var guid = UUID.randomUUID().toString();
            
            var article = new Article(
                    this.articleTitleField.getText().trim(),
                    this.articleLinkField.getText().trim(),
                    this.creatorComboBoxModel.getSelectedItem(),
                    LocalDateTime.ofInstant(datePublished.toInstant(), ZoneId.systemDefault()),
                    this.descriptionArea.getText().trim(),
                    guid
            );
            
            // Set image if set
            if (!this.articleImagePanel.isDefault()) {
                article.setImage(this.articleImagePanel.getImageBytes());
            }
            
            var articlesRepo = this.repository.getArticleRepo();
            articlesRepo.create(article);
            
            // Link categories
            for (var category : this.articleCategoriesListModel.getAll()) {
                articlesRepo.addCategory(article, category, true);
            }
            
            this.articleTableModel.setArticles(articlesRepo.getAll());
            this.clearForm();
        } catch (Exception ex) {
            Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Failed to create article!");
        }
    }//GEN-LAST:event_createButton_actionPerformed

    private void saveButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButton_actionPerformed
        if (this.selectedArticle.isEmpty()) {
            MessageUtils.showInformationMessage("Invalid operation", "Please select an article to update!");
            return;
        }
        
        if (!this.formValid()) {
            return;
        }
        
        try {
            var article = this.selectedArticle.get();
            article.setTitle(this.articleTitleField.getText().trim());
            article.setDescription(this.descriptionArea.getText().trim());
            article.setLink(this.articleLinkField.getText().trim());
            
            var datePublished = (Date)this.datePublishedSpinner.getModel().getValue();
            article.setDatePublished(LocalDateTime.ofInstant(datePublished.toInstant(), ZoneId.systemDefault()));
            article.setCreator(this.creatorComboBoxModel.getSelectedItem());
            
            var articlesRepo = this.repository.getArticleRepo();
            
            var oldCategories = article.getCategories();
            var newCategories = this.articleCategoriesListModel.getAll();
            
            // Remove old categories
            for (var cat : oldCategories) {
                if (!newCategories.contains(cat)) {
                    articlesRepo.removeCategory(article, cat, false);
                }
            }
            
            // Add new categores
            for (var cat : newCategories) {
                if (!oldCategories.contains(cat)) {
                    articlesRepo.addCategory(article, cat, true);
                }
            }
            
            // Update image
            article.setImage(this.articleImagePanel.getImageBytes());
            
            articlesRepo.update(article);
            this.articleTableModel.setArticles(articlesRepo.getAll());
            
            this.clearForm();
        } catch (Exception ex) {
            Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Failed to update article!");
        }
    }//GEN-LAST:event_saveButton_actionPerformed

    private void deleteButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton_actionPerformed
        if (this.selectedArticle.isEmpty()) {
            MessageUtils.showInformationMessage("Invalid operation", "Please select an article to delete!");
            return;
        }
        
        try {
            var articlesRepo = this.repository.getArticleRepo();
            articlesRepo.delete(this.selectedArticle.get().getId());
            this.clearForm();
            this.articleTableModel.setArticles(articlesRepo.getAll());
        } catch (Exception ex) {
            Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Failed to delete article!");
        }
    }//GEN-LAST:event_deleteButton_actionPerformed

    private void articlesTable_keyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_articlesTable_keyReleased
        this.showArticle();
    }//GEN-LAST:event_articlesTable_keyReleased

    private void articleTable_mouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_articleTable_mouseClicked
        this.showArticle();
    }//GEN-LAST:event_articleTable_mouseClicked

    private void articlesView_componentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_articlesView_componentShown
        this.init();
    }//GEN-LAST:event_articlesView_componentShown

    private void xmlExportButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xmlExportButton_actionPerformed
        try {
            var file = FileUtils.saveFile("Export to XML", ArticleArchive.ARCHIVE_NAME, "xml");
            if (file.isEmpty()) {
                return;
            }
            
            JAXBUtils.save(new ArticleArchive(this.articleTableModel.getArticles()), file.get());
        } catch (JAXBException ex) {
            Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Export error", "Failed to export to xml!");
        }
    }//GEN-LAST:event_xmlExportButton_actionPerformed

    private void entityXmlExportButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entityXmlExportButton_actionPerformed
        if (this.selectedArticle.isEmpty()) {
            MessageUtils.showInformationMessage("Export info", "Please select an article to export!");
            return;
        }
        
        try {
            var exportItem = this.selectedArticle.get();
            var file = FileUtils.saveFile("Export to XML", exportItem.getClass().getSimpleName() + "_" + exportItem.getTitle(), "xml");
            if (file.isEmpty()) {
                return;
            }
            
            JAXBUtils.save(exportItem, file.get());
        } catch (JAXBException ex) {
            Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Export error", "Failed to export to xml!");
        }
    }//GEN-LAST:event_entityXmlExportButton_actionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCategoriesButton;
    private javax.swing.JList<Category> articleCategoriesList;
    private javax.swing.JLabel articleDescriptionErrorLabel;
    private app.lmalecic.horsenewsapp.controls.ImagePanel articleImagePanel;
    private javax.swing.JLabel articleLinkErrorLabel;
    private javax.swing.JTextField articleLinkField;
    private javax.swing.JLabel articleTitleErrorLabel;
    private javax.swing.JTextField articleTitleField;
    private javax.swing.JTable articlesTable;
    private javax.swing.JList<Category> availableCategoriesList;
    private javax.swing.JButton changeImageButton;
    private javax.swing.JButton createButton;
    private javax.swing.JComboBox<String> creatorComboBox;
    private javax.swing.JSpinner datePublishedSpinner;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JScrollPane descriptionTextAreaPanel;
    private javax.swing.JButton entityXmlExportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton removeCategoriesButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton xmlExportButton;
    // End of variables declaration//GEN-END:variables

    private void addCategoriesToArticle(List<Category> categories) {
        categories.forEach(category -> {
            this.availableCategoriesListModel.remove(category);
            System.out.println("Removed " + category.toString() + " from available categories!");
            this.articleCategoriesListModel.add(category);
            System.out.println("Added " + category.toString() + " to article categories!");
        });
    }
    
    private void removeCategoriesFromArticle(List<Category> categories) {
        categories.forEach(category -> {
            this.articleCategoriesListModel.remove(category);
            System.out.println("Removed " + category.toString() + " from article categories!");
            this.availableCategoriesListModel.add(category);
            System.out.println("Added " + category.toString() + " to available categories!");
        });
    }
    
    private void showArticle() {
        this.clearForm();
        int selectedRow = this.articlesTable.getSelectedRow();
        int rowIndex = this.articlesTable.convertRowIndexToModel(selectedRow);
        int selectedArticleId = (int) this.articleTableModel.getValueAt(rowIndex, 0);

        try {
            Optional<Article> optArticle = this.repository.getArticleRepo().get(selectedArticleId);
            if (optArticle.isPresent()) {
                this.selectedArticle = optArticle;
                this.fillForm(this.selectedArticle);
            }
        } catch (Exception ex) {
            Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to show article!");
        }
    }

    private void clearForm() {
        this.hideErrors();
        this.validationFields.forEach(e -> e.setText(""));
        this.articleImagePanel.setDefaultImage();
        this.datePublishedSpinner.setValue(new Date());
        this.articleCategoriesListModel.clear();
        this.availableCategoriesListModel.set(this.allCategories);
        this.selectedArticle = Optional.empty();
    }

    private void fillForm(Optional<Article> selectedArticle) throws Exception {
        if (selectedArticle.isEmpty())
            return;
        
        var article = selectedArticle.get();
        
        this.articleTitleField.setText(article.getTitle());
        this.descriptionArea.setText(article.getDescription());
        this.creatorComboBoxModel.setSelectedItem(article.getCreator());
        this.datePublishedSpinner.setValue(Date.from(article.getDatePublished().atZone(ZoneId.systemDefault()).toInstant()));
        this.articleLinkField.setText(article.getLink());
        
        var imgBytes = article.getImage();
        if (imgBytes != null && imgBytes.length > 0) {
            this.articleImagePanel.setImage(imgBytes);
        } else {
            this.articleImagePanel.setDefaultImage();
        }
        
        var articleCategories = article.getCategories();
        this.articleCategoriesListModel.set(this.allCategories
                .stream()
                .filter(category -> {
                    return articleCategories.contains(category);
                })
                .collect(Collectors.toList())
        );
        this.availableCategoriesListModel.set(this.allCategories
                .stream()
                .filter(category -> {
                    return !articleCategories.contains(category);
                })
                .collect(Collectors.toList())
        );
        
        this.creatorComboBox.repaint();
    }

    private void hideErrors() {
        this.errorLabels.forEach((key, label) -> {
            label.setVisible(false);
            label.setToolTipText("");
        });
    }

    private boolean formValid() {
        hideErrors();
        boolean ok = true;
        
        for (var label : this.validationFields) {
            boolean errorVisible = false;
            String errorText = "";
            
            if (label.getText().trim().isEmpty()) {
                ok = false;
                errorVisible = true;
                errorText = "Cannot be empty!";
            } else if (label.equals(this.articleLinkField)) {
                // Validate link
                try {
                    URI.create(label.getText()).toURL();
                } catch (Exception e) {
                    ok = false;
                    errorVisible = true;
                    errorText = "Invalid URL!";
                }
            }
            
            var errorLabel = this.errorLabels.get(label);
            if (errorLabel != null) {
                errorLabel.setToolTipText(errorText);
                errorLabel.setVisible(errorVisible);
            }
        }
        
        return ok;
    }

    private void initDragNDrop() {
        if (!this.isDragNDropEnabled) {
            return;
        }
        
        this.articleCategoriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.articleCategoriesList.setDragEnabled(true);
        this.articleCategoriesList.setDropMode(DropMode.ON);
        
        this.availableCategoriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.availableCategoriesList.setDragEnabled(true);
        this.availableCategoriesList.setDropMode(DropMode.ON);
        
        this.articleCategoriesList.setTransferHandler(new CategoriesTransferHandler());
        this.availableCategoriesList.setTransferHandler(new CategoriesTransferHandler());
    }

    private class CategoriesTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(CategoriesTransferable.CATEGORY_LIST_FLAVOR);
        }
        
        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }
        
        @Override
        public Transferable createTransferable(JComponent c) {
            JList<Category> list = (JList<Category>) c;
            return new CategoriesTransferable(list.getSelectedValuesList());
        }
        
        @Override
        public boolean importData(TransferSupport support) {
            try {
                if (!canImport(support)) return false;
                
                List<Category> categories = (List<Category>) support.getTransferable().getTransferData(CategoriesTransferable.CATEGORY_LIST_FLAVOR);
                JList<Category> targetList = (JList<Category>) support.getComponent();
                System.out.println("Target List: " + targetList.getName());
                if (targetList == availableCategoriesList) {
                    removeCategoriesFromArticle(categories);
                } else if (targetList == articleCategoriesList) {
                    addCategoriesToArticle(categories);
                }
                
                return true;
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ArticlesView.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return false;
        }
    }
    
    private static class CategoriesTransferable implements Transferable {
        
        public static final DataFlavor CATEGORY_LIST_FLAVOR = new DataFlavor(List.class, "Category List");
        
        private final List<Category> categories;

        public CategoriesTransferable(List<Category> categories) {
            this.categories = categories;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{CATEGORY_LIST_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(CATEGORY_LIST_FLAVOR);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) {
            return categories;
        }

        public List<Category> getCategories() {
            return categories;
        }
    }
}
