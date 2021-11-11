package com.wingsiwoo.www.service;

import com.wingsiwoo.www.entity.po.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

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
}
