package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.AddressMapper;
import com.wingsiwoo.www.entity.po.Address;
import com.wingsiwoo.www.service.AddressService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WingsiWoo
 * @date 2021/11/27
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    @Override
    public List<Integer> getAllBuildings() {
        List<Address> addresses = addressMapper.selectList(null);
        List<Integer> buildings = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(addresses)) {
            buildings = addresses.stream().sorted(Comparator.comparing(Address::getBuilding)).map(Address::getBuilding).distinct().collect(Collectors.toList());
        }
        return buildings;
    }

    @Override
    public List<Integer> getBuildingClazz(Integer building) {
        List<Address> addresses = addressMapper.getByBuilding(building);
        Assert.isTrue(CollectionUtils.isNotEmpty(addresses), "该教学楼不存在");
        return addresses.stream().sorted(Comparator.comparing(Address::getNum)).map(Address::getNum).collect(Collectors.toList());
    }
}
