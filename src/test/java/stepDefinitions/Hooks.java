package stepDefinitions;

import io.cucumber.java.After;

public class Hooks {
	
	
    @After("@LastScenario")
    public void afterLastScenario() {
        System.out.println("Quitting driver after final scenario...");
        utils.DriverManager.quitDriver();
    }
}
