/// 
Class IRISDemo.BP.Customer.Process Extends Ens.BusinessProcessBPL
{

Storage Default
{
<Type>%Storage.Persistent</Type>
}

/// BPL Definition
XData BPL [ XMLNamespace = "http://www.intersystems.com/bpl" ]
{
<process language='objectscript' request='DataSink.OriginalData' response='Ens.Response' height='2000' width='2000' >
<context>
<property name='NormalizedData' type='%Persistent' instantiate='0' />
</context>
<sequence xend='200' yend='700' >
<if name='Normalize?' condition='$parameter(request,"%DTL")&apos;=""' xpos='200' ypos='250' xend='200' yend='500' >
<true>
<transform name='Apply Normalization' class='@$parameter(request,"%DTL")' source='request' target='context.NormalizedData' xpos='200' ypos='400' />
</true>
<false>
<assign name="Use same message" property="context.NormalizedData" value="request" action="set" xpos='470' ypos='400' />
</false>
</if>
<call name='Trace' target='Trace Operation' async='1' xpos='200' ypos='600' >
<request type='DataSink.CanonicalData' >
<assign property="callrequest" value="context.NormalizedData" action="set" />
</request>
</call>
</sequence>
</process>
}

}
