package com.grab;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelExport {

    /**
     * 导出excel
     *
     * @param list        数据列表 List<Map<String, Object>>
     * @param headers     列标题，以英文逗号分割，必传
     * @param includes    字段名，以英文逗号分割，必传
     * @param filePath    导出文件全路径
     * @param sheetName   sheet名称：sheet1
     * @param dataPattern 日期格式：yyyy-MM-dd HH:mm:ss
     * @throws Exception 另一个程序正在使用此文件，进程无法访问
     */
    public static void exportListMap(List<Map<String, Object>> list, String headers, String includes, String filePath,
                                     String sheetName, String dataPattern) throws IOException {
        Workbook wb;
        if (filePath.endsWith(".xls")) {
            wb = new HSSFWorkbook();
        } else {
            wb = new SXSSFWorkbook(1000);
        }

        String[] headerArr = headers.split(",");
        String[] includeArr = includes.split(",");

        Font font = wb.createFont();
        font.setFontHeight((short) 18);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);

        Sheet sheet = wb.createSheet(sheetName);

        Row row = sheet.createRow(0);

        Cell cell;

        for (int i = 0; i < headerArr.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headerArr[i]);
        }

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0, k = 0; j < includeArr.length; j++) {
                cell = row.createCell(k);
                cell.setCellValue(list.get(i).get(includeArr[j]).toString());
                k++;
            }
        }

        //写入文件
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        wb.write(fileOutputStream);

        fileOutputStream.close();
        wb.close();

    }


}
