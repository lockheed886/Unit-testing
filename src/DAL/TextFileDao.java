package DAL;

import java.sql.*;
import java.util.ArrayList;

public class TextFileDao {
    private Connection con;

    public TextFileDao() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/scd_project", "root", "");
    }

    public ArrayList<String> getFileNames() {
        ArrayList<String> fileList = new ArrayList<>();
        String query = "SELECT fileName FROM text_file";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                fileList.add(rs.getString("fileName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return fileList;
    }

    public String getFileContent(String fileName) {
        String fileContent = null;
        String query = "SELECT fileContent FROM text_file WHERE fileName = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, fileName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                fileContent = rs.getString("fileContent");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return fileContent;
    }

    public void saveToDatabase(String fileName, String content) throws SQLException {
        String query = "INSERT INTO text_file (fileName, fileContent) VALUES (?, ?) ON DUPLICATE KEY UPDATE fileContent = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, fileName);
            stmt.setString(2, content);
            stmt.setString(3, content);
            stmt.executeUpdate();
        }
    }

    public void deleteFileFromDatabase(String fileName) throws SQLException {
        String query = "DELETE FROM text_file WHERE fileName = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, fileName);
            stmt.executeUpdate();
        }
    }
}
