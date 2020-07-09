Include DEMOMACROS

/// As per the documentation on https://docs.intersystems.com/latest/csp/docbook/DocBook.UI.Page.cls?KEY=EREST_service, one
/// should not use EnsLib.REST.Service to create REST business services with IRIS Interoperability. We should use %CSP.REST instead.
/// So, this dispatcher class will serve as an example of how to create a rest service that is calling a business service while
/// a better way of doing it is not available.
Class IRISDemo.REST.Dispatcher Extends %CSP.REST
{

Parameter HandleCorsRequest = 1;

Parameter UseSession = 1;

Parameter CONVERTINPUTSTREAM = 1;

Parameter CHARSET = "utf-8";

XData UrlMap
{
<Routes>
		<Route Url="/v1" Method="post" Call="CreateV1" />
		<Route Url="/v1/batch" Method="post" Call="CreateV1Batch" />
		<Route Url="/v2" Method="post" Call="CreateV2" />
		<Route Url="/v2/batch" Method="post" Call="CreateV2Batch" />
	</Routes>
}

ClassMethod CreateV1() As %Status
{
	Quit ..Create("V1")
}

ClassMethod CreateV2() As %Status
{
	Quit ..Create("V2")
}

ClassMethod CreateV1Batch() As %Status
{
	Quit ..Create("V1", 1)
}

ClassMethod CreateV2Batch() As %Status
{
	Quit ..Create("V2", 1)
}

ClassMethod Create(pVersion As %String, pBatchMode As %Boolean = 0) As %Status
{
	Set tSC = $$$OK
	Try
	{
		// Configuring response type to JSON
		Set %response.ContentType=..#CONTENTTYPEJSON
		Set response = {}
		Set response.status = ..#HTTP200OK

		// Instantiating the right class for the version
		If pBatchMode
		{
			Set tClassName="Original."_pVersion_".CustomerBatch"
		}
		Else 
		{
			Set tClassName="Original."_pVersion_".Customer"
		}
		
		Set original = $classmethod(tClassName, "%New")

		// Importing the JSON into the instance
		Set tSC = original.%JSONImport(%request.Content)
		Quit:$$$ISERR(tSC)

		// Saving to disk
		Set tSC = original.%Save()
		Quit:$$$ISERR(tSC)

		Set response.id=original.%Id()
	}
	Catch (oException)
	{
		Set tSC = oException.AsStatus()
	}

	If $$$ISERR(tSC)
	{
		Set %response.Status = ..#HTTP500INTERNALSERVERERROR
		Set response.status = ..#HTTP500INTERNALSERVERERROR
		
		// Bad practice:
		Set response.statusError = $System.Status.GetErrorText(tSC)
	}

	// Whatever the response is, let's send it
	Write response.%ToJSON()

	Quit $$$OK
}

}
