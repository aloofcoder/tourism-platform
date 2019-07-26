package net.le.tourism.authority.configurate;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-05-17
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public class AutoCodeGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/tourism-authority/src/main/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
//        gc.setSwagger2(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setAuthor("韩乐");
        gc.setServiceName("I%sService");

        generator.setGlobalConfig(gc);
        // 配置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/tourism_platform?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC");
        dsc.setUsername("root");
        dsc.setPassword("root");
        generator.setDataSource(dsc);

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        // 全局大写命名
        stConfig.setCapitalMode(true)
                // 数据库表映射到实体的命名策略（默认下划线 当前使用驼峰）
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude("login_log")
                .setEntityLombokModel(true);
        generator.setStrategy(stConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("net.le.tourism.authority");
        pc.setEntity("pojo.entity");
        generator.setPackageInfo(pc);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/tourism-authority/src/main/resources/mapper/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);
        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        generator.setTemplate(tc);
        ///指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
        //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("templates/entity2.java");

        AutoGenerator mpg = new AutoGenerator();
        //配置自定义模板
        mpg.setTemplate(templateConfig);
        generator.execute();
    }
}
