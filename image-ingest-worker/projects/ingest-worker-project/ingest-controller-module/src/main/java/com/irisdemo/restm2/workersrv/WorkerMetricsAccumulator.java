package com.irisdemo.restm2.workersrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WorkerMetricsAccumulator 
{
	@Autowired
	WorkerSemaphore workerSemaphore;
    
    private int numberOfActiveIngestionThreads = 0;

	private double startTimeInMillis;
    private double timeSinceLastAggInMillis;
    
    private double numberOfRowsIngested;
    
    private double recordsIngestedPerSec;
    private double avgRecordsIngestedPerSec;

    private double bytesIngested;
    private double MBIngested;

    private double MBIngestedPerSec;    
    private double avgMBIngestedPerSec;
    
    /*
     * Variables used to compute the aggregated values
     */
    private double previousNumberOfRowsIngested;
    private double previousMBIngested;
    
    /*
     * Getters and setters for the outside world
     */
    
    /**
     * This method is called by the WorkerService just before the workers are started so
     * that aggregated metrics can be computed properly
     * @param startTimeInMillis
     */
    public void reset()
    {
        this.startTimeInMillis=System.currentTimeMillis();
        this.timeSinceLastAggInMillis=this.startTimeInMillis;
    	this.numberOfRowsIngested=0;
    	this.recordsIngestedPerSec=0;
    	this.avgRecordsIngestedPerSec=0;
    	this.MBIngested=0;
    	this.MBIngestedPerSec=0;
    	this.avgMBIngestedPerSec=0;
    	this.previousNumberOfRowsIngested=0;
    	this.previousMBIngested=0;
        this.bytesIngested=0;
        this.numberOfActiveIngestionThreads=0;
    }
        
	synchronized public void incrementNumberOfActiveIngestionThreads()
	{
		numberOfActiveIngestionThreads++;
	}

	synchronized public void decrementNumberOfActiveIngestionThreads()
	{
		numberOfActiveIngestionThreads--;
    }
    
    public int getNumberOfActiveIngestionThreads()
    {
        return numberOfActiveIngestionThreads;
    }

    public double getNumberOfRowsIngested() {
        return numberOfRowsIngested;
    }

    /**
     * Called by Worker threads to add to stats 
     * @param numberOfRowsIngested
     * @param MBIngested
     */
    synchronized public void addToStats(double numberOfRowsIngested, double bytesIngested) {
        this.numberOfRowsIngested+= numberOfRowsIngested;
        this.bytesIngested+=bytesIngested;
        this.MBIngested=this.bytesIngested/1024/1024;
    }

    synchronized public double getRecordsIngestedPerSec() {
        return recordsIngestedPerSec;
    }

    synchronized  public double getAvgRecordsIngestedPerSec() {
        return avgRecordsIngestedPerSec;
    }

    synchronized public double getMBIngested() {
        return MBIngested;
    }

    synchronized public double getMBIngestedPerSec() {
        return MBIngestedPerSec;
    }

    synchronized public double getAvgMBIngestedPerSec() {
        return avgMBIngestedPerSec;
    }
    
    @Scheduled(fixedRate = 1000)
    synchronized protected void computeAggregatedMetrics()
    {
    	if (workerSemaphore.green())
    	{
			double deltaNumberOfRowsIngested = numberOfRowsIngested - previousNumberOfRowsIngested;
			
			//Just a second precaution to stop recomputing the metrics 
			//if we are stopping the workers and no new records have been ingested.
			if (deltaNumberOfRowsIngested>0) 
			{	
                double currentTimeInMillis = System.currentTimeMillis();
                
                double ellapsedTimeSinceLastAggInSeconds = (currentTimeInMillis - timeSinceLastAggInMillis)/1000d;
                timeSinceLastAggInMillis = currentTimeInMillis;

	    		double ellapsedTimeInMillis = (currentTimeInMillis - startTimeInMillis);
                double ellapsedTimeInSeconds = ellapsedTimeInMillis/1000d;
                
				previousNumberOfRowsIngested = numberOfRowsIngested;
				this.recordsIngestedPerSec = deltaNumberOfRowsIngested / ellapsedTimeSinceLastAggInSeconds;
						
				this.MBIngestedPerSec = (MBIngested - previousMBIngested) / ellapsedTimeSinceLastAggInSeconds;
				previousMBIngested = MBIngested;
				
				avgRecordsIngestedPerSec = numberOfRowsIngested / ellapsedTimeInSeconds;
				
				avgMBIngestedPerSec = MBIngested/ ellapsedTimeInSeconds;
			}
    	}
    }
}
