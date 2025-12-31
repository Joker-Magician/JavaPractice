package museum.dao;

import museum.model.Architecture;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArchitectureDAO {
    private DatabaseManager dbManager;

    public ArchitectureDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }

    public boolean create(Architecture arch) {
        String query = "INSERT INTO architecture (name, dynasty, location, type, description, image_path, year_built, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, arch.getName());
            pstmt.setString(2, arch.getDynasty());
            pstmt.setString(3, arch.getLocation());
            pstmt.setString(4, arch.getType());
            pstmt.setString(5, arch.getDescription());
            pstmt.setString(6, arch.getImagePath());
            pstmt.setInt(7, arch.getYearBuilt());
            pstmt.setInt(8, arch.getCreatedBy());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Architecture> readAll() {
        List<Architecture> list = new ArrayList<>();
        String query = "SELECT * FROM architecture";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                list.add(extractArchitectureFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Architecture> search(String keyword) {
        List<Architecture> list = new ArrayList<>();
        String query = "SELECT * FROM architecture WHERE name LIKE ? OR dynasty LIKE ? OR type LIKE ? OR location LIKE ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractArchitectureFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Architecture arch) {
        String query = "UPDATE architecture SET name=?, dynasty=?, location=?, type=?, description=?, image_path=?, year_built=? WHERE architecture_id=?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, arch.getName());
            pstmt.setString(2, arch.getDynasty());
            pstmt.setString(3, arch.getLocation());
            pstmt.setString(4, arch.getType());
            pstmt.setString(5, arch.getDescription());
            pstmt.setString(6, arch.getImagePath());
            pstmt.setInt(7, arch.getYearBuilt());
            pstmt.setInt(8, arch.getArchitectureId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int architectureId) {
        String query = "DELETE FROM architecture WHERE architecture_id=?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, architectureId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Architecture extractArchitectureFromResultSet(ResultSet rs) throws SQLException {
        Architecture a = new Architecture();
        a.setArchitectureId(rs.getInt("architecture_id"));
        a.setName(rs.getString("name"));
        a.setDynasty(rs.getString("dynasty"));
        a.setLocation(rs.getString("location"));
        a.setType(rs.getString("type"));
        a.setDescription(rs.getString("description"));
        a.setImagePath(rs.getString("image_path"));
        a.setYearBuilt(rs.getInt("year_built"));
        a.setCreatedBy(rs.getInt("created_by"));
        return a;
    }
}
