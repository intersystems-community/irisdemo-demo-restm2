Class DataSink.OriginalData Extends (Ens.Request, %JSON.Adaptor) [ Abstract, NoExtent ]
{

Parameter %JSONIGNOREINVALIDFIELD As BOOLEAN = 1;

Parameter %DTL As %String;

Property %ProcessingStatus As %String(%JSONINCLUDE = "none", DISPLAYLIST = ",Pending,Processing,Done,Error,Discarded", VALUELIST = ",PEND,PROC,DONE,ERR,DISC") [ InitialExpression = "PEND", Required ];

Property %ErrorMessage As %String(%JSONINCLUDE = "none", MAXLEN = "");

Property %DateReceived As %TimeStamp(%JSONINCLUDE = "none") [ InitialExpression = {$ZDateTime($ZTimeStamp, 3)}, Required ];

Index ProcessingStatusIndex On %ProcessingStatus;

}
