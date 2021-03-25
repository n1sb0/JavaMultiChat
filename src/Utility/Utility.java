package Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
	
	public static String getCurrentTime() {
		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	    return myDateObj.format(myFormatObj);
	}

}
