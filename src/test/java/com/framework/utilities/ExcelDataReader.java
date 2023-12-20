package com.framework.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelDataReader {
    public static List<Map<String, String>> readExcelData(String excelFilePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Get the first sheet from workbook

        List<Map<String, String>> data = new ArrayList<>();
        Iterator<Row> iterator = sheet.iterator();

        // Assuming the first row is headers
        Row headersRow = iterator.next();
        List<String> headers = new ArrayList<>();
        headersRow.forEach(cell -> headers.add(cell.getStringCellValue()));

        // Iterate over the rows
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Map<String, String> rowData = new HashMap<>();
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = currentRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                String cellValue = cell != null ? getCellValueAsString(cell) : "";
                rowData.put(headers.get(i), cellValue);
            }
            data.add(rowData);
        }

        workbook.close();
        inputStream.close();

        return data;
    }

    private static String getCellValueAsString(Cell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue().toString();
                } else {
                    cellValue = Double.toString(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                cellValue = Boolean.toString(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case BLANK:
            case _NONE:
            case ERROR:
                break;
        }
        return cellValue;
    }
}

