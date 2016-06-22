package CucumberTest;

import cucumber.api.java.Before;

public class RunningHook {
	@Before
	public void setValues(){
		System.setProperty("TEST_URL", "api.staging.crowdcompass.com");
		System.setProperty("AccessKey", "tFBYNSSFy3ozsc-JysS7");
		System.setProperty("Event", "qeNpEbCxiG");
		
		
	}
}
