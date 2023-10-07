package com.raza.netty.sqlddl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static void main(String[] args) throws IOException {

        String tableComment = "7.2.9、门诊/急诊费用表（从表）";
        String tableName = "EMR_OUTP_CHARGE_DETAIL_RECORD";
        System.out.println("-- " + tableComment);
        System.out.println("create table " + tableName);
        System.out.println("(");
        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\新建 XLS 工作表.xls"; // 替换为你的XLS文件路径

        List<Column> list = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new HSSFWorkbook(inputStream);
        // 获取第一个工作表（Sheet）
        Sheet sheet = workbook.getSheetAt(6);

        // 迭代遍历每一行
        for (Row row : sheet) {
            Column column = new Column();
            column.setName(ExcelReader.getCellValue(row.getCell(2)));
            column.setNotNull(ExcelReader.getCellValue(row.getCell(5)));
            column.setLength(ExcelReader.getCellValue(row.getCell(4)));
            String comment = ExcelReader.getCellValue(row.getCell(0)) +
                    " " +
                    ExcelReader.getCellValue(row.getCell(1)) +
                    " " +
                    ExcelReader.getCellValue(row.getCell(6)) ;
            column.setComment(comment);
            column.setType(ExcelReader.getCellValue(row.getCell(3)));
            list.add(column);
        }
        for (Column column : list) {
            System.out.println(column.getString());
        }

        System.out.println("  primary key (guid) ) COMMENT ' "+ tableComment +" '" + ";");
    }


    public static String getCellValue(Cell cell) {
        if (cell == null ){
            return "";
        }

        switch ((cell.getCellType())) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                double numericCellValue = cell.getNumericCellValue();
                int numericCellValue1 = (int) numericCellValue;
                return String.valueOf(numericCellValue1);
            default:
                return "";
        }
    }
}
