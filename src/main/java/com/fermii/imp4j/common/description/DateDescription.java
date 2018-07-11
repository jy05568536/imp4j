package com.fermii.imp4j.common.description;

import java.util.HashMap;

/**
 * 描述文件数据结构
 */
public class DateDescription {
    private String tableName;
    private int dateStartRow;//数据起始行，同excel，从1开始
    private Class className; //数据对应对象类名
    private HashMap<Integer, ColumnDescription> columnMapping;//每列数据映射

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getDateStartRow() {
        return dateStartRow;
    }

    public void setDateStartRow(int dateStartRow) {
        this.dateStartRow = dateStartRow;
    }

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public HashMap<Integer, ColumnDescription> getColumnMapping() {
        return columnMapping;
    }

    public void setColumnMapping(HashMap<Integer, ColumnDescription> columnMapping) {
        this.columnMapping = columnMapping;
    }
}
