package com.fermii.imp4j.common.excel;

import com.fermii.imp4j.common.description.ColumnDescription;
import com.fermii.imp4j.common.description.DateDescription;
import com.fermii.imp4j.common.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

import static com.fermii.imp4j.common.utility.MapObjectTransferUtility.formatValue;

/**
 * excel 工具类
 *
 * @author lirui
 * @date 2018年7月5日 14:29:02
 */
public class ExcelParseUtil {


    /**
     * 将excel解析成对象的形式
     *
     * @param inStream         输入流
     * @param keyRowNum        列明所在行，从1开始
     * @param valueStartRowNum 数据起始行
     * @return List<Map>
     */
    public static List<HashMap<String, String>> parse(FileInputStream inStream, int keyRowNum, int valueStartRowNum) {
        Workbook workBook;
        try {
//            workBook = WorkbookFactory.create(inStream);
            workBook = new XSSFWorkbook(inStream);
            Sheet sheet = workBook.getSheetAt(0);//默认只解析第一个sheet
            Row keyRow = sheet.getRow(keyRowNum - 1);
            int rowLength = sheet.getLastRowNum();
            int columnSize = keyRow.getLastCellNum();
            List<HashMap<String, String>> list = new ArrayList<>();
            for (int rowNum = valueStartRowNum - 1; rowNum <= rowLength; rowNum++) {//开始遍历数据
                Row currentRow = sheet.getRow(rowNum);
                if (isRowEmpty(currentRow)) {//检查一整行是否都是空的，空的话就跳过
                    continue;
                }
                HashMap<String, String> map = new HashMap<>();
                for (int c = 0; c < columnSize; c++) {
                    String key = (String) getCellValue(keyRow.getCell(c));
                    Object value = getCellValue(currentRow.getCell(c));
                    String valueStr = String.valueOf(value);
//                    valueStr = Trim_str(valueStr); //目前不对数据进行预处理
                    map.put(key, valueStr);
                }
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串修剪  去除字符串前后空格。
     *
     * @param str str
     * @return str
     */
    public static String Trim_str(String str) {
        if (str == null)
            return null;
        return str.trim();
    }

    /**
     * 判断该行是否所有数值都为空
     *
     * @param currentRow 当前Row
     * @return boolean
     */
    private static boolean isRowEmpty(Row currentRow) {
        if (currentRow == null) {
            return true;
        }
        Iterator<Cell> cellIterator = currentRow.cellIterator();
        while (cellIterator.hasNext()) {
            Object cellValue = (cellIterator.next());
            if (cellValue != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取单元格值
     *
     * @param cell 第一个
     * @return Object
     */
    private static Object getCellValue(Cell cell) {
        if (cell == null
                || (cell.getCellTypeEnum() == CellType.STRING && StringUtils.isBlank(cell
                .getStringCellValue()))) {
            return null;
        }
        CellType cellType = cell.getCellTypeEnum();
        if (cellType == CellType.BLANK)
            return null;
        else if (cellType == CellType.BOOLEAN)
            return cell.getBooleanCellValue();
        else if (cellType == CellType.ERROR)
            return cell.getErrorCellValue();
        else if (cellType == CellType.FORMULA) {
            try {
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            } catch (IllegalStateException e) {
                return cell.getRichStringCellValue();
            }
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }
        } else if (cellType == CellType.STRING)
            return cell.getStringCellValue();
        else
            return null;
    }

    /**
     * 将excel解析成对象的形式，默认第一行是列明，第二行开始是数据
     *
     * @param inStream 输入流
     * @return List<Map>
     */
    public static List<HashMap<String, String>> parse(FileInputStream inStream) {
        return parse(inStream, 1, 2);
    }

    /**
     * 解析成map对象
     *
     * @param inStream        文件输入流
     * @param dateDescription 数据描述
     * @return lest
     */
    public static List<HashMap<String, Object>> parseToMap(FileInputStream inStream, DateDescription dateDescription) {
        Workbook workBook;
        int valueStartRowNum = dateDescription.getDateStartRow();
        try {
//            workBook = WorkbookFactory.create(inStream);
            workBook = new XSSFWorkbook(inStream);
            Sheet sheet = workBook.getSheetAt(0);//默认只解析第一个sheet
            int rowLength = sheet.getLastRowNum();
            List<HashMap<String, Object>> list = new ArrayList<>();
            HashMap<Integer, ColumnDescription> columnMapping = dateDescription.getColumnMapping();
            Set<Integer> needColumns = columnMapping.keySet();
            for (int rowNum = valueStartRowNum - 1; rowNum <= rowLength; rowNum++) {//开始遍历数据
                Row currentRow = sheet.getRow(rowNum);
                if (isRowEmpty(currentRow)) {//检查一整行是否都是空的，空的话就跳过
                    continue;
                }
                HashMap<String, Object> map = new HashMap<>();
                for (int c : needColumns) {
                    ColumnDescription des = columnMapping.get(c);
                    String key = des.getName();
                    Object value = getCellValue(currentRow.getCell(c));
                    String valueStr = String.valueOf(value);
                    valueStr = Trim_str(valueStr); //目前不对数据进行预处理
                    Object formatValue = formatValue(valueStr, des);
                    map.put(key, formatValue);
                }
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
