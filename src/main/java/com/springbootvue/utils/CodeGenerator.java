package com.springbootvue.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args){
        generator();
    }

    private static void generator(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/springbootvue", "root", "password")
                .globalConfig(builder -> {
                    builder.author("林于哲") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/linyuzhe/Intellij/SpringVueJava/springboot/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.springbootvue") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/linyuzhe/Intellij/SpringVueJava/springboot/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
//                    builder.mapperBuilder().enableMapperAnnotation().build(); 會自動加上 mapper 註解
                    builder.controllerBuilder().enableHyphenStyle().enableRestStyle();
                    builder.addInclude("sys_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
