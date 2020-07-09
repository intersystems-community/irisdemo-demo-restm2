package com.irisdemo.restm2.worker.iris;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.json.simple.JSONObject;

import com.irisdemo.restm2.config.Config;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RandomDataGenerator
{
	protected static Logger logger = LoggerFactory.getLogger(RandomDataGenerator.class);
    
    @Autowired
    protected Config config;    
	
    protected static String[] randomNames;
	
	protected static String[] randomDates;
	
	protected static String[] randomStreets;

	protected static String[] randomStates;

	protected static String[] randomCities;
	
    protected static boolean randomMappingInitialized = false;
    
	/*
	 * This method will prepare the statement on TABLE_SELECT and use that to:
	 * - See how many columns the table has
	 * - Create the paramRandomValues and paramDataTypes based on the number of columns
	 * - Loop on each column and create 1000 random values for it.
	 */
	public synchronized void initializeRandomMapping() throws IOException
	{
		int entries = 1000;
		//String typeName;
		
		if (randomMappingInitialized)
			return;

		randomNames = new String[entries];

		randomDates = new String[entries];

		randomStreets = new String[entries];

		randomStates = new String[entries]; 
		
		for(int i=0; i<entries; i++)
		{
			randomNames[i] = Util.randomName();
			randomDates[i] = Util.randomMySQLStringTimeStamp();
			randomStreets[i] = Util.randomStreet();
			randomStates[i] = Util.randomAlphaNumeric(2);
			randomCities[i] = Util.randomCity();
		
		}
				
		randomMappingInitialized = true;
	}

	public static void populateJSONRequest(JSONObject request, String threadPrefix)
	{	
		int numberOfRandomValues = 999;


		JSONObject address = (JSONObject)request.get("address");
		address.put("street", randomStreets[(int)Math.random()*numberOfRandomValues]);
		address.put("state", randomStates[(int)Math.random()*numberOfRandomValues]);
		address.put("city", randomCities[(int)Math.random()*numberOfRandomValues]);

		request.put("dob", randomDates[(int)Math.random()*numberOfRandomValues]);
		request.put("name", randomNames[(int)Math.random()*numberOfRandomValues]);
		request.put("account_id", threadPrefix);
		request.put("address", address);

	}
}
