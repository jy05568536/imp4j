package csv;

import com.alibaba.fastjson.JSONObject;
import com.fermii.imp4j.common.csv.CSVParseUtil;
import com.fermii.imp4j.common.description.DateDescription;
import com.fermii.imp4j.common.regex.RegexValidateInfo;
import com.fermii.imp4j.common.utility.DataDescriptionUtility;
import com.fermii.imp4j.common.utility.MapObjectTransferUtility;
import com.fermii.imp4j.common.utility.PathUtility;
import com.fermii.imp4j.mapping.Coupons;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fermii.imp4j.common.regex.RegexUtility.validateRegex;

public class CSVParseTest {
    public static void main(String[] args) throws Exception {
        String path = PathUtility.getProjectRootPath();
        path = path.replace("test-classes", "classes");
        String dataPath = path + "data/1.csv";
        //获取流
//        String encoding = getFileCharset(dataPath);
        String jsonPath = "template/test.json";
        JSONObject jsonObj = DataDescriptionUtility.getJSONMapping(jsonPath);
        //目标：数据无论是json还是存在mysql，最终转换成此对象
        DateDescription dateDescription = DataDescriptionUtility.transferToDateDescription(jsonObj);
        FileInputStream fileInputStream = new FileInputStream(new File(dataPath));
//        CsvReader reader = new CsvReader(dataPath, ',', Charset.forName(encoding));
        List<HashMap<String, Object>> map = CSVParseUtil.parseToMap(fileInputStream, dateDescription);
        System.out.println("1");
        RegexValidateInfo valid = validateRegex(map, dateDescription);
        List coupons = mapToObject(map, dateDescription);
        System.out.println(coupons);
    }

    private static List mapToObject(List<HashMap<String, Object>> list, DateDescription dateDescription) throws IllegalAccessException, InstantiationException {
        List<Coupons> couponsList = new ArrayList<>();
        for (HashMap<String, Object> map : list) {
            Coupons coupons = (Coupons) dateDescription.getClassName().newInstance();
            MapObjectTransferUtility.setValue(map, coupons);
            couponsList.add(coupons);
        }
        return couponsList;
    }


}
