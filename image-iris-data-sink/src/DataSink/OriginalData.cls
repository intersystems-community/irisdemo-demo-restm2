Class DataSink.OriginalData Extends %JSON.Adaptor [ Abstract ]
{

Parameter %JSONIGNOREINVALIDFIELD As BOOLEAN = 1;

Property %DateReceived As %TimeStamp(%JSONINCLUDE = "none") [ InitialExpression = {$ZDateTime($ZTimeStamp, 3)}, Required ];

}
