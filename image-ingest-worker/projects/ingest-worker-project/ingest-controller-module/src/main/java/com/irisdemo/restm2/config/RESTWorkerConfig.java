package com.irisdemo.restm2.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTWorkerConfig 
{
	public String workerNodePrefix;
	public RESTMasterConfig config;
}
