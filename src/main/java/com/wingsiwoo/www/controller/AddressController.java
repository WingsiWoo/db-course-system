package com.wingsiwoo.www.controller;

import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WingsiWoo
 * @date 2021/11/27
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    @GetMapping("/getAllBuildings")
    public CommonResult<List<Integer>> getAllBuildings() {
        return CommonResult.operateSuccess(addressService.getAllBuildings());
    }

    @GetMapping("/getBuildingClazz")
    public CommonResult<List<Integer>> getBuildingClazz(@NotNull(message = "楼号不能为空") @RequestParam Integer building) {
        return CommonResult.operateSuccess(addressService.getBuildingClazz(building));
    }
}
