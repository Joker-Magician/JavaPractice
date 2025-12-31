package Java课程实验FX;

import java.util.List;

public interface SpacecraftDao {
    public boolean addSpacecraft(Spacecraft spacecraft)throws Exception;
    public boolean updateSpacecraft(Spacecraft spacecraft)throws Exception;
    public boolean deleteSpacecraft(int id)throws Exception;
    public Spacecraft getSpacecraft(int id)throws Exception;
    public List<Spacecraft> getSpacecraftByName(String name)throws Exception;
    public List<Spacecraft> getAll()throws Exception;
}
