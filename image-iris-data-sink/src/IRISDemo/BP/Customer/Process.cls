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
<sequence xend='200' yend='600' >
<if name='Normalize?' condition='$parameter(request,"%DTL")&apos;=""' xpos='200' ypos='250' xend='200' yend='500' >
<true>
<transform name='Apply Normalization' class='@$parameter(request,"%DTL")' source='request' target='context.NormalizedData' xpos='335' ypos='400' />
</true>
</if>
</sequence>
</process>
}

}
