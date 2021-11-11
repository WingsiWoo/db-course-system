package com.wingsiwoo.www.constant;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
public class SexConstant {
    public static final Boolean SEX_MAN = false;
    public static final Boolean SEX_WOMAN = true;

    public static final String SEX_CH_MAN = "男";
    public static final String SEX_CH_WOMAN = "女";

    public static Boolean transform(String sex) {
        switch (sex) {
            case SEX_CH_MAN:
                return SEX_MAN;
            case SEX_CH_WOMAN:
                return SEX_WOMAN;
            default:
                throw new IllegalArgumentException("参数不合法");
        }
    }

    public static String transform(Integer sex) {
        switch (sex) {
            case 0:
                return SEX_CH_MAN;
            case 1:
                return SEX_CH_WOMAN;
            default:
                throw new IllegalArgumentException("参数不合法");
        }
    }
}
