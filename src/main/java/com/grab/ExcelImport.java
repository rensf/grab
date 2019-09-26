package com.grab;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelImport {

    public static void importExcelFile(String filepath) throws IOException {

        Workbook workbook;
        if (filepath.endsWith(".xls")) {
            workbook = new HSSFWorkbook(new FileInputStream(filepath));
        } else {
            workbook = new XSSFWorkbook(new FileInputStream(filepath));
        }

        int sheets = workbook.getNumberOfSheets();
        for (int s = 0; s < sheets; s++) {
            Sheet sheet = workbook.getSheet(workbook.getSheetName(s));

            int rows = sheet.getPhysicalNumberOfRows();
            int columns = 0;

            for (int r = 0; r < rows; r++) {
                if (r == 0) {
                    columns = sheet.getRow(r).getLastCellNum();
                    continue;
                }
                String value = "";
                Row row = sheet.getRow(r);
                if (row != null) {
                    for (int c = 0; c < columns; c++) {
                        Cell cell = row.getCell(c);
                        if(cell != null) {
                            value += cell.getStringCellValue() + ",";
                        }
                    }
                }
                System.out.println(value);
            }
        }


    }


}
