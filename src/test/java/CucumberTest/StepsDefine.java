package CucumberTest;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import org.testng.Assert;


public class StepsDefine {
	int ticketscount=0;
	HttpURLConnection conn=null;
	
	Scanner scan=null;
	String entireResponse=null;
	@Given("^I have the list of top 100 Invitations$")
	public void getallinvites() throws Throwable {
		try {
			URL url = new URL("https://"+System.getProperty("TEST_URL")+"/v3/events/"+System.getProperty("Event")+"/invitations/");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
			conn.setDoOutput(true);
		int resp=conn.getResponseCode();
			if (resp != 200) {
			throw new RuntimeException(" HTTP error code : "
			+ resp);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		scan = new Scanner(conn.getInputStream());
		entireResponse = new String();
	}
	
	@Given("^All 100 invitations saved locally$")
	public void savelocally() throws Throwable {
		
		while (scan.hasNext())
		entireResponse += scan.nextLine();

		

		scan.close();
	}

	@When("^i call delete function for all 100 Invitations individually$")
	public void deleteinvites() throws Throwable {
		JSONObject json = new JSONObject(entireResponse);
		ticketscount = json.getJSONArray("invitations").length();
		for (int loop = 0;loop<ticketscount;loop++)
		{
			try {
				URL url1 = new URL("https://"+System.getProperty("TEST_URL")+"/v3/events/"+System.getProperty("Event")+"/invitations/"+json.getJSONArray("invitations").getJSONObject(loop).getString("ref"));
				
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
	}
	
	@But("^There are still invitations remaining so call all steps again to delete next 100 and do that till all are deleted")
	public void repetTillAll() throws Throwable {

		  while(ticketscount==100)
		  {
			  getallinvites();
			  savelocally();
			  deleteinvites();
		  }

		
	}

	@Then("^Trying to get all invitaions again will respond with blank array but 200 response$")
	public void checkifremaining() throws Throwable {

		try {
			URL url = new URL("https://"+System.getProperty("TEST_URL")+"/v3/events/"+System.getProperty("Event")+"/invitations/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
			conn.setDoOutput(true);
		int resp=conn.getResponseCode();
			if (resp != 200) {
			throw new RuntimeException(" HTTP error code : "
			+ resp);
			}
			Assert.assertEquals(resp, 200);


			Scanner scan = new Scanner(conn.getInputStream());
			String entireResponse = new String();
			while (scan.hasNext())
			entireResponse += scan.nextLine();

			

			scan.close();
			JSONObject json = new JSONObject(entireResponse);
			ticketscount = json.getJSONArray("invitations").length();	 
	
		conn.disconnect();
	} catch (MalformedURLException e) {
	e.printStackTrace();

	} 
		Assert.assertEquals(ticketscount, 0);
	}
	
	
}
