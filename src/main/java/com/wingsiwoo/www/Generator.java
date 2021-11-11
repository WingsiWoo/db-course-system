package com.wingsiwoo.www;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * @author WingsiWoo
 * @date 2021/11/10
 */
public class Generator {
    /**
     * 配置文件信息
     */
    private static final ResourceBundle RB_MYBATIS_PLUS = ResourceBundle.getBundle("mybatis-plus");

    /**
     * java文件目录
     */
    private static final String JAVA_SOURCE_PATH = System.getProperty("user.dir") + "/src/main/java";

    /**
     * xml映射文件目录
     */
    private static final String XML_SOURCE_PATH = System.getProperty("user.dir") + "/src/main/resources/mapper/";

    public static void main(String[] args) throws InterruptedException {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        mpg.setGlobalConfig(getGlobalConfig(JAVA_SOURCE_PATH));

        // 数据源配置
        mpg.setDataSource(getDataSourceConfig());

        // 数据库表配置
        mpg.setStrategy(getStrategyConfig(RB_MYBATIS_PLUS.getString("tableNames").split(",")));

        // 配置包结构
        String controllerPag = RB_MYBATIS_PLUS.getString("controller.package");
        String servicePag = RB_MYBATIS_PLUS.getString("service.package");
        String serviceImplPag = RB_MYBATIS_PLUS.getString("service.impl.package");
        String entityPag = RB_MYBATIS_PLUS.getString("entity.package");
        String mapperPag = RB_MYBATIS_PLUS.getString("mapper.package");
        mpg.setPackageInfo(getPackageConfig(controllerPag, servicePag, serviceImplPag,
                entityPag, mapperPag));

        // mapper.xml配置
        mpg.setCfg(getInjectionConfig());

        // 删除默认的xml生成
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        if (StringUtils.isBlank(controllerPag)) {
            tc.setController(null);
        }
        if (StringUtils.isBlank(servicePag)) {
            tc.setService(null);
        }
        if (StringUtils.isBlank(serviceImplPag)) {
            tc.setServiceImpl(null);
        }
        if (StringUtils.isBlank(entityPag)) {
            tc.setEntity(null);
        }
        if (StringUtils.isBlank(mapperPag)) {
            tc.setMapper(null);
        }

        mpg.setTemplate(tc);

        // 执行
        mpg.execute();
    }

    private static InjectionConfig getInjectionConfig() {
        // 注入自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>(2);
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-RB_MYBATIS_PLUS");
                this.setMap(map);
            }
        };

        // 修改xml生成目录
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return XML_SOURCE_PATH + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static PackageConfig getPackageConfig(String controllerPackage,
                                                  String servicPackage,
                                                  String serviceImplPackage,
                                                  String entityPackage,
                                                  String mapperPackage) {
        PackageConfig pc = new PackageConfig();
        pc.setParent(RB_MYBATIS_PLUS.getString("parent.package"));
        if (StringUtils.isNotBlank(controllerPackage)) {
            pc.setController(controllerPackage);
        }
        if (StringUtils.isNotBlank(servicPackage)) {
            pc.setService(servicPackage);
        }
        if (StringUtils.isNotBlank(serviceImplPackage)) {
            pc.setServiceImpl(serviceImplPackage);
        }
        if (StringUtils.isNotBlank(entityPackage)) {
            pc.setEntity(entityPackage);
        }
        if (StringUtils.isNotBlank(mapperPackage)) {
            pc.setMapper(mapperPackage);
        }
        return pc;
    }

    private static StrategyConfig getStrategyConfig(String... tableNames) {
        StrategyConfig strategy = new StrategyConfig();
        // 驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tableNames);
        return strategy;
    }

    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(RB_MYBATIS_PLUS.getString("mysql.driver"));
        dsc.setUrl(RB_MYBATIS_PLUS.getString("mysql.url"));
        dsc.setUsername(RB_MYBATIS_PLUS.getString("mysql.user"));
        dsc.setPassword(RB_MYBATIS_PLUS.getString("mysql.pwd"));
        return dsc;
    }

    private static GlobalConfig getGlobalConfig(String outPutDir) {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outPutDir);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor(RB_MYBATIS_PLUS.getString("author"));
        // 自定义文件命名，%s：文件名
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        return gc;
    }
}
