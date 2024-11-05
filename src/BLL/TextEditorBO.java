package BLL;

import java.sql.SQLException;
import java.util.ArrayList;

import DAL.TextFileDao;

public class TextEditorBO {
    private TextFileDao fileDAO;

    public TextEditorBO() throws SQLException, ClassNotFoundException {
        fileDAO = new TextFileDao();
    }

    public ArrayList<String> getFileNames() {
        return fileDAO.getFileNames();
    }

    public String getFileContent(String fileName) {
        return fileDAO.getFileContent(fileName);
    }

    public void saveToDatabase(String fileName, String content) throws SQLException {
        fileDAO.saveToDatabase(fileName, content);
    }

    public void deleteFileFromDatabase(String fileName) throws SQLException {
        fileDAO.deleteFileFromDatabase(fileName);
    }

    public String calculateHash(String content) {
        // Simple hash calculation (can use any preferred hash algorithm)
        return Integer.toHexString(content.hashCode());
    }
}
