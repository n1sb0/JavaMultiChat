package Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
	
	public static String getCurrentTime() {
		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");

	    return myDateObj.format(myFormatObj);
	}

}
