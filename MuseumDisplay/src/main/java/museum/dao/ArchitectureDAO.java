package museum.dao;

import museum.entity.Architecture;
import museum.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArchitectureDAO {
    private DBUtil dbManager;

    public ArchitectureDAO() { this.dbManager = DBUtil.getInstance(); }

    public boolean create(Architecture arch) {
        String query = "insert into architecture (name, dynasty, location, type, description, image_path, year_built, created_by) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, arch.getName());
            pstmt.setString(2, arch.getDynasty());
            pstmt.setString(3, arch.getLocation());
            pstmt.setString(4, arch.getType());
            pstmt.setString(5, arch.getDescription());
            pstmt.setString(6, arch.getImagePath());
            pstmt.setInt(7, arch.getYearBuilt());
            pstmt.setInt(8, arch.getCreatedBy());

            return (pstmt.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Architecture> getAll() {
        List<Architecture> list = new ArrayList<>();
        String query = "select * from architecture";
        try(Connection conn = dbManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            while (rs.next()) {
            list.add(extractArchitectureFromReseltSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Architecture> searchByName(String keyword) {
        List<Architecture> list = new ArrayList<>();
        String query = "select * from architecture where name like ? or dynasty like ? or type like ? or location like ?";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);

            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractArchitectureFromReseltSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Architecture arch) {
        String query = "update architecture set name=?, dynasty=?, location=?, type=?, description=?, image_path=?, year_built=? where architecture_id=?";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, arch.getName());
            pstmt.setString(2, arch.getDynasty());
            pstmt.setString(3, arch.getLocation());
            pstmt.setString(4, arch.getType());
            pstmt.setString(5, arch.getDescription());
            pstmt.setString(6, arch.getImagePath());
            pstmt.setInt(7, arch.getYearBuilt());
            pstmt.setInt(8, arch.getArchitectureID());

            return (pstmt.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int architectureId) {
        String query = "delete from architecture where architecture_id=?";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, architectureId);
            return (pstmt.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Architecture extractArchitectureFromReseltSet(ResultSet rs) throws SQLException {
    Architecture a = new Architecture();
    a.setArchitectureID(rs.getInt("architecture_id"));
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
