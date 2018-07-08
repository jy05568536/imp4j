package com.fermii.imp4j.common.csv;

import com.csvreader.CsvReader;
import com.fermii.imp4j.common.description.ColumnDescription;
import com.fermii.imp4j.common.description.DateDescription;
import com.fermii.imp4j.common.utility.FileEncodingUtility;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.fermii.imp4j.common.excel.ExcelParseUtil.Trim_str;
import static com.fermii.imp4j.common.utility.MapObjectTransferUtility.formatValue;

/**
 * 解析csv文件工具类
 */
public class CSVParseUtil {
    public static List<HashMap<String, Object>> parseToMap(FileInputStream fileInputStream,
                                                           DateDescription dateDescription) throws IOException {
        byte[] fileBytes = FileEncodingUtility.getFileByte(fileInputStream);
        assert fileBytes != null;

        String fileEncoding = FileEncodingUtility.getFileCharset(new ByteArrayInputStream(fileBytes));//解析字符编码
        CsvReader reader = new CsvReader(new ByteArrayInputStream(fileBytes), ',', Charset.forName(fileEncoding));
        int valueStartRowNum = dateDescription.getDateStartRow();//数据初始行

        List<HashMap<String, Object>> list = new ArrayList<>();
        while (reader.readRecord()) {
            //获取当前记录位置
            long currentRowNum = reader.getCurrentRecord();
            HashMap<Integer, ColumnDescription> columnMapping = dateDescription.getColumnMapping();
            Set<Integer> needColumns = columnMapping.keySet();
            if (valueStartRowNum - 1 <= currentRowNum) {
                HashMap<String, Object> map = new HashMap<>();
                String[] rowValues = reader.getValues();
                if (assertIsNull(rowValues)) {
                    continue;
                }
                for (int c : needColumns) {
                    ColumnDescription des = columnMapping.get(c);
                    String key = des.getName();
                    String valueStr = rowValues[c];
                    valueStr = Trim_str(valueStr); //目前不对数据进行预处理
                    if (valueStr != null && valueStr.length() > 0) {
                        Object formatValue = formatValue(valueStr, des);
                        map.put(key, formatValue);
                        continue;
                    }
                    map.put(key, null);
                }
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 假如这行中有空值的话。
     *
     * @param rowValues 行数据
     * @return boolean
     */
    private static boolean assertIsNull(String[] rowValues) {
        if (rowValues == null) {
            return true;
        }
        boolean isNull = true;
        for (String c : rowValues) {
            if (c != null && c.length() > 0) {
                isNull = false;
            }
        }
        return isNull;
    }
}
