package com.Udemy.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public String path = null;
	public FileInputStream fis = null;
	public FileOutputStream fos = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public ExcelUtility(String path) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			int num = sheet.getLastRowNum() + 1;
			return num;
		}
	}

	public int getColumnCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (row == null) {
				return 0;
			}
		}
		return row.getLastCellNum();
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {

		try {
			// row should be greater than zero
			if (rowNum < 0) {
				System.out.println(rowNum);
				return "invalid row";
			}

			int index = workbook.getSheetIndex(sheetName); // get index of particular sheet using sheetName
			//System.out.println("index -- " + index);
			if (index == -1) {
				return "incorrect index"; // index of first sheet in a workbook is 0
			}

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);
			//System.out.println(row.getRowNum() +"  "+cell.toString());
			if (row == null) {
				return "invalid row";
			}
			if (cell == null) {
				return "invalid cell";
			}

			if (cell.getCellType() == CellType.STRING) {
				return cell.getStringCellValue();
			}
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				return String.valueOf(cell.getNumericCellValue());
			}
			else if(cell.getCellType()==CellType.BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			}
			else
				return " ";

		} catch (Exception e) {
			e.printStackTrace();
			return "row Number - " + rowNum + " col Number - "+ colNum + " does not exist in xlsx" ;
		}
	}
}
