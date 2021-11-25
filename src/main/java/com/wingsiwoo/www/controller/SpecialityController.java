package com.wingsiwoo.www.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.bo.SpecialityPageBo;
import com.wingsiwoo.www.service.SpecialityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@RestController
@RequestMapping("/api/speciality")
public class SpecialityController {
    @Resource
    private SpecialityService specialityService;

    @GetMapping("/getAllSpecInfo")
    public CommonResult<Page<SpecialityPageBo>> getAllSpecInfo() {
        return CommonResult.operateSuccess(specialityService.getAllSpecInfo());
    }
}

