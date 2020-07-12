package com.irisdemo.restm2.worker.iris;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.irisdemo.restm2.config.Config;
import com.irisdemo.restm2.workersrv.IWorker;
import com.irisdemo.restm2.workersrv.WorkerMetricsAccumulator;
import com.irisdemo.restm2.workersrv.WorkerSemaphore;

@Component("worker")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IRISWorker implements IWorker 
{
	protected static Logger logger = LoggerFactory.getLogger(IRISWorker.class);
	
    @Autowired
    protected WorkerSemaphore workerSemaphore;
    
    @Autowired 
    protected WorkerMetricsAccumulator accumulatedMetrics;
    
    @Autowired
    protected Config config;    
    
    @Autowired
    protected RandomDataGenerator randomDataGenerator;
    
	protected static char[] prefixes = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	@Async("workerExecutor")
    public CompletableFuture<Long> startOneFeed(String nodePrefix, int threadNum) throws IOException, RestClientException
    {	
		long recordNum = 0;
		String requestString = "";
		
		accumulatedMetrics.incrementNumberOfActiveIngestionThreads();

		logger.info("Ingestion worker #"+threadNum+" started.");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(config.getingestionRESTUserName(), config.getingestionRESTPassword());
		headers.setContentType(MediaType.APPLICATION_JSON);
		randomDataGenerator.initializeRandomMapping();


		String Iris_REST_Endpoint = config.getIngestionRESTEndpoint();
		String schema = config.getIngestionRESTSchemaVersion();

		try
		{

			int currentBatchSize = 0;
			int currentRecord = 0;
			String threadPrefix = nodePrefix+prefixes[threadNum];
			JSONObject requestJSON = new JSONObject();
			JSONObject addressJSON = new JSONObject();
			requestJSON.put("address", addressJSON);

			JSONObject requestJSONBatch = new JSONObject();
			JSONArray requestJSONBatchArray = new JSONArray();
			requestJSONBatch.put("customers", requestJSONBatchArray);

			
			Iris_REST_Endpoint += "/" + schema;

			if (config.getIngestionBatchSize() > 1)
			{
				Iris_REST_Endpoint += "/batch";				 
			}

			
			
			while(workerSemaphore.green())
			{
				randomDataGenerator.populateJSONRequest(requestJSON, threadPrefix, schema, ++currentRecord);
				

				if (config.getIngestionBatchSize() == 1)
				{
					requestString = requestJSON.toString();

					HttpEntity<String> request = new HttpEntity<String>(requestString, headers);

					restTemplate.postForObject(Iris_REST_Endpoint, request, String.class);

					accumulatedMetrics.addToStats(1, requestString.getBytes().length);

				}
				
				else
				{

					//Creating Deep Copies of requestJSON

					JSONObject requestJSONCopy = (JSONObject)requestJSON.clone();
					//needed to make deep copy
					JSONObject addressJSONCopy = (JSONObject)addressJSON.clone();
					requestJSONCopy.put("address", addressJSONCopy);

					requestJSONBatchArray.add(requestJSONCopy);
					

					if (currentBatchSize == config.getIngestionBatchSize())
					{
						requestString = requestJSONBatch.toString();


						HttpEntity<String> request = new HttpEntity<String>(requestString, headers);

						String response = restTemplate.postForObject(Iris_REST_Endpoint, request, String.class);

						accumulatedMetrics.addToStats(currentBatchSize, requestString.getBytes().length);
						requestJSONBatch = new JSONObject();
						requestJSONBatch.clear();
						requestJSONBatchArray.clear();
						requestJSONBatch.put("customers", requestJSONBatchArray);
						if( config.getIngestionWaitTimeBetweenBatchesInMillis() > 0 )
						{
							Thread.sleep(config.getIngestionWaitTimeBetweenBatchesInMillis());
						}
						
						currentBatchSize = 0;
						

					}

					currentBatchSize++;
				}

				recordNum++;
			}

		}

		catch(RestClientException restException)
		{
			logger.error("Ingestion worker #"+threadNum+" crashed with the following error:" + restException.getMessage());
			logger.error("Schema Value is " + schema);
			logger.error("Address being called is " + Iris_REST_Endpoint);

		}

		catch (InterruptedException e) 
		{
			logger.warn("Thread has been interrupted. Maybe the master asked it to stop: " + e.getMessage());
		} 

		catch (Exception e)
		{

			logger.error("Unhandled exception: " + e.getMessage());
		}


		accumulatedMetrics.decrementNumberOfActiveIngestionThreads();

    	logger.info("Ingestion worker #"+threadNum+" finished.");
    	return CompletableFuture.completedFuture(recordNum);
	 }



	 @Override
	 public void resetDemo() throws Exception
	 {
		 //TODO
		 
		 //MAKE REST CALL TO IRIS, WHICH THEN EMPTIES & TRUNCATES TABLE TABLE
	
	}

	
}
