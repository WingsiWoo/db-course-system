package com.wingsiwoo.www.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.bo.YearBo;
import com.wingsiwoo.www.entity.po.Year;
import com.wingsiwoo.www.service.YearService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/11/27
 */
@RestController
@RequestMapping("/api/year")
public class YearController {
    @Resource
    private YearService yearService;

    @GetMapping("/getYearInfoInPage")
    public CommonResult<Page<Year>> getYearInfoInPage() {
        return CommonResult.operateSuccess(yearService.getYearInfoInPage());
    }

    @PostMapping("/addYear")
    public CommonResult<Void> addYear(@NotNull(message = "年级号不能为空") @RequestParam Integer grade) {
        return CommonResult.autoResult(yearService.addYear(grade));
    }

    @PostMapping("/updateYear")
    public CommonResult<Void> updateYear(@Validated @RequestBody YearBo yearBo) {
        return CommonResult.autoResult(yearService.updateYear(yearBo));
    }
}
