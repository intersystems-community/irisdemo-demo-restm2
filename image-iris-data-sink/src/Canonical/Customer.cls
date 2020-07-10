Class Canonical.Customer Extends %Persistent
{

Property AccountId As %String [ Required ];

Property FullName As %String [ Required ];

Property DateOfBirth As %Date [ Required ];

Property Address As Address;

Storage Default
{
<Data name="CustomerDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>AccountId</Value>
</Value>
<Value name="3">
<Value>FullName</Value>
</Value>
<Value name="4">
<Value>DateOfBirth</Value>
</Value>
<Value name="5">
<Value>Address</Value>
</Value>
</Data>
<DataLocation>^Canonical.CustomerD</DataLocation>
<DefaultData>CustomerDefaultData</DefaultData>
<IdLocation>^Canonical.CustomerD</IdLocation>
<IndexLocation>^Canonical.CustomerI</IndexLocation>
<StreamLocation>^Canonical.CustomerS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}