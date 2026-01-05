package museum.service;

import museum.dao.ArchitectureDAO;
import museum.entity.Architecture;
import museum.exception.DatabaseException;
import museum.exception.ValidationException;

import java.util.List;

/**
 * 古建筑服务类
 * 负责古建筑项目相关的业务逻辑
 */
public class ArchitectureService {
    
    private ArchitectureDAO architectureDAO;
    
    public ArchitectureService() {
        this.architectureDAO = new ArchitectureDAO();
    }
    
    /**
     * 获取所有古建筑项目
     * @return 古建筑项目列表
     * @throws DatabaseException 数据库操作失败
     */
    public List<Architecture> getAllArchitecture() throws DatabaseException {
        try {
            return architectureDAO.getAll();
        } catch (Exception e) {
            throw new DatabaseException("获取古建筑列表失败", e);
        }
    }
    
    /**
     * 搜索古建筑项目
     * @param keyword 搜索关键词
     * @return 搜索结果列表
     * @throws DatabaseException 数据库操作失败
     */
    public List<Architecture> searchArchitecture(String keyword) 
            throws DatabaseException {
        // 如果关键词为空，返回所有项目
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllArchitecture();
        }
        
        try {
            return architectureDAO.searchByName(keyword);
        } catch (Exception e) {
            throw new DatabaseException("搜索古建筑项目失败", e);
        }
    }
    
    /**
     * 创建古建筑项目
     * @param architecture 古建筑项目对象
     * @return 是否创建成功
     * @throws ValidationException 验证失败
     * @throws DatabaseException 数据库操作失败
     */
    public boolean createArchitecture(Architecture architecture) 
            throws ValidationException, DatabaseException {
        // 验证输入
        validateArchitecture(architecture);
        
        try {
            return architectureDAO.create(architecture);
        } catch (Exception e) {
            throw new DatabaseException("创建古建筑项目失败", e);
        }
    }
    
    /**
     * 更新古建筑项目
     * @param architecture 古建筑项目对象
     * @return 是否更新成功
     * @throws ValidationException 验证失败
     * @throws DatabaseException 数据库操作失败
     */
    public boolean updateArchitecture(Architecture architecture) 
            throws ValidationException, DatabaseException {
        validateArchitecture(architecture);
        
        try {
            return architectureDAO.update(architecture);
        } catch (Exception e) {
            throw new DatabaseException("更新古建筑项目失败", e);
        }
    }
    
    /**
     * 删除古建筑项目
     * @param architectureId 古建筑项目ID
     * @return 是否删除成功
     * @throws ValidationException 验证失败
     * @throws DatabaseException 数据库操作失败
     */
    public boolean deleteArchitecture(int architectureId) 
            throws ValidationException, DatabaseException {
        if (architectureId <= 0) {
            throw new ValidationException("无效的古建筑项目ID");
        }
        
        try {
            return architectureDAO.delete(architectureId);
        } catch (Exception e) {
            throw new DatabaseException("删除古建筑项目失败", e);
        }
    }
    
    /**
     * 验证古建筑项目对象
     */
    private void validateArchitecture(Architecture architecture) 
            throws ValidationException {
        if (architecture == null) {
            throw new ValidationException("古建筑项目不能为空");
        }
        if (architecture.getName() == null || 
            architecture.getName().trim().isEmpty()) {
            throw new ValidationException("古建筑项目名称不能为空");
        }
    }
}
