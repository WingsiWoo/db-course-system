package com.wingsiwoo.www.controller;


import com.wingsiwoo.www.entity.bo.UserExcelBo;
import com.wingsiwoo.www.service.ClazzService;
import com.wingsiwoo.www.util.ExcelUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Controller
@RequestMapping("/api/clazz")
public class ClazzController {
    @Resource
    private ClazzService clazzService;

    /**
     * 导出班级学生信息
     */
    @GetMapping("/exportClazzInfo")
    public ResponseEntity<byte[]> exportClazzInfo(@NotNull @RequestParam("clazzId") Integer clazzId) {
        return clazzService.exportClazzInfo(clazzId);
    }
}

