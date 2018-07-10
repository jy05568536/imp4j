package com.fermii.imp4j.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Imp4jData {

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
    private List<String[]> values;

    public Imp4jData() {
        columnNames = new ArrayList<>();
        values = new ArrayList<>();
    }
}
