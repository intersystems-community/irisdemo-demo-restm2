Class Original.V1.CustomerBatch Extends (%Persistent, %JSON.Adaptor)
{

Property customers As list Of Customer;

Storage Default
{
<Data name="CustomerBatchDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>customers</Value>
</Value>
</Data>
<DataLocation>^Original.V1.CustomerBatchD</DataLocation>
<DefaultData>CustomerBatchDefaultData</DefaultData>
<IdLocation>^Original.V1.CustomerBatchD</IdLocation>
<IndexLocation>^Original.V1.CustomerBatchI</IndexLocation>
<StreamLocation>^Original.V1.CustomerBatchS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
