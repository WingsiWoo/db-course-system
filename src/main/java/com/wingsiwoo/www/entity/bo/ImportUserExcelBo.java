package com.wingsiwoo.www.entity.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
@Data
public class ImportUserExcelBo {
    @NotNull(message = "文件不可为空")
    private MultipartFile file;

    @NotNull(message = "角色id不可为空")
    private Integer roleId;
}
