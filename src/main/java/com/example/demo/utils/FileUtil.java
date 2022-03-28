package com.example.demo.utils;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.OutputStream;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<List<String>> ReadExcel(String excelPath) throws Exception {
        List<List<String>> data = null;
        String fileType = excelPath.substring(excelPath.lastIndexOf(".") + 1, excelPath.length());
        // 创建工作文档对象
        InputStream in = new FileInputStream(excelPath);
        HSSFWorkbook hssfWorkbook = null;//.xls
        XSSFWorkbook xssfWorkbook = null;//.xlsx
        //根据后缀创建读取不同类型的excel
        if (fileType.equals("xls")) {
            hssfWorkbook = new HSSFWorkbook(in);//它是专门读取.xls的
        } else if (fileType.equals("xlsx")) {
            xssfWorkbook = new XSSFWorkbook(in);//它是专门读取.xlsx的
        } else {
            throw new Exception("文档格式后缀不正确!!！");
        }
        /*这里默认只读取第 1 个sheet*/
        if (hssfWorkbook != null) {
            data = readXls(hssfWorkbook.getSheetAt(0));
        } else {
            data = readXlsx(xssfWorkbook.getSheetAt(0));
        }
        return data;
    }

    private static List<List<String>> readXlsx(XSSFSheet sheet) {
        int rowNumber = 0;
        List<List<String>> data = new ArrayList<>();
        XSSFRow row = sheet.getRow(rowNumber);
        while (row != null){
            rowNumber ++;
            List<String> oneRow = new ArrayList<>();
            for (int i = 0; i < row.getLastCellNum(); i++) {
                row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                oneRow.add(row.getCell(i).getStringCellValue());
            }
            data.add(oneRow);
            row = sheet.getRow(rowNumber);
        }
        return data;
    }

    private static List<List<String>> readXls(HSSFSheet sheet) {
        int rowNumber = 0;
        List<List<String>> data = new ArrayList<>();
        HSSFRow row = sheet.getRow(rowNumber);
        while (row != null){
            rowNumber ++;
            List<String> oneRow = new ArrayList<>();
            for (int i = 0; i < row.getLastCellNum(); i++) {
                row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                oneRow.add(row.getCell(i).getStringCellValue());
            }
            data.add(oneRow);
            row = sheet.getRow(rowNumber);
        }
        return data;
    }

    /**使用栗子
     * WriteExcel excel = new WriteExcel("D:\\myexcel.xlsx");
     * excel.write(new String[]{"1","2"}, 0);//在第1行第1个单元格写入1,第一行第二个单元格写入2
     */
    private static void write(List<List<String>> data, Sheet sheet, Workbook workbook, String pathname) throws Exception {
        //将内容写入指定的行号中

        //遍历整行中的列序号
//        for(List<String> oneRow:data){
//            for (int i = 0; i < oneRow.size(); i++) {
//                Cell cell = row.createCell(i);
//                cell.setCellValue(oneRow.get(i));
//            }
//        }
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < data.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(data.get(i).get(j));
            }
        }


//        for (int j = 0; j < writeStrings.length; j++) {
//            //根据行指定列坐标j,然后在单元格中写入数据
//            Cell cell = row.createCell(j);
//            cell.setCellValue(writeStrings[j]);
//        }
        OutputStream stream = new FileOutputStream(pathname);
        workbook.write(stream);
        stream.close();
    }

    public static void WriteExcel(List<List<String>> data, String excelPath) throws Exception {
        Workbook workbook;
        Sheet sheet;
        //在excelPath中需要指定具体的文件名(需要带上.xls或.xlsx的后缀)
        String fileType = excelPath.substring(excelPath.lastIndexOf(".") + 1, excelPath.length());
        //创建文档对象
        if (fileType.equals("xls")) {
            //如果是.xls,就new HSSFWorkbook()
            workbook = new HSSFWorkbook();
        } else if (fileType.equals("xlsx")) {
            //如果是.xlsx,就new XSSFWorkbook()
            workbook = new XSSFWorkbook();
        } else {
            throw new Exception("文档格式后缀不正确!!！");
        }
        // 创建表sheet
        sheet = workbook.createSheet("sheet1");
        write(data, sheet, workbook, excelPath);
    }


//    public static void out(List<List<String>> data){
//        for (List<String> datum : data) {
//            for (String s : datum) {
//                System.out.print(s);
//            }
//            System.out.println();
//        }
//    }
//    public static void main(String[] args) throws Exception {
//        List<List<String>> data = ReadExcel("D:\\Project\\FileArea\\path1\\1.xlsx");
//        out(data);
//        WriteExcel(data, "D:\\Project\\FileArea\\path1\\2.xlsx");
//    }
}
