package museum.service;

import museum.dao.HeritageDAO;
import museum.entity.Heritage;
import museum.exception.DatabaseException;
import museum.exception.ValidationException;

import java.util.List;

/**
 * 非遗服务类
 * 负责非遗项目相关的业务逻辑
 */
public class HeritageService {
    
    private HeritageDAO heritageDAO;
    
    public HeritageService() {
        this.heritageDAO = new HeritageDAO();
    }
    
    /**
     * 获取所有非遗项目
     * @return 非遗项目列表
     * @throws DatabaseException 数据库操作失败
     */
    public List<Heritage> getAllHeritage() throws DatabaseException {
        try {
            return heritageDAO.getAll();
        } catch (Exception e) {
            throw new DatabaseException("获取非遗列表失败", e);
        }
    }
    
    /**
     * 搜索非遗项目
     * @param keyword 搜索关键词
     * @return 搜索结果列表
     * @throws DatabaseException 数据库操作失败
     */
    public List<Heritage> searchHeritage(String keyword) throws DatabaseException {
        // 如果关键词为空，返回所有项目
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllHeritage();
        }
        
        try {
            return heritageDAO.searchByName(keyword);
        } catch (Exception e) {
            throw new DatabaseException("搜索非遗项目失败", e);
        }
    }
    
    /**
     * 创建非遗项目
     * @param heritage 非遗项目对象
     * @return 是否创建成功
     * @throws ValidationException 验证失败
     * @throws DatabaseException 数据库操作失败
     */
    public boolean createHeritage(Heritage heritage) 
            throws ValidationException, DatabaseException {
        // 验证输入
        validateHeritage(heritage);
        
        try {
            return heritageDAO.create(heritage);
        } catch (Exception e) {
            throw new DatabaseException("创建非遗项目失败", e);
        }
    }
    
    /**
     * 更新非遗项目
     * @param heritage 非遗项目对象
     * @return 是否更新成功
     * @throws ValidationException 验证失败
     * @throws DatabaseException 数据库操作失败
     */
    public boolean updateHeritage(Heritage heritage) 
            throws ValidationException, DatabaseException {
        validateHeritage(heritage);
        
        try {
            return heritageDAO.update(heritage);
        } catch (Exception e) {
            throw new DatabaseException("更新非遗项目失败", e);
        }
    }
    
    /**
     * 删除非遗项目
     * @param heritageId 非遗项目ID
     * @return 是否删除成功
     * @throws ValidationException 验证失败
     * @throws DatabaseException 数据库操作失败
     */
    public boolean deleteHeritage(int heritageId) 
            throws ValidationException, DatabaseException {
        if (heritageId <= 0) {
            throw new ValidationException("无效的非遗项目ID");
        }
        
        try {
            return heritageDAO.delete(heritageId);
        } catch (Exception e) {
            throw new DatabaseException("删除非遗项目失败", e);
        }
    }
    
    /**
     * 验证非遗项目对象
     */
    private void validateHeritage(Heritage heritage) throws ValidationException {
        if (heritage == null) {
            throw new ValidationException("非遗项目不能为空");
        }
        if (heritage.getName() == null || heritage.getName().trim().isEmpty()) {
            throw new ValidationException("非遗项目名称不能为空");
        }
    }
}
