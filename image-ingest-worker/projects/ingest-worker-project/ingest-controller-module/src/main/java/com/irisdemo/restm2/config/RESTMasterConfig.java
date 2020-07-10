package com.irisdemo.restm2.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTMasterConfig 
{	
	/* 
	INGESTION CONFIGURATION 
	*/
	public int ingestionBatchSize;
	public int ingestionNumThreadsPerWorker;
	public boolean disableJournalForDropTable;
	public boolean disableJournalForTruncateTable;
	public int databaseSizeInGB;
	public int ingestionWaitTimeBetweenBatchesInMillis;
	public String ingestionRESTEndpoint;
	public String ingestionRESTSchemaVersion;
	public String ingestionRESTUserName;
	public String ingestionRESTPassword;
	/*
	 * Statements
	 */
	public String insertStatement;
	public String queryStatement;
	public String queryByIdStatement;
	public String irisProcDisableJournalDrop;
	public String irisProcDisableJournal;
	public String irisProcEnableCallInService;
	public String tableDropStatement;
	public String tableCreateStatement;
	public String tableTruncateStatement;

}
