package es.upm.dit.gsi.sojason.services.travel;
import java.util.regex.Pattern;

import java.util.logging.Logger;


public class VuelosBaratosConvenion {
	
	public final static String SERVICE_URL="http://www.vuelosbaratos.es/OfertasVuelos/flysearch.aspx?";
	public final static String DEP_PARAM="depIATA";
	public final static String DEST_PARAM="destIATA";
	public final static String DATE_PARAM="depart";
	public final static String ADULTS="adults";
	public final static String APPENDIX="&infants=0&children=0&flway=True&flight=true&currency=ES&lang=es-ES";

	private Logger logger = Logger.getLogger("TEPRJason." + VuelosBaratosScrapper.class.getName());
	
	
	public VuelosBaratosConvenion(){
		
	}
	
	public String generateQuery(String depCity,String destCity,String day, String month, String year, String adults){
		
		//validateParams(depCity,destCity,day,month,year);
		
		if(depCity.equals("madrid")){
			depCity="MAD";
		}
		if(destCity.equals("berlin")){
			destCity="BER";
		}
		String res=SERVICE_URL;
		res=res.concat(DEP_PARAM);
		res=res.concat("=");
		res=res.concat(depCity);
		res=res.concat("&");
		res=res.concat(DEST_PARAM);
		res=res.concat("=");
		res=res.concat(destCity);
		res=res.concat("&");
		res=res.concat(DATE_PARAM);
		res=res.concat("=");
		res=res.concat(year+"-"+month+"-"+day);
		res=res.concat("&");
		res=res.concat(ADULTS);
		res=res.concat("=");
		res=res.concat(adults);
		res=res.concat(APPENDIX);
		return res;
		
		
	}
	
	/**
	 * 
	 * @param origin
	 * @param destination
	 * @param day
	 * @param month
	 * @param year
	 * @throws IllegalArgumentException
	 */
	private void validateParams(String origin, String destination, String day,
			String month, String year) throws IllegalArgumentException{

		
		if(!Pattern.matches("\\d{4}", year)){
			throw new IllegalArgumentException("year:" + year);
		}
		if(!Pattern.matches("\\d{2}", month)){
			throw new IllegalArgumentException("month:" + month);
		}
		if(!Pattern.matches("\\d{2}", day)){
			throw new IllegalArgumentException("day:" + day);
		}
		
		int monthI = Integer.parseInt(month);
		int dayI = Integer.parseInt(day);
		int monthNumberOfDays[] = {31,29,31,30,31,30,31,31,30,31,30,31};
		if(monthI < 1 || monthI > 12){
			throw new IllegalArgumentException("month range");
		}
		if(dayI < 1 || dayI > monthNumberOfDays[monthI]){
			throw new IllegalArgumentException("day range");
		}
	}
	
	public static void main(String[] args){
		VuelosBaratosConvenion vBC=new VuelosBaratosConvenion();
		String gen = vBC.generateQuery("MAD", "AMS", "15", "06", "2012", "1");
		System.out.println(gen);
	}
}
