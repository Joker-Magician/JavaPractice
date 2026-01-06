package museum.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 图片资源管理工具类
 * 负责图片的上传、保存和路径管理
 */
public class ImageManager {
    // 图片存储的相对目录
    private static final String STORAGE_DIR = "data/images/";

    /**
     * 保存图片文件到本地存储目录
     * @param sourceFile 源文件
     * @return 存储后的相对路径
     * @throws IOException IO异常
     */
    public static String saveImage(File sourceFile) throws IOException {
        if (sourceFile == null || !sourceFile.exists()) {
            throw new IOException("源文件不存在");
        }

        // 1. 确保存储目录存在
        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created && !dir.exists()) {
                throw new IOException("无法创建存储目录: " + STORAGE_DIR);
            }
        }

        // 2. 生成唯一文件名 (防止覆盖)
        String ext = getFileExtension(sourceFile.getName());
        String newName = UUID.randomUUID().toString() + (ext.isEmpty() ? ".jpg" : "." + ext);
        File destFile = new File(dir, newName);

        // 3. 复制文件
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // 4. 返回相对路径 (注意：在不同系统下路径分隔符可能不同，统一使用 / 方便存储和读取)
        // 使用 new File(STORAGE_DIR, newName).getPath() 可能会使用反斜杠，这里手动拼接
        return STORAGE_DIR + newName;
    }

    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return 扩展名（不带点），如果没有则返回空字符串
     */
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
}
