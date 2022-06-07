package com.example.demo.ExcelExport;

import com.example.demo.model.Todo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelExport {
    private List<Todo> exportList;
    private String fileName;

    public ExcelExport(List<Todo> exportList, String fileName) {
        this.exportList = exportList;
        this.fileName = fileName;
    }

    public void writeToExcel() {

        // set directory to...

        // create a new Workbook
        Workbook wb = new XSSFWorkbook();

        // Create a new Sheet named "Contacts"
        Sheet sheet = wb.createSheet("ToDoList");

        // Create header row
        Row row = sheet.createRow(0);
        // Create a cell and put a value in it.
        row.createCell(0).setCellValue("name");
        row.createCell(1).setCellValue("deadline");
        row.createCell(2).setCellValue("status");

        // Create rows for contents
        for (int i = 0; i < exportList.size(); i++) {
            // row index equals i + 1 because the first row of Excel file is the header row.
            int rowIndex = i + 1;
            Todo item = exportList.get(i);
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(item.getName());
            row.createCell(1).setCellValue(item.getDeadline());
            row.createCell(2).setCellValue(item.isStatus());
        }

        //write to file(try with resource - no need for finally)
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            wb.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}