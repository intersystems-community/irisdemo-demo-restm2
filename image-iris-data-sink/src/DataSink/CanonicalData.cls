Class DataSink.CanonicalData Extends REST.JSONRequest [ Abstract, NoExtent ]
{

Property %SessionId As %String;

Property %DataSourceName As %String;

Property %VersionDateTime As %TimeStamp [ InitialExpression = {$ZDateTime($ZTimeStamp,3)}, Required ];

}
