package CrawlerWikipedia.test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalDate localDate = LocalDate.now();
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHms");
		//String time = localDate.format(formatter);
		Instant instant = Instant.now();
		long timeStampSeconds = instant.toEpochMilli();
		System.out.println( timeStampSeconds);
		System.out.println( timeStampSeconds);
		System.out.println( timeStampSeconds);
		
	}

}
