package com.tricentis.utilities;

import java.time.LocalDate;

public class commonUtils {
	
	public int getCurrentMonth() {
	
	String monthValue; 	
	LocalDate localDate=LocalDate.now();
	
	String month=String.valueOf(localDate.getMonthValue());
	//String year=String.valueOf(localDate.getYear() + 1);	  
	if(month.contains("0")) {
		 monthValue=month;
		  }
	else {
		  monthValue="0" + month;
			
		  }
	System.out.println("This is inside function" + monthValue);
	System.out.println("Convert Integer" + monthValue);
	return Integer.valueOf(monthValue); 
	}

	public int getCurrentYear() {
		
		String yearValue; 	
		LocalDate localDate=LocalDate.now();
		String year=String.valueOf(localDate.getYear());	  
		if(year.contains("0")) {
			 yearValue=year;
			  }
		else {
			  yearValue="0" + year;
				
			  }
		return Integer.valueOf(yearValue);
				
		}

}
