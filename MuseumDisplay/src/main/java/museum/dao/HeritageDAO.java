package museum.dao;

import museum.model.Heritage;
import museum.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeritageDAO {
    private DBUtil dbManager;

    public HeritageDAO() {
        this.dbManager = DBUtil.getInstance();
    }

    public boolean create(Heritage heritage) {
        String query = "INSERT INTO heritage (name, category, region, description, image_path, year_recognized, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, heritage.getName());
            pstmt.setString(2, heritage.getCategory());
            pstmt.setString(3, heritage.getRegion());
            pstmt.setString(4, heritage.getDescription());
            pstmt.setString(5, heritage.getImagePath());
            pstmt.setInt(6, heritage.getYearRecognized());
            pstmt.setInt(7, heritage.getCreatedBy());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Heritage> readAll() {
        List<Heritage> list = new ArrayList<>();
        String query = "SELECT * FROM heritage";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(extractHeritageFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Heritage> search(String keyword) {
        List<Heritage> list = new ArrayList<>();
        String query = "SELECT * FROM heritage WHERE name LIKE ? OR category LIKE ? OR region LIKE ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractHeritageFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Heritage heritage) {
        String query = "UPDATE heritage SET name=?, category=?, region=?, description=?, image_path=?, year_recognized=? WHERE heritage_id=?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, heritage.getName());
            pstmt.setString(2, heritage.getCategory());
            pstmt.setString(3, heritage.getRegion());
            pstmt.setString(4, heritage.getDescription());
            pstmt.setString(5, heritage.getImagePath());
            pstmt.setInt(6, heritage.getYearRecognized());
            pstmt.setInt(7, heritage.getHeritageId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int heritageId) {
        String query = "DELETE FROM heritage WHERE heritage_id=?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, heritageId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Heritage extractHeritageFromResultSet(ResultSet rs) throws SQLException {
        Heritage h = new Heritage();
        h.setHeritageId(rs.getInt("heritage_id"));
        h.setName(rs.getString("name"));
        h.setCategory(rs.getString("category"));
        h.setRegion(rs.getString("region"));
        h.setDescription(rs.getString("description"));
        h.setImagePath(rs.getString("image_path"));
        h.setYearRecognized(rs.getInt("year_recognized"));
        h.setCreatedBy(rs.getInt("created_by"));
        // h.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime()); // Optional handling
        return h;
    }
}
