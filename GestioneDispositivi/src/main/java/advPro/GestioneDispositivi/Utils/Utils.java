package advPro.GestioneDispositivi.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

	public class Utils {
		
		//istanzia una nuova data di tipo java.util.Date
		public static Date date(String inputDate) throws ParseException {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
			return date;
		}
		
}