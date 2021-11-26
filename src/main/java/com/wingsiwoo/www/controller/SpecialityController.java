package com.wingsiwoo.www.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.bo.SpecialityBo;
import com.wingsiwoo.www.entity.bo.SpecialityPageBo;
import com.wingsiwoo.www.service.SpecialityService;
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
@RequestMapping("/api/speciality")
public class SpecialityController {
    @Resource
    private SpecialityService specialityService;

    @GetMapping("/getAllSpecInfo")
    public CommonResult<Page<SpecialityPageBo>> getAllSpecInfo() {
        return CommonResult.operateSuccess(specialityService.getAllSpecInfo());
    }

    @PostMapping("/addSpeciality")
    public CommonResult<Void> addSpeciality(@Validated @RequestBody SpecialityBo specialityBo) {
        return CommonResult.autoResult(specialityService.addSpeciality(specialityBo));
    }

    @PostMapping("/deleteSpeciality")
    public CommonResult<Void> deleteSpeciality(@NotNull(message = "专业id不可为空") @RequestParam Integer id) {
        return CommonResult.autoResult(specialityService.deleteSpeciality(id));
    }

    @PostMapping("/updateSpeciality")
    public CommonResult<Void> updateSpeciality(@Validated @RequestBody SpecialityBo specialityBo) {
        return CommonResult.autoResult(specialityService.updateSpeciality(specialityBo));
    }
}

