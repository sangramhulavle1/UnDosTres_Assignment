package Automation_Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ParameterizationTestData {
	
	public  String securedinfo  (int row , int col) throws EncryptedDocumentException, IOException
	{
		FileInputStream file = new FileInputStream ("C:\\Users\\Sunny\\eclipse-workspace\\UnDosTres\\TestData\\confidential data.xlsx"); 
		
		Sheet excel = WorkbookFactory.create(file).getSheet("sheet1");
		 String value= excel.getRow(row).getCell(col).getStringCellValue();
		return value ;
	}


}
