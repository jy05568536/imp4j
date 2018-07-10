package com.fermii.imp4j.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fermii.imp4j.common.util.POIUtil;
import com.fermii.imp4j.mapper.Imp4jMapper;
import com.fermii.imp4j.model.Imp4jData;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Imp4jService {

    @Autowired
    private Imp4jMapper imp4jMapper;

    public int impData(String tableName, MultipartFile file) throws Exception {
        File jsonFile;
        try {
            jsonFile = ResourceUtils.getFile("classpath:\\imp4j\\" + tableName + ".json");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("没有找到" + tableName + ".json配置文件");
        }
        String json = FileUtils.readFileToString(jsonFile);
        JSONObject config = JSON.parseObject(json);
        Imp4jData data = new Imp4jData();
        data.setTableName(config.getString("tableName"));
        JSONArray columns = config.getJSONArray("columns");
        int startRow = config.getIntValue("startRow");
        //取出excel原始数据
        List<String[]> rawDatas = POIUtil.readExcel(file, startRow);
        List<String[]> values = new ArrayList<>();
        rawDatas.forEach(e -> {
            String[] item = e;
            String[] result = new String[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                JSONArray citem = (JSONArray) columns.get(i);
                String val = citem.getString(1);
                if (isFormula(val)) {
                    // 公式特殊出处理
                    result[i] = item[getColumnNum(val)];
                } else {
                    result[i] = val;
                }
            }
            values.add(result);
        });
        columns.forEach(e -> {
            JSONArray item = (JSONArray) e;
            //添加列名
            data.getColumnNames().add(item.getString(0));
        });
        data.setValues(values);
        return imp4jMapper.replaceInsert(data);
    }

    private boolean isFormula(String str) {
        if (str.startsWith("{") && str.endsWith("}")) {
            return true;
        }
        return false;
    }

    /**
     * 获取列数值
     *
     * @param str
     * @return
     */
    private int getColumnNum(String str) {
        //去掉一头一尾大括号
        str = str.substring(1, str.length() - 1);
        return Integer.parseInt(str.substring(str.indexOf("$") + 1));
    }
}
