Class Original.V2.Customer Extends (%Persistent, DataSink.OriginalData)
{

Property "account_id" As %String [ Required ];

Property fullName As %String [ Required ];

Property dob As %Date [ Required ];

Property address As Address;

}
