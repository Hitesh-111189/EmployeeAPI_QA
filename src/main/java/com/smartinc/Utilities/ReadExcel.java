package com.smartinc.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import junit.framework.Assert;

public class ReadExcel {

	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row   =null;
	private XSSFCell cell = null;
	
	public ReadExcel(String fileName){
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	public List<Object[]> getInputData(String sheetName){

		List<Object[]> myData = new ArrayList<Object[]>();
		try{
			
			Sheet sheet = workbook.getSheet(sheetName);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				String name = getCellData(sheetName, "name", i);
				String salary = getCellData(sheetName, "salary", i);
				String age = getCellData(sheetName, "age", i);
				Object ob[] = {name, salary, age};
				myData.add(ob);
			} 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return myData;

	}

	public String getCellData(String sheetName,String colName,int rowNum){
		
		try{
			if(rowNum <=0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);

			if(cell==null)
				return "";
			if(cell.getCellType()==CellType.STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==CellType.NUMERIC)
				return String.valueOf(cell.getNumericCellValue());
		}
		catch(Exception e){
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
		return "";
	}
}

