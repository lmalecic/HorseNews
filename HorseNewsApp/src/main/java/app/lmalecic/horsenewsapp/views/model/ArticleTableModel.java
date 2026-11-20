/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp.views.model;

import app.lmalecic.model.Article;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lmalecic
 */
public class ArticleTableModel extends AbstractTableModel {
    
    private static final String[] COLUMNS = { "Id", "Title", "Creator", "Categories", "Date Published", "Description" };
    private List<Article> articles;
    
    public ArticleTableModel(List<Article> articles) {
        this.articles = articles;
    }
    
    public void setArticles(List<Article> articles) {
        this.articles = articles;
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return articles.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Article article = articles.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return article.getId();
            case 1:
                return article.getTitle();
            case 2:
                return article.getCreator().getName();
            case 3:
                return article.getCategoriesString();
            case 4:
                return article.getDatePublished();
            case 5:
                return article.getDescription();
            default:
                throw new RuntimeException("No such column");
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
    
    public List<Article> getArticles() {
        return this.articles;
    }
}
