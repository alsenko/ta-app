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
	             protoTA.setLastName(taInfo.get(0));
	             protoTA.setFirstName(taInfo.get(1));
	             protoTA.setTaType(taInfo.get(2));
	             protoTA.setMonday(taInfo.get(4));
	             protoTA.setTuesday(taInfo.get(5));
	             protoTA.setWednesday(taInfo.get(6));
	             protoTA.setThursday(taInfo.get(7));
	             protoTA.setFriday(taInfo.get(8));
	             protoTA.setPref1(taInfo.get(15));
	             protoTA.setPref2(taInfo.get(16));
	             protoTA.setPref3(taInfo.get(17));
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

 
 static ArrayList<Course> createClassList(String filename) {
	 ArrayList<Course> classList = new ArrayList<Course>();
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
	             newSection.setSection(sectionInfo.get(1));
	             newSection.setCourse(sectionInfo.get(0));
	             newSection.setRoom(sectionInfo.get(5));
	             
	             switch (sectionInfo.get(2)){
	             
	             case "M" : newSection.setDay(1);
	             			break;            	 
	             case "Tu" : newSection.setDay(2);
	             			break;	            	 
	             case "W" : newSection.setDay(3);
	             			break;	             
	             case "Th" :newSection.setDay(4);
	             			break;
	             case "F" : newSection.setDay(5);
      						break;	             
	             }
	             
	             switch (sectionInfo.get(3).charAt(0)){
	             
	             case '9' : newSection.setBlock(1);
	             			break;            	 
	             case '1' : newSection.setBlock(2);
	             			break;	            	 
	             case '3' : newSection.setBlock(3);
	             			break;	             
	             case '6' :newSection.setBlock(4);
	             			break;
	             case '7' :newSection.setBlock(4);
      						break;	             			
         
	             }
	             boolean classFound = false;
	             for (Course course : classList){
	            	 if (course.getClassName().equals(newSection.getCourse())){
	            		 course.getSections().add(newSection);
	            		 classFound = true;
	            		 break;
	            	 }
	             }
	             if (!classFound){
	            	 Course course = new Course();
	            	 course.setClassName(newSection.getCourse());
	            	 course.getSections().add(newSection);
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
	 newTA.setFirstName(protoTA.getFirstName());
	 	 newTA.setLastName(protoTA.getLastName());
	 
	 if (protoTA.getPref1().equals("") || protoTA.getPref1().equals("None"))
		 newTA.setPref1("none");
	 else
		 newTA.setPref1(protoTA.getPref1().substring(0, 7));
	 
	 
	 if (protoTA.getPref2().equals("")|| protoTA.getPref2().equals("None"))
		 newTA.setPref2("none");	 
	 else
		 newTA.setPref2(protoTA.getPref2().substring(0, 7));
	 
	 if (protoTA.getPref3().equals("")|| protoTA.getPref3().equals("None"))
		 newTA.setPref3("none");	 
	 else
		 newTA.setPref3(protoTA.getPref3().substring(0, 7));
	 
	 if (protoTA.getTaType().equals("Full TA")){
		 newTA.setStatus(2);
		 newTA.setFullTA(true);
	 }
	 else {
		 newTA.setStatus(1);
		 newTA.setFullTA(false);
	 }
	 
	 newTA.setMonday(createDay(protoTA.getMonday()));
	 newTA.setTuesday(createDay(protoTA.getTuesday()));
	 newTA.setWednesday(createDay(protoTA.getWednesday()));
	 newTA.setThursday(createDay(protoTA.getThursday()));
	 newTA.setFriday(createDay(protoTA.getFriday()));
 
	 return newTA;
}

private static Day createDay(String monday) {
	Day day = new Day();
	String[] blocks = monday.split("/");
	day.setBlockOne((blocks[0].contains("*") || blocks[0].contains("X")) ? false : true);
	day.setBlockTwo((blocks[1].contains("*") || blocks[1].contains("X")) ? false : true);
	day.setBlockThree((blocks[2].contains("*") || blocks[2].contains("X")) ? false : true);
	day.setBlockFour((blocks[3].contains("*") || blocks[3].contains("X")) ? false : true);
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