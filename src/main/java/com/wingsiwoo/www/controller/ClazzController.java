package com.wingsiwoo.www.controller;


import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.service.ClazzService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@RestController
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

    /**
     * 导入班级学生信息
     */
    @PostMapping("/importClazzStudents")
    public CommonResult<Void> importClazzStudents(@NotNull(message = "文件不可为空") @RequestParam("file") MultipartFile file,
                                                  @NotNull(message = "课程id不可为空") @RequestParam("clazzId") Integer clazzId) {
        return CommonResult.autoResult(clazzService.importClazzStudents(file, clazzId));
    }

    /**
     * 导出班级学生信息模板
     */
    @GetMapping("/exportClazzTemplate")
    public ResponseEntity<byte[]> exportClazzTemplate() {
        return clazzService.exportClazzTemplate();
    }
}

