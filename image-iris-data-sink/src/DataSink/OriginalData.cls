Class DataSink.OriginalData Extends (%Persistent, Ens.Util.MessageBodyMethods, %JSON.Adaptor) [ Abstract, NoExtent ]
{

Parameter %JSONIGNOREINVALIDFIELD As BOOLEAN = 1;

Parameter %DTL As %String;

Property %ProcessingStatus As %String(%JSONINCLUDE = "none", DISPLAYLIST = ",Pending,Processing,Done,Error,Discarded", VALUELIST = ",PEND,PROC,DONE,ERR,DISC") [ InitialExpression = "PEND", Required ];

Property %ErrorMessage As %String(%JSONINCLUDE = "none", MAXLEN = "");

Property %DateReceived As %TimeStamp(%JSONINCLUDE = "none") [ InitialExpression = {$ZDateTime($ZTimeStamp, 3)}, Required ];

Index ProcessingStatusIndex On %ProcessingStatus;

Method %GetContentType() As %String
{
	Quit "text/html"
}

/// This method is called by the Management Portal to 
/// display a message-specific content viewer.<br>
/// This method displays its content by writing out to the current device.
/// The content should match the type returned by the 
/// <method>%GetContentType</method> method.<br>
Method %ShowContents(pZenOutput As %Boolean = 0)
{
    &html<
    <style>
        pre {outline: 1px solid #ccc; padding: 5px; margin: 5px; }
        .string { color: green; }
        .number { color: darkorange; }
        .boolean { color: blue; }
        .null { color: magenta; }
        .key { color: red; }
    </style>
    >
    Write "<script language=""javascript"">"

	Set tWriter = ##class(%JSON.Formatter).%New()
	Set tWriter.Indent = 1
    Do ..%JSONExportToString(.jsonString)
    Write "request = "
    Do tWriter.Format(jsonString)

    &js<
        function output(inp) {
            document.body.appendChild(document.createElement('pre')).innerHTML = inp;
        }

        function syntaxHighlight(json) {
            json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
            return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
                var cls = 'number';
                if (/^"/.test(match)) {
                    if (/:$/.test(match)) {
                        cls = 'key';
                    } else {
                        cls = 'string';
                    }
                } else if (/true|false/.test(match)) {
                    cls = 'boolean';
                } else if (/null/.test(match)) {
                    cls = 'null';
                }
                return '<span class="' + cls + '">' + match + '</span>';
            });
        }

        var str = JSON.stringify(request, null, 4);

        output(syntaxHighlight(str));

    >
    Write "</script>"
}

}
