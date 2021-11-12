package com.wingsiwoo.www.service;

import com.wingsiwoo.www.entity.po.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
public interface ClazzService extends IService<Clazz> {
    /**
     * 导出班级学生信息
     * @param clazzId 班级id
     * @return 班级学生信息excel
     */
    ResponseEntity<byte[]> exportClazzInfo(Integer clazzId);

    /**
     * 导入班级学生信息
     * @param file 学生信息文件
     * @param clazzId 班级id
     * @return 导入结果
     */
    boolean importClazzStudents(MultipartFile file, Integer clazzId);

    /**
     * 导出班级学生信息模板
     * @return ResponseEntity<byte[]>
     */
    ResponseEntity<byte[]> exportClazzTemplate();
}
