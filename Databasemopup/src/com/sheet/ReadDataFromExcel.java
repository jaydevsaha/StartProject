package com.sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.helper.UpdateDatabase;
import com.model.Domain;

public class ReadDataFromExcel {
	static final Logger logger = Logger.getLogger(ReadDataFromExcel.class);
	private static final String FILE_NAME =
			"E:/data1/2019/workspace/April/15/servlet/generate_excel/apr2020/template.xlsx";

	@Test()
	public static void execute() {
		BasicConfigurator.configure();
		List<Object> sheetData = new ArrayList<Object>();
		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				List<Object> data = new ArrayList<Object>();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					currentCell.setCellType(Cell.CELL_TYPE_STRING);
					data.add(currentCell);
				}
				sheetData.add(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Domain> listOfLists=showExelData(sheetData);
		UpdateDatabase.updateDb(listOfLists);
		   
		     
		
	}

	// @Test()
	private static List<Domain> showExelData(List<Object> sheetData) {
		List<Domain> listDomain=new ArrayList<Domain>();
		for (int i = 0; i < sheetData.size(); i++) {
			List<Object> list = (List<Object>) sheetData.get(i);
			XSSFCell oldKey = (XSSFCell) list.get(0);
			XSSFCell newKey = (XSSFCell) list.get(1);
			String oldValue = oldKey.getRichStringCellValue().getString();
			String newValue = newKey.getRichStringCellValue().getString();
		
			//System.out.println("old value "+oldValue);
			//System.out.println("new value "+newValue);
			
			
			Domain d=new Domain();
			d.setOldKey(oldValue);
			d.setNewKey(newValue);
			listDomain.add(d);
		}
	//	Reporter.log(count + " records updated successfully");
		return listDomain;
		}

}
