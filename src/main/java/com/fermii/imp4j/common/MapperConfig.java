package com.fermii.imp4j.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.fermii.imp4j.mapper"})
public class MapperConfig {
}
