package excel;

import com.csvreader.CsvReader;
import com.fermii.imp4j.common.utility.PathUtility;

import java.io.*;
import java.nio.charset.Charset;

public class ParseCSVTest {
    public static void main(String[] args) throws Exception {
        String path = PathUtility.getProjectRootPath();
        path = path.replace("test-classes", "classes");
        String dataPath = path + "data/1.csv";
        //获取流
        String encoding = getFileCharset(dataPath);
        CsvReader reader = new CsvReader(dataPath, ',', Charset.forName(encoding));
        while (reader.readRecord()) {
            //把头保存起来

            //获取当前记录位置
            System.out.print(reader.getCurrentRecord() + ".");
            //读取一条记录
            System.out.println(reader.getRawRecord());
            String[] tmp = {reader.getValues()[0], reader.getValues()[1]};
            //修改记录，并只写入第一个字段和第二字段
        }
    }

    /**
     * 判断编码格式方法
     *
     * @param filePath 文件路径
     * @return charset
     */
    private static String getFileCharset(String filePath) {
        File sourceFile = new File(filePath);
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
}
