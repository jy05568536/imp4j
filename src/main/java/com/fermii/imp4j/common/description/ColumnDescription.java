package com.fermii.imp4j.common.description;

/**
 * 列数据描述
 */
public class ColumnDescription {
    private String name; //对应列名称
    private String type; //对应类型
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
