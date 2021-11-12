package com.wingsiwoo.www.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.po.College;
import com.wingsiwoo.www.service.CollegeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Controller
@RequestMapping("/api/college")
public class CollegeController {
    @Resource
    private CollegeService collegeService;

    @GetMapping("/getAllCollegeInfo")
    public CommonResult<Page<College>> getAllCollegeInfo() {
        return CommonResult.operateSuccess(collegeService.getAllCollegeInfo());
    }
}

