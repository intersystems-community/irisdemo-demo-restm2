Class Original.Customer Extends (%Persistent, DataSink.OriginalData)
{

Property "account_id" As %String [ Required ];

Property name As %String [ Required ];

Property dob As %Date [ Required ];

Property address As Address;

Storage Default
{
<Data name="CustomerDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>%DateReceived</Value>
</Value>
<Value name="3">
<Value>name</Value>
</Value>
<Value name="4">
<Value>dob</Value>
</Value>
<Value name="5">
<Value>address</Value>
</Value>
<Value name="6">
<Value>account_id</Value>
</Value>
</Data>
<DataLocation>^Original.CustomerD</DataLocation>
<DefaultData>CustomerDefaultData</DefaultData>
<IdLocation>^Original.CustomerD</IdLocation>
<IndexLocation>^Original.CustomerI</IndexLocation>
<StreamLocation>^Original.CustomerS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
