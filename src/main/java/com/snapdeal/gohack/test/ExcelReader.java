package com.snapdeal.gohack.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelReader {

	public static void main(String[] args) {

		try
		{
			FileInputStream file = new FileInputStream(new File("/home/vishal/Downloads/dataexcel.xls"));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
            StringBuffer query= new StringBuffer();
			//get the category sheet
			HSSFSheet sheet = workbook.getSheetAt(2);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				if(row.getRowNum()==0)
					continue;
				Cell categoryId=row.getCell(0);
				
				Cell categoryName=row.getCell(1);
				query.append("CREATE (:CATEGORY{catCode:'"+categoryId+"',name:'"+categoryName+"'})"+"\n");

			}
			//Subcategory sheet
			sheet=workbook.getSheetAt(3);
			Iterator<Row> subrowIterator = sheet.iterator();
			
			while (subrowIterator.hasNext())
			{
				Row row = subrowIterator.next();
				if(row.getRowNum()==0)
					continue;
				Cell subCategoryId=row.getCell(0);
				Cell subCategoryName=row.getCell(1);
				Cell category_Ids = row.getCell(2);
				query.append("CREATE (subCat:SUB_CATEGORY{subCatCode:'"+subCategoryId+"',name:'"+subCategoryName+"'})"+"\n");
				query.append("WITH subCat"+"\n");
				String[] cat_ids = category_Ids.toString().split(",");
				for(String cat : cat_ids){
					query.append("MATCH (cat:CATEGORY{catCode:'"+cat+"'})"+"\n");
					query.append("CREATE (subCat)-[:WITHIN_CAT]->(cat)"+"\n");
				}
		}
			//Product
			sheet=workbook.getSheetAt(4);
			Iterator<Row> prodrowIterator = sheet.iterator();
			
			while (prodrowIterator.hasNext())
			{
				Row row = prodrowIterator.next();
				if(row.getRowNum()==0)
					continue;
				Cell productCode=row.getCell(0);
				Cell productName=row.getCell(1);
				Cell subCatIds = row.getCell(2);
				query.append("CREATE (prod:PRODUCT{product:'"+productCode+"',name:'"+productName+"'})"+"\n");
				query.append("WITH prod"+"\n");
				String[] subCat_ids = subCatIds.toString().split(",");
				for(String sc : subCat_ids){
					query.append("MATCH (subCat:SUB_CATEGORY{subCatCode:'"+sc+"'})"+"\n");
					query.append("CREATE (prod)-[:WITHIN_SUB_CAT]->(subCat)"+"\n");
				}
			}
			
			//state this is not generalized..hardcoded for 2
			sheet=workbook.getSheetAt(6);
			Iterator<Row> stateIterator = sheet.iterator();
			
			while (stateIterator.hasNext())
			{
				Row row = stateIterator.next();
				if(row.getRowNum()==0)
					continue;
				Cell city=row.getCell(0);
				Cell state=row.getCell(1);
				query.append("CREATE (state:STATE{name:'"+state+"'})"+"\n");
				query.append("CREATE (city:CITY{name:'"+city+"'})"+"\n");
			}
			
			
			
			sheet=workbook.getSheetAt(1);
			Iterator<Row> cityIterator = sheet.iterator();
			int count=1;
			while (cityIterator.hasNext())
			{
				Row row = cityIterator.next();
				if(row.getRowNum()==0)
					continue;
				Cell pincode=row.getCell(0);
				Cell city=row.getCell(1);
				query.append("CREATE (pincode"+count+":PINCODE{pincode:'"+pincode+"'})"+"\n");
				query.append("WITH pincode"+count+"\n");
				query.append("MATCH (city"+count+":CITY{name:'"+city+"'})"+"\n");
				query.append("CREATE (pincode"+count+")-[:BASED_IN]->(city"+count+")");
				count++;
			}
			
			
			
			//state this is not generalized..hardcoded for 2
			sheet=workbook.getSheetAt(0);
			Iterator<Row> mainIterator = sheet.iterator();
			
			while (mainIterator.hasNext())
			{
				Row row = mainIterator.next();
				if(row.getRowNum()==0)
					continue;
				Cell seller=row.getCell(0);
				Cell pins=row.getCell(1);
				String[] pinCodes = pins.toString().split(","); 
				query.append("MATCH (seller:SELLER{sellerCode:'"+seller+"'})"+"\n");
				
				for(String eachString:pinCodes){
					query.append("MATCH (pin:PINCODE{pincode:'"+eachString+"'})"+"\n");
					query.append("CREATE (seller)-[:BASED_IN]->(city"+count+")");
				}
				
			}
			
			
			
			System.out.println(query);
			
			
			
			
			
			file.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
