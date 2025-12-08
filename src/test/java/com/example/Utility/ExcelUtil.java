package com.example.Utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {

    private static Workbook workbook;
    private static Sheet sheet;

    public static void setExcelFile(String fileName, String sheetName) throws IOException {

        String fullPath = "src/test/resources/testdata/" + fileName;
        System.out.println("Loading Excel from: " + new File(fullPath).getAbsolutePath() +
                " | sheet = " + sheetName);

        try (FileInputStream fis = new FileInputStream(fullPath)) {
            workbook = new XSSFWorkbook(fis);
        }

        sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            System.out.println("❌ Sheet '" + sheetName + "' not found. Available sheets:");
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                System.out.println("   - " + workbook.getSheetName(i));
            }
            throw new IllegalArgumentException(
                    "Sheet '" + sheetName + "' not found in " + fileName);
        }
    }

    public static int getRowCount() {
        if (sheet == null) {
            throw new IllegalStateException("Excel sheet is null. " +
                    "Call setExcelFile() with correct sheet name before getRowCount().");
        }
        return sheet.getPhysicalNumberOfRows();
    }

    public static String getCellData(int rowIndex, int colIndex) {
        if (sheet == null) {
            throw new IllegalStateException("Excel sheet is null. Call setExcelFile() first.");
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(sheet.getRow(rowIndex).getCell(colIndex));
    }

    public static void closeWorkbook() {
    }
}
