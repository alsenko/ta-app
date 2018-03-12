import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

 
 static ArrayList<TA> createTAList(String filename) {
	 ArrayList<TA> taList = new ArrayList<TA>();
	 try{
	 String excelFilePath = filename;   
	   FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	   System.out.println(excelFilePath);
	   Workbook workbook = getRelevantWorkbook(inputStream, excelFilePath);
	   
	   Sheet firstSheet = workbook.getSheetAt(0);
	         Iterator<Row> iterator = firstSheet.iterator();
	         iterator.next();
	         ArrayList<String> taInfo = new ArrayList<String>();
	         while (iterator.hasNext()) {
	             Row nextRow = iterator.next();
	             ProtoTA protoTA = new ProtoTA();
	             Iterator<Cell> cellIterator = nextRow.cellIterator();
	             int counter = 0;
	             while (cellIterator.hasNext()) {
	                 Cell cell = cellIterator.next();
	                 if (cell.getColumnIndex() != counter){
	                	 taInfo.add("");
	                	 counter++;
	                 }
	                 counter++;
	                 switch (cell.getCellTypeEnum()){
	                 
	                 case NUMERIC : taInfo.add(Double.toString(cell.getNumericCellValue()));
	                	 			break;
	                 case STRING : taInfo.add(cell.getStringCellValue());
	                 			   break;
					default:
						taInfo.add(cell.getStringCellValue());
						break;
	                 
	                 
	                 }
	                 System.out.println(taInfo.get(taInfo.size()-1));
	                 System.out.println(counter);
	             }
	             while (taInfo.size() != 18)
	            	 taInfo.add("");
	             protoTA.lastName = taInfo.get(0);
	             protoTA.firstName = taInfo.get(1);
	             protoTA.taType = taInfo.get(2);
	             protoTA.monday = taInfo.get(4);
	             protoTA.tuesday = taInfo.get(5);
	             protoTA.wednesday = taInfo.get(6);
	             protoTA.thursday = taInfo.get(7);
	             protoTA.friday = taInfo.get(8);
	             protoTA.pref1 = taInfo.get(15);
	             protoTA.pref2 = taInfo.get(16);
	             protoTA.pref3 = taInfo.get(17);
	             taInfo.clear();
	             TA newTA = createTA(protoTA);
	             taList.add(newTA);
	             
	         }
	          
	         workbook.close();
	         inputStream.close();
	   
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	 return taList;
}

 
 static ArrayList<Class> createClassList(String filename) {
	 ArrayList<Class> classList = new ArrayList<Class>();
	 try{
	 String excelFilePath = filename;   
	   FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	   System.out.println(excelFilePath);
	   Workbook workbook = getRelevantWorkbook(inputStream, excelFilePath);
	   
	   Sheet firstSheet = workbook.getSheetAt(0);
	         Iterator<Row> iterator = firstSheet.iterator();
	         iterator.next();
	         ArrayList<String> sectionInfo = new ArrayList<String>();
	         while (iterator.hasNext()) {
	             Row nextRow = iterator.next();
	             Section newSection = new Section();
	             Iterator<Cell> cellIterator = nextRow.cellIterator();
	             while (cellIterator.hasNext()) {
	            	 
	                 Cell cell = cellIterator.next();
	                 switch (cell.getCellTypeEnum()){
	                 
	                 case NUMERIC : sectionInfo.add(Integer.toString(((Double) cell.getNumericCellValue()).intValue()));
	                	 			break;
	                 case STRING : sectionInfo.add(cell.getStringCellValue());
	                 			   break;	                             
	                 }
	             }
	             newSection.section = sectionInfo.get(1);
	             newSection.course = sectionInfo.get(0);
	             newSection.room = sectionInfo.get(5);
	             
	             switch (sectionInfo.get(2)){
	             
	             case "M" : newSection.day = 1;
	             			break;            	 
	             case "Tu" : newSection.day = 2;
	             			break;	            	 
	             case "W" : newSection.day = 3;
	             			break;	             
	             case "Th" :newSection.day = 4;
	             			break;
	             case "F" : newSection.day = 5;
      						break;	             
	             }
	             
	             switch (sectionInfo.get(3).charAt(0)){
	             
	             case '9' : newSection.block = 1;
	             			break;            	 
	             case '1' : newSection.block = 2;
	             			break;	            	 
	             case '3' : newSection.block = 3;
	             			break;	             
	             case '6' :newSection.block = 4;
	             			break;
	             case '7' :newSection.block = 4;
      						break;	             			
         
	             }
	             boolean classFound = false;
	             for (Class course : classList){
	            	 if (course.className.equals(newSection.course)){
	            		 course.sections.add(newSection);
	            		 classFound = true;
	            		 break;
	            	 }
	             }
	             if (!classFound){
	            	 Class course = new Class();
	            	 course.className = newSection.course;
	            	 course.sections.add(newSection);
	            	 classList.add(course);
	             }
	             sectionInfo.clear();
	         }
	          
	         workbook.close();
	         inputStream.close();
	   
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	 return classList;
}
 
 
 
 
private static TA createTA(ProtoTA protoTA) {
	 TA newTA = new TA();
	 newTA.firstName = protoTA.firstName;
	 	 newTA.lastName = protoTA.lastName;
	 
	 if (protoTA.pref1.equals("") || protoTA.pref1.equals("None"))
		 newTA.pref1 = "none";
	 else
		 newTA.pref1 = protoTA.pref1.substring(0, 7);
	 
	 
	 if (protoTA.pref2.equals("")|| protoTA.pref2.equals("None"))
		 newTA.pref2 = "none";	 
	 else
		 newTA.pref2 = protoTA.pref2.substring(0, 7);
	 
	 if (protoTA.pref3.equals("")|| protoTA.pref3.equals("None"))
		 newTA.pref3 = "none";	 
	 else
		 newTA.pref3 = protoTA.pref3.substring(0, 7);
	 
	 if (protoTA.taType.equals("Full TA")){
		 newTA.status = 2;
		 newTA.fullTA = true;
	 }
	 else {
		 newTA.status = 1;
		 newTA.fullTA = false;
	 }
	 
	 newTA.monday = createDay(protoTA.monday);
	 newTA.tuesday = createDay(protoTA.tuesday);
	 newTA.wednesday = createDay(protoTA.wednesday);
	 newTA.thursday = createDay(protoTA.thursday);
	 newTA.friday = createDay(protoTA.friday);
 
	 return newTA;
}

private static Day createDay(String monday) {
	Day day = new Day();
	String[] blocks = monday.split("/");
	day.blockOne = (blocks[0].contains("*") || blocks[0].contains("X")) ? false : true;
	day.blockTwo = (blocks[1].contains("*") || blocks[1].contains("X")) ? false : true;
	day.blockThree = (blocks[2].contains("*") || blocks[2].contains("X")) ? false : true;
	day.blockFour = (blocks[3].contains("*") || blocks[3].contains("X")) ? false : true;
	return day;
}

private static Workbook getRelevantWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException
 {
     Workbook workbook = null;
  
     if (excelFilePath.endsWith("xls")) {
         workbook = new HSSFWorkbook(inputStream);
     } else if (excelFilePath.endsWith("xlsx")) {
         workbook = new XSSFWorkbook(inputStream);
     } else {
         throw new IllegalArgumentException("Incorrect file format");
     }
  
     return workbook;
 }

}