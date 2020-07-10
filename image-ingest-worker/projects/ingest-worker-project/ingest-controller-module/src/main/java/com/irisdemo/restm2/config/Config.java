package com.irisdemo.restm2.config;

import org.springframework.stereotype.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Config 
{
	Logger logger = LoggerFactory.getLogger(Config.class);
	
	/*
	WORKER CONFIGURATION COMING FROM THE MASTER
	*/

	private String masterHostName;
	private String masterPort;
	private String thisHostName;
	private int thisServerPort;
	private String workerNodePrefix;
	private int databaseSizeInGB;
	
	private boolean disableJournalForDropTable;
	private boolean disableJournalForTruncateTable;

	private int ingestionBatchSize;
	private int ingestionNumThreadsPerWorker;
	private int ingestionWaitTimeBetweenBatchesInMillis;
	private String ingestionRESTEndpoint;
	private String ingestionRESTSchemaVersion;
	private String ingestionRESTUserName;
	private String ingestionRESTPassword;
	
	/*
	 * Statements
	 */
	private String insertStatement;
	private String queryByIdStatement;
	private String queryStatement;
	private String irisProcDisableJournalDrop;
	private String irisProcDisableJournal;
	private String irisProcEnableCallInService;
	private String tableDropStatement;
	private String tableCreateStatement;
	private String tableTruncateStatement;
	
	
	public void setIngestionWaitTimeBetweenBatchesInMillis(int ingestionWaitTimeBetweenBatchesInMillis)
	{
		this.ingestionWaitTimeBetweenBatchesInMillis=ingestionWaitTimeBetweenBatchesInMillis;
	}

	public int getIngestionWaitTimeBetweenBatchesInMillis()
	{
		return this.ingestionWaitTimeBetweenBatchesInMillis;
	}

	public void setWorkerNodePrefix(String workerNodePrefix)
	{
		this.workerNodePrefix=workerNodePrefix;
	}
	
	public String getWorkerNodePrefix()
	{
		return this.workerNodePrefix;
	}
		
	public void setInsertStatement(String insertStatement) 
	{
		logger.info("Setting INSERT statement = " + insertStatement);
		this.insertStatement=insertStatement;
	}

	public String getQueryStatement() 
	{
		return queryStatement;
	}

	public String getQueryByIdStatement() 
	{
		return queryByIdStatement;
	}

	public void setQueryStatement(String queryStatement) 
	{
		logger.info("Setting QUERY statement = " + queryStatement);
		this.queryStatement=queryStatement;
	}

	public void setQueryByIdStatement(String queryByIdStatement) 
	{
		logger.info("Setting QUERY By ID statement = " + queryByIdStatement);
		this.queryByIdStatement=queryByIdStatement;
	}

	public String getInsertStatement() 
	{
		return insertStatement;
	}
	
	@Value( "${HOSTNAME}" )
	public void setThisHostName(String thisHostName) {
		logger.info("This hostname is " + thisHostName);
		this.thisHostName = thisHostName;
	}

	public void setThisServerPort(int thisServerPort) {
		logger.info("This server port is " + thisServerPort);
		this.thisServerPort = thisServerPort;
	}

	public int getDatabaseSizeInGB() {
		return this.databaseSizeInGB;
	}

	public void setDatabaseSizeInGB(int databaseSizeInGB) {
		logger.info("Database size set to " + databaseSizeInGB + "GB." );
		this.databaseSizeInGB = databaseSizeInGB;
	}

	public int getThisServerPort() {
		return this.thisServerPort;
	}

	public String getThisHostName() {
		return thisHostName;
	}

	public String getMasterHostName() {
		return masterHostName;
	}
		
	@Value( "${MASTER_HOSTNAME}" )
	public void setMasterHostName(String masterHostName) {
		logger.info("Setting MASTER_HOSTNAME = " + masterHostName);
		this.masterHostName = masterHostName;
	}

	public String getMasterPort() {
		return masterPort;
	}
		
	@Value( "${MASTER_PORT:80}" )
	public void setMasterPort(String masterPort) {
		logger.info("Setting MASTER_PORT = " + masterPort);
		this.masterPort = masterPort;
	}
	
	public int getIngestionNumThreadsPerWorker() 
	{
		return ingestionNumThreadsPerWorker;
	}
	
	public void setIngestionNumThreadsPerWorker(int value) 
	{
		logger.info("Setting INGESTION_THREADS_PER_WORKER = " + value);
		ingestionNumThreadsPerWorker=value;
	}

	public int getIngestionBatchSize() {
		return ingestionBatchSize;
	}
	
	public void setIngestionBatchSize(int ingestionBatchSize) {
		logger.info("Setting INGESTION_BATCH_SIZE = " + ingestionBatchSize);
		this.ingestionBatchSize = ingestionBatchSize;
	}

	public boolean getDisableJournalForDropTable() {
		return disableJournalForDropTable;
	}
	
	public void setDisableJournalForDropTable(boolean disableJournalForDropTable) {
		logger.info("Setting DISABLE_JOURNAL_FOR_DROP_TABLE = " + disableJournalForDropTable);
		this.disableJournalForDropTable = disableJournalForDropTable;
	}

	public boolean getDisableJournalForTruncateTable() {
		return disableJournalForTruncateTable;
	}
	
	public void setDisableJournalForTruncateTable(boolean disableJournalForTruncateTable) {
		logger.info("Setting DISABLE_JOURNAL_FOR_TRUNCATE_TABLE = " + disableJournalForTruncateTable);
		this.disableJournalForTruncateTable = disableJournalForTruncateTable;
	}

	public String getIrisProcDisableJournal() {
		return irisProcDisableJournal;
	}

	public void setIrisProcDisableJournal(String irisProcDisableJournal) {
		logger.info("Got disable journal create procedure for IRIS.");
		this.irisProcDisableJournal = irisProcDisableJournal;
	}

	public String getIrisProcEnableCallInService() {
		return irisProcEnableCallInService;
	}

	public void setIrisProcEnableCallInService(String irisProcEnableCallInService) {
		logger.info("Got IRIS procedure for enabling CallIn Service for XEP.");
		this.irisProcEnableCallInService = irisProcEnableCallInService;
	}

	public String getTableDropStatement() {
		return tableDropStatement;
	}

	public void setTableDropStatement(String tableDropStatement) {
		logger.info("Got table drop statement.");
		this.tableDropStatement = tableDropStatement;
	}

	public String getTableCreateStatement() {
		return tableCreateStatement;
	}

	public void setTableCreateStatement(String tableCreateStatement) {
		logger.info("Got table create statement.");
		this.tableCreateStatement = tableCreateStatement;
	}

	public String getIrisProcDisableJournalDrop() {
		return irisProcDisableJournalDrop;
	}

	public void setIrisProcDisableJournalDrop(String irisProcDisableJournalOnDrop) {
		logger.info("Got disable journal drop procedure for IRIS.");
		this.irisProcDisableJournalDrop = irisProcDisableJournalOnDrop;
	}

	public String getTableTruncateStatement() {
		return tableTruncateStatement;
	}

	public void setTableTruncateStatement(String tableTruncateStatement) {
		logger.info("Got table truncate statement.");
		this.tableTruncateStatement = tableTruncateStatement;
	}

	public void setIngestionRESTEndpoint(String restEndPoint)
	{
		logger.info("Got REST endpoint.");
		ingestionRESTEndpoint = restEndPoint;
	}

	public String getIngestionRESTEndpoint()
	{
		return ingestionRESTEndpoint;
	}

	public void setIngestionRESTSchemaVersion(String restSchemaVersion)
	{
		logger.info("Got REST schema version.");
		ingestionRESTSchemaVersion = restSchemaVersion;
	}

	public String getIngestionRESTSchemaVersion()
	{
		return ingestionRESTSchemaVersion;
	}

	public void setingestionRESTUserName(String Username)
	{
		logger.info("Got REST endpoint.");
		ingestionRESTUserName = Username;
	}

	public String getingestionRESTUserName()
	{
		return ingestionRESTUserName;
	}
	public void setingestionRESTPassword(String Password)
	{
		logger.info("Got REST endpoint.");
		ingestionRESTPassword = Password;
	}

	public String getingestionRESTPassword()
	{
		return ingestionRESTPassword;
	}


}