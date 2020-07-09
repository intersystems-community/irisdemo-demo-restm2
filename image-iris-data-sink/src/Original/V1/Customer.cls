Class Original.V1.Customer Extends (%Persistent, DataSink.OriginalData)
{

Property "account_id" As %String [ Required ];

Property name As %String [ Required ];

Property dob As %Date [ Required ];

Property address As Address;

}
