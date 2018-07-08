package excel;


import com.fermii.imp4j.common.excel.ExcelParseUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class ExcelUtilTest {
    public static void main(String[] args) throws FileNotFoundException {
        xlsxTest();
    }

    private static void xlsxTest() throws FileNotFoundException {
        String xlsxPath = "G:/1.xlsx";
        File xlsxFile = new File(xlsxPath);
        FileInputStream xlsxInStream = new FileInputStream(xlsxFile);
        //解析过程，定义输入流
        List<HashMap<String, String>> content = ExcelParseUtil.parse(xlsxInStream, 1, 2);
        assert content != null;
        System.out.println(content.size());
//        System.out.println(content);
    }
}
