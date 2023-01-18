package tcpConnection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Trial {

	public static void main(String[] args) {
		LocalDateTime fromDateTime = LocalDateTime.now();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDateTime toDateTime = LocalDateTime.now();

//		LocalDateTime fromDateTime = LocalDateTime.from( fromDateTime );

		long minutes = fromDateTime.until( toDateTime, ChronoUnit.SECONDS );
		

		System.out.println( minutes);
	}
	
}
