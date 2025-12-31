package Java课程实验FX;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SpacecraftDaoImpl implements SpacecraftDao {
        DBUtil db =new DBUtil();
        public SpacecraftDaoImpl() {
            db.getConnection();
        }
        @Override
        public boolean addSpacecraft(Spacecraft spacecraft) throws Exception {
            String sql="insert into spacecraft(name, launchTime, launchSite, Summary) values (?,?,?,?)";
            String[] param= {spacecraft.getName(),spacecraft.getLaunchTime()+"",
                    spacecraft.getLaunchSite(),spacecraft.getSummary()};
            int x=db.executeUpdate(sql, param);
            return x>0;
        }
        @Override
        public boolean updateSpacecraft(Spacecraft spacecraft) throws Exception {
            String sql ="update spacecraft set name=?,launchTime=?,launchSite=?,summary=? where id=?";
            String[] param= {spacecraft.getName(),spacecraft.getLaunchTime()+"",
                    spacecraft.getLaunchSite(),spacecraft.getSummary(),spacecraft.getId()+""};
            int x=db.executeUpdate(sql, param);
            return x>0;
        }
        @Override
        public boolean deleteSpacecraft(int id) throws Exception {
            String sql="delete from spacecraft where id=?";
            String[] param= {id+""};
            int x=db.executeUpdate(sql, param);
            return x>0;
        }
        @Override
        public Spacecraft getSpacecraft(int id) throws Exception {
            Spacecraft spacecraft=null;
            String sql="select * from spacecraft where id=?";
            String[] param= {id+""};
            ResultSet rs=db.executeQuery(sql, param);
            if(rs.next()) {
                spacecraft=new Spacecraft();
                spacecraft.setId(rs.getInt(1));
                spacecraft.setName(rs.getString(2));
                spacecraft.setLaunchTime(rs.getTimestamp(3));
                spacecraft.setLaunchSite(rs.getString(4));
                spacecraft.setSummary(rs.getString(5));
            }
            return spacecraft;
        }
        @Override
        public List<Spacecraft> getAll() throws Exception {
            List<Spacecraft> list=new ArrayList<Spacecraft>();
            String sql="select * from spacecraft";
            String[] param= {};
            ResultSet rs=db.executeQuery(sql, param);
            while(rs.next()) {
                Spacecraft spacecraft=new Spacecraft();
                spacecraft.setId(rs.getInt(1));
                spacecraft.setName(rs.getString(2));
                spacecraft.setLaunchTime(rs.getTimestamp(3));
                spacecraft.setLaunchSite(rs.getString(4));
                spacecraft.setSummary(rs.getString(5));
                list.add(spacecraft);
            }
            return list;
        }
        @Override
        public List<Spacecraft> getSpacecraftByName(String name) throws Exception {
            List<Spacecraft> list=new ArrayList<Spacecraft>();
            String sql="select * from spacecraft where name like ?";
            String[] param= {"%"+name+"%"};
            ResultSet rs=db.executeQuery(sql, param);
            while(rs.next()) {
                Spacecraft spacecraft=new Spacecraft();
                spacecraft.setId(rs.getInt(1));
                spacecraft.setName(rs.getString(2));
                spacecraft.setLaunchTime(rs.getTimestamp(3));
                spacecraft.setLaunchSite(rs.getString(4));
                spacecraft.setSummary(rs.getString(5));
                list.add(spacecraft);
            }
            return list;
        }
    }
