package excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fermii.imp4j.common.description.ColumnDescription;
import com.fermii.imp4j.common.description.DateDescription;
import com.fermii.imp4j.common.excel.ExcelUtil;
import com.fermii.imp4j.common.utility.PathUtility;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ParseMappingTest {
    public static void main(String[] args) throws Exception {
        String path = PathUtility.getProjectRootPath();
        path = path.replace("test-classes", "classes");
        String dataPath = path + "data/1.xlsx";
        //获取流
        File file = new File(dataPath);
        FileInputStream inStream = new FileInputStream(file);
        String jsonPath = "template/test.json";
        JSONObject jsonObj = getJSONMapping(jsonPath);
        //目标：数据无论是json还是存在mysql，最终转换成此对象
        DateDescription dateDescription = transferToDateDescription(jsonObj);
        List<HashMap<String, Object>> dataMap = ExcelUtil.parseToMap(inStream, dateDescription);
        System.out.println(dataMap);
    }

    /**
     * 将数据描述转换成对应对象
     *
     * @param jsonObj json对象
     * @return DateDescription
     */
    private static DateDescription transferToDateDescription(JSONObject jsonObj) throws Exception {
        DateDescription des = new DateDescription();
        des.setClassName(Class.forName((String) jsonObj.get("className")));
        des.setDateStartRow(jsonObj.getInteger("dateStartRow"));
        JSONObject columnMapping = (JSONObject) jsonObj.get("columnMapping");
        Set<String> columnNums = columnMapping.keySet();
        if (columnNums.size() == 0) {
            throw new Exception("未找到列对应mapping");
        }
        HashMap<Integer, ColumnDescription> map = new HashMap<>();
        for (String columnNum : columnNums) {
            JSONObject value = (JSONObject) columnMapping.get(columnNum);
            String name = value.getString("name");
            String type = value.getString("type");
            Integer column = Integer.valueOf(columnNum);
            ColumnDescription columnDescription = new ColumnDescription();
            columnDescription.setName(name);
            columnDescription.setType(type);
            if (value.containsKey("remark")) {
                String remark = value.getString("remark");
                columnDescription.setRemark(remark);
            }
            map.put(column, columnDescription);
        }
        des.setColumnMapping(map);
        return des;
    }

    /**
     * 获得json映射
     *
     * @param jsonPath 路径
     * @return JSONObject
     */
    private static JSONObject getJSONMapping(String jsonPath) throws IOException {
        String path = PathUtility.getProjectRootPath();
        path = path.replace("test-classes", "classes");
        jsonPath = path + jsonPath;
        BufferedReader reader = new BufferedReader(new FileReader(new File(jsonPath)));
        String s;
        StringBuilder jsonStr = new StringBuilder();
        while ((s = reader.readLine()) != null) {//使用readLine方法，一次读一行
            jsonStr.append(s);
        }
        return (JSONObject) JSON.parse(String.valueOf(jsonStr));
    }
}
