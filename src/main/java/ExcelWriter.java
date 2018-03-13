import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
	public static void printSchedule(ArrayList<Course> schedule,ArrayList<TA> taList, String FILE_NAME) {

	        XSSFWorkbook workbook = new XSSFWorkbook();
	        CellStyle style = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setColor(IndexedColors.RED.getIndex());
	        font.setBold(true);
	        style.setFont(font);
	        
	        CellStyle style2 = workbook.createCellStyle();
	        Font font2 = workbook.createFont();
	        font2.setBold(true);
	        style2.setFont(font2);
	        
	        System.out.println("Creating excel");	        
	        
	        for (Course course : schedule){
		        ArrayList<ArrayList<Section>> dailySchedules = new ArrayList<ArrayList<Section>>();
		        int tempMax = 0;
		        int max = 0;
		        for (int i = 0; i<5; i++){
		        	dailySchedules.add(new ArrayList<Section>());
		        }	        		        	
		        int rowNum = 0; 
		        XSSFSheet sheet = workbook.createSheet(course.getClassName());
		        for (Section section : course.getSections()){
		        	switch (section.getDay()){
		        	
		        	case 1: dailySchedules.get(0).add(section);
		        			break;
		        		
		        	case 2:dailySchedules.get(1).add(section);
        					break;
		        		
		        	case 3:dailySchedules.get(2).add(section);
        					break;
		        		
		        	case 4:dailySchedules.get(3).add(section);
        					break;
		        		
		        	case 5:dailySchedules.get(4).add(section);
        					break;
		        	
		        	}
		        }
		        
		        for(ArrayList<Section> section : dailySchedules){
		        	Collections.sort(section, new Comparator<Section>() {
		        	    @Override
		        	    public int compare(Section o1, Section o2) {
		        	        return o1.getSection().compareTo(o2.getSection());
		        	    }
		        	});
		        }
		        for (int i = 0; i<5; i++){
		        	for (int j = 0; j<4; j++){
				        Row row = sheet.createRow(rowNum++);
				        int colNum = 0;
				        Cell cell = row.createCell(colNum++);				        
				        if (j == 0){
				        String day = "ERROR";
				        switch (i){
				        
				        	case 0: day = "Monday"; 
				        			break;
				        	
				        	case 1:day = "Tuesday"; 
		        					break; 
				        		
				        	case 2:day = "Wednesday"; 
		        					break;
				        		
				        	case 3:day = "Thursday"; 
		        					break;
				        	
				        	case 4:day = "Friday"; 
		        					break;
				        }
				    cell.setCellStyle(style2);      
		        	cell.setCellValue(course.getClassName() + " " + day + " Sections" );
				        }
				        else if( j == 1){
				        	cell.setCellStyle(style2);  
				        	cell.setCellValue("TA");
				        }
				        else if (j == 2){
				        	cell.setCellStyle(style2);  
				        	cell.setCellValue("Room");
				        }
				        else{
				        	cell.setCellStyle(style2);  
				        	cell.setCellValue("Time");
				        }
				        for (int k = 0; k<dailySchedules.get(i).size(); k++){
					        Cell cell2 = row.createCell(colNum++);
					        tempMax++;
					        if ( j == 0){
					        	cell.setCellStyle(style2);  
					        	cell2.setCellValue(dailySchedules.get(i).get(k).getSection());
					        }
					        else if ( j == 1){
					        	cell2.setCellStyle(style);  
					        	if (dailySchedules.get(i).get(k).getTeacher() == null){
					        	cell2.setCellValue("UNFILLED");	
					        	}
					        	else if (dailySchedules.get(i).get(k).getTeacher().isFullTA() == false){
					        	cell2.setCellValue(dailySchedules.get(i).get(k).getTeacher().getLastName() + " " + dailySchedules.get(i).get(k).getTeacher().getFirstName() + " UG" );	
					        	}
					        	else{
						        cell2.setCellValue(dailySchedules.get(i).get(k).getTeacher().getLastName() + " " + dailySchedules.get(i).get(k).getTeacher().getFirstName() );
					        	}
					        }
					        else if ( j == 2)
					        	cell2.setCellValue(dailySchedules.get(i).get(k).getRoom());
					        else{
					        	String time = null;
				        		int block = dailySchedules.get(i).get(k).getBlock();
					        	if (i % 2 == 0){
					        		if (block==1){
					        			time = "9:30 - 12:20 p.m.";
					        		}
					        		else if (block==2){
					        			time = "12:30 - 3:20 p.m.";
					        		}
					        		else if (block==3){
					        			time = "3:30 - 6:20 p.m.";
					        		}
					        		else if (block==4){
					        			time = "6:30 - 9:20 p.m.";
					        		}
					        	}
					        	else {
					        		if (block==1){
					        			time = "9:00 - 11:50 a.m.";
					        		}
					        		else if (block==2){
					        			time = "12:00 - 2:50 p.m.";
					        		}
					        		else if (block==3){
					        			time = "3:00 - 5:50 p.m.";
					        		}
					        		else if (block==4){
					        			time = "6:00 - 8:50 p.m.";
					        		}					        		
					        		
					        	}
					        	if (block == 4 && dailySchedules.get(i).get(k).getSection().contains("SE")){
					        		time = "7:30 - 10:20 p.m.";
					        	}
					        	cell2.setCellValue(time);
					        	
					        }
				        }
				        max = (max > tempMax) ? max : tempMax;

				        
			       }
			        
		        	rowNum++;
		        }
				for (int i = 0; i < max+1; sheet.autoSizeColumn(i++));
	        }
	        int rowNum = 0; 
	        XSSFSheet sheet = workbook.createSheet("Remaining TAs");
	        int tempMax = 0;
	        int max = 0;
	        ArrayList<TA> remainingTAs = new ArrayList<TA>();
	        for (TA ta : taList){
	        	if (ta.getStatus() > 0)
	        		remainingTAs.add(ta);
	        }
	        
	        for (TA ta : remainingTAs){
	        	Row row = sheet.createRow(rowNum++);
	        	int colNum = 0;
	        		for (int i = 0; i < 7; i++){
	        			Cell cell = row.createCell(colNum++);
	        			
	        			switch (i){
	        			case 0: cell.setCellValue(ta.getLastName());	
	        					break;
	        			
	        			case 1: cell.setCellValue(ta.getFirstName());	
    							break;
	        			
	        			case 2:cell.setCellValue((ta.isFullTA()) ? "Full TA" : "Half TA");	
								break;
	        				
	        			case 3:cell.setCellValue(Integer.toString(ta.getTeaching().size()));
	        					break;
	        			case 4:cell.setCellValue(ta.getPref1());
	        					break;
	        			case 5:cell.setCellValue(ta.getPref2());
	        					break;	        			
	        			case 6:cell.setCellValue(ta.getPref3());
	        					break;	        			
	        			}
	        		}
	        	rowNum++;
	        }

	        
	        for (int i = 0; i < max+1; sheet.autoSizeColumn(i++));	        
	        
	        try {
	            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Done");
	    }
	}

