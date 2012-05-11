package es.upm.dit.gsi.sojason.services.travel;

import jason.asSyntax.Literal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.*;

import es.upm.dit.gsi.sojason.beans.JourneyFlight;
import es.upm.dit.gsi.sojason.beans.Perceptable;
import es.upm.dit.gsi.sojason.services.WebServiceConnector;





public class VuelosBaratosScrapper implements WebServiceConnector{

	VuelosBaratosConvenion queryGenerator;
	int vuelosEncontrados;
	private Logger logger = Logger.getLogger("TEPRJason." + VuelosBaratosScrapper.class.getName());
	int bestPrice;

	public VuelosBaratosScrapper(){
		this.queryGenerator=new VuelosBaratosConvenion();
		this.vuelosEncontrados=0;
		this.bestPrice=9999;
		
	}
	

	public Collection<Literal> call(String... params) {
		
		if(!validateParams(params)){
			logger.info("Parametros invalidos");
			return null;
		}
		
		try {
			String queryid = params[0].toString();
			
			List<Perceptable> schedule = getSchedule  ( params[1].toString(), 
														params[2].toString(), 
														params[3].toString(), 
														params[4].toString(), 
														params[5].toString());
			
			// prepare response

			Collection<Literal> res =  new LinkedList<Literal>();
			for (Perceptable travel : schedule){
				if(travel instanceof JourneyFlight){
					((JourneyFlight)travel).setQueryid(queryid); // add the query id
				}
				res.addAll(travel.toPercepts());
			}
			return res;
			
		} catch (IOException e) {
//			return CollectionUtils.wrapList("error(io_exception, \"Some io exception ocurr\")");
			return null;
		}
		
	}

	public List<Perceptable> getSchedule (String origin, String destination, 
			  String day, String month, String year) throws IOException {
	
		this.vuelosEncontrados=0;
		this.bestPrice=9999;
		
		List<Perceptable> journeyList = new LinkedList<Perceptable>();
		journeyList.add(new JourneyFlight());
		String queryUrl = queryGenerator.generateQuery(origin, destination, day, month, year,"1");
		
		logger.info(queryUrl);
		System.out.println("Scrapping:");
		Document doc = Jsoup.connect(queryUrl).timeout(0).get(); //importante el timeout!
		//File aFile=new File("/Users/carloscrespog/blong2.js");
		//Writer output = new BufferedWriter(new FileWriter(aFile));
		try {
			Elements rows = doc.select("script");
			
			
			for (int i=0;i<rows.size();i++){
				String script=rows.get(i).html();
				if(script.startsWith("viewMgr")){
					
					String[] firstSplit= script.split("flights");
					
					//output.write(firstSplit[0]);output.write("\n\n\n");
					//output.write(firstSplit[1]);output.write("\n\n\n\n\n\n\n");
					
					String[] secondSplit= firstSplit[1].split(",updated");
					String flights="{flights"+secondSplit[0];
					flights=flights.concat("}");
					JSONSerializer jsonSer=new JSONSerializer();
					JSONObject flightObject=(JSONObject) jsonSer.toJSON(flights);
					JSONArray flightArray=flightObject.getJSONArray("flights");
					
					int size= flightArray.size();
					
					for(int j=0;j<size;j++){
						JSONObject flight=(JSONObject) flightArray.get(j);
						JSONObject outBound=(JSONObject) flight.get("outbound");
						String departureTime=outBound.getString("time");
						String arrivalTime=outBound.getString("arrive");
						String duration=outBound.getString("durationText");
						String originJ=flight.getString("dep");
						String destinationJ=flight.getString("dest");
						int fee=flight.getInt("value");
						String company=flight.getString("airline");
						if (company.equals("")){
							company=outBound.getString("airline");
						}
						if (company.equals("")){
							company=outBound.getString("aircode");
						}
						JourneyFlight journey=new JourneyFlight(departureTime, arrivalTime, duration,
								originJ, destinationJ, fee, company);
						//output.write(journey.toString());
						//output.write("\n\n\n");
						vuelosEncontrados++;
						//output.write("---------------"+vuelosEncontrados+"------------------");
						//output.write("\n\n\n");
						if(fee<bestPrice){
							journeyList.remove(0);
							journeyList.add(journey);
							bestPrice=fee;
						}
						
					}
					//output.write("\n\n\n");
					
					//output.write("\n\n\n");
					//output.write("---------------"+i+"------------------");
					//output.write("\n\n\n");
					//output.write(flightArray.toString());
					//output.write("\n\n\n");
					
				}
				
			}
			//output.write(flightArray.toString());
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    finally {
		     // output.close();
		      System.out.println("done");
		      System.out.println("Vuelos encontrados: "+vuelosEncontrados);
		    }
		
		
		
		
		
		return journeyList;
		
	}
	
	public JourneyFlight cheapest(List<JourneyFlight> list){
		
		JourneyFlight bestJourney=new JourneyFlight();
		bestJourney.setFee(99999);
		for (JourneyFlight j: list){
			if(j.getFee()<bestJourney.getFee()){
				bestJourney=j;
			}
		}
		return bestJourney;
		
	}
	public boolean validateParams(String... params) {
		if(params.length != 6){
			return false;
		}
		return true;
	}
	public static void main(String [] args) throws IOException{
		VuelosBaratosScrapper vBS=new VuelosBaratosScrapper();
		List<Perceptable> list=vBS.getSchedule("MAD", "BER", "13", "08", "2012");
		//JourneyFlight bestJourney=vBS.cheapest(list);
		//System.out.println(bestJourney.toString());
	}
}
