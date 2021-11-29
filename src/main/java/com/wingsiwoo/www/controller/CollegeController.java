package com.wingsiwoo.www.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.bo.CollegeBo;
import com.wingsiwoo.www.entity.po.College;
import com.wingsiwoo.www.service.CollegeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/college")
public class CollegeController {
    @Resource
    private CollegeService collegeService;

    @GetMapping("/getAllCollegeInfo")
    public CommonResult<Page<College>> getAllCollegeInfo() {
        return CommonResult.operateSuccess(collegeService.getAllCollegeInfo());
    }

    @PostMapping("/addCollege")
    public CommonResult<Void> addCollege(@Validated @RequestBody CollegeBo collegeBo) {
        return CommonResult.autoResult(collegeService.addCollege(collegeBo));
    }

    @PostMapping("/deleteCollege")
    public CommonResult<Void> deleteCollege(@NotNull(message = "学院id不可为空") @RequestParam Integer id) {
        return CommonResult.autoResult(collegeService.deleteCollege(id));
    }

    @PostMapping("/updateCollege")
    public CommonResult<Void> updateCollege(@RequestBody College college) {
        return CommonResult.autoResult(collegeService.updateCollege(college));
    }
}

