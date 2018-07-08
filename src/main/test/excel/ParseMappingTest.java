package excel;

import com.alibaba.fastjson.JSONObject;
import com.fermii.imp4j.common.description.DateDescription;
import com.fermii.imp4j.common.excel.ExcelParseUtil;
import com.fermii.imp4j.common.utility.DataDescriptionUtility;
import com.fermii.imp4j.common.utility.PathUtility;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class ParseMappingTest {
    public static void main(String[] args) throws Exception {
        String path = PathUtility.getProjectRootPath();
        path = path.replace("test-classes", "classes");
        String dataPath = path + "data/1.xlsx";
        //获取流
        File file = new File(dataPath);
        FileInputStream inStream = new FileInputStream(file);
        String jsonPath = "template/test.json";
        JSONObject jsonObj = DataDescriptionUtility.getJSONMapping(jsonPath);
        //目标：数据无论是json还是存在mysql，最终转换成此对象
        DateDescription dateDescription = DataDescriptionUtility.transferToDateDescription(jsonObj);
        List<HashMap<String, Object>> dataMap = ExcelParseUtil.parseToMap(inStream, dateDescription);
        System.out.println(dataMap);
    }


}
