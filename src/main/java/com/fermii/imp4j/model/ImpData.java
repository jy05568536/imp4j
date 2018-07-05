package com.fermii.imp4j.model;

import lombok.Data;

import java.util.List;

@Data
public class ImpData {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 列名
     */
    private List<String> columnNames;
    /**
     * 数据
     */
    private List<String[]> datas;
}
