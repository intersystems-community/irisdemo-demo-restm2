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
		<Route Url="/" Method="post" Call="Create" />
	</Routes>
}

ClassMethod Create() As %Status
{
	Set tSC = $$$OK
	Try
	{
		// Configuring response type to JSON
		Set %response.ContentType=..#CONTENTTYPEJSON
		Set response = {}
		Set response.status = ..#HTTP200OK

		Set original = ##class(Original.Customer).%New()
		Set tSC = original.%JSONImport(%request.Content)
		Quit:$$$ISERR(tSC)

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
