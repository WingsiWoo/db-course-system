package com.wingsiwoo.www.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.bo.ClazzNameBo;
import com.wingsiwoo.www.service.ClazzService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

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


    /**
     * 获取所有班级名称
     *
     * @return CommonResult<List < ClazzNameBo>>
     */
    @GetMapping("/getAllClazzName")
    public CommonResult<List<ClazzNameBo>> getAllClazzName() {
        return CommonResult.operateSuccess(clazzService.getAllClazzName());
    }

    @GetMapping("/getAllClazzInPage")
    public CommonResult<Page<ClazzNameBo>> getAllClazzInPage() {
        return CommonResult.operateSuccess(clazzService.getAllClazzInPage());
    }
}

