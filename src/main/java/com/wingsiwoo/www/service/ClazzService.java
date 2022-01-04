package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.ClazzNameBo;
import com.wingsiwoo.www.entity.po.Clazz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
public interface ClazzService extends IService<Clazz> {
    /**
     * 导出班级学生信息
     *
     * @param clazzId 班级id
     * @return 班级学生信息excel
     */
    ResponseEntity<byte[]> exportClazzInfo(Integer clazzId);

    /**
     * 导入班级学生信息
     *
     * @param file    学生信息文件
     * @param clazzId 班级id
     * @return 导入结果
     */
    boolean importClazzStudents(MultipartFile file, Integer clazzId);

    /**
     * 导出班级学生信息模板
     *
     * @return ResponseEntity<byte [ ]>
     */
    ResponseEntity<byte[]> exportClazzTemplate();

    /**
     * 获取所有班级名称
     *
     * @return 班级名称列表
     */
    List<ClazzNameBo> getAllClazzName();

    /**
     * 分页获取所有班级信息
     */
    Page<ClazzNameBo> getAllClazzInPage();
}
