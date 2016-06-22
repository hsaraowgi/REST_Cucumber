package CucumberTest;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeleteSession {
	
	@BeforeTest
	  public void beforeSuite() {
		System.setProperty("TEST_URL", "api.staging.crowdcompass.com");
		System.setProperty("AccessKey", "tFBYNSSFy3ozsc-JysS7");
		System.setProperty("Event", "qeNpEbCxiG");
	  }
	
  @Test
  public void DeleteAllSessions() throws Exception{
	  int ticketscount=0;
		do{	
			
			
		int loop=0;
	try {
	URL url = new URL("https://"+System.getProperty("TEST_URL")+"/v3/events/"+System.getProperty("Event")+"/activities/");
	
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("GET");
	conn.setRequestProperty("Accept", "application/json");
	conn.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
	conn.setDoOutput(true);

	if (conn.getResponseCode() != 200) {
	throw new RuntimeException(" HTTP error code : "
	+ conn.getResponseCode());
	}


	Scanner scan = new Scanner(conn.getInputStream());
	String entireResponse = new String();
	while (scan.hasNext())
	entireResponse += scan.nextLine();


	scan.close();
	JSONObject json = new JSONObject(entireResponse);
	ticketscount = json.getJSONArray("activities").length();

	

	for (loop=0;loop<ticketscount;loop++)
	{
		try {
			
			URL url1 = new URL("https://"+System.getProperty("TEST_URL")+"/v3/events/"+System.getProperty("Event")+"/activities/"+json.getJSONArray("activities").getJSONObject(loop).getString("ref"));
			
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setRequestMethod("DELETE");
			conn1.setRequestProperty("Accept", "application/json");
			conn1.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
			conn1.setDoOutput(true);
			
			if (conn1.getResponseCode()!=204) {
			throw new RuntimeException(" HTTP error code : "
			+ conn.getResponseCode());
			}
			conn1.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();

			} 
	}



	conn.disconnect();
	} catch (MalformedURLException e) {
	e.printStackTrace();

	} 
		}while(ticketscount==100);
		

  }
}
