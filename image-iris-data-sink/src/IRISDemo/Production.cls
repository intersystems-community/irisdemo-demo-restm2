Class IRISDemo.Production Extends IRISDemo.BaseProduction
{

XData ProductionDefinition
{
<Production Name="IRISDemo.Production" TestingEnabled="true" LogGeneralTraceEvents="true">
  <Description></Description>
  <ActorPoolSize>2</ActorPoolSize>
  <Item Name="Customer v1 Original Data Sink" Category="" ClassName="DataSink.DataCollect.Service" PoolSize="1" Enabled="true" Foreground="false" Comment="" LogTraceEvents="true" Schedule="">
    <Setting Target="Host" Name="OriginalDataSource">Original.V1.Customer</Setting>
    <Setting Target="Host" Name="TargetConfigName">Customer Normalization Process</Setting>
  </Item>
  <Item Name="Customer Normalization Process" Category="" ClassName="IRISDemo.BP.Customer.Process" PoolSize="1" Enabled="true" Foreground="false" Comment="" LogTraceEvents="false" Schedule="">
  </Item>
</Production>
}

}
