# InterSystems IRIS Data Ingestion and Schema Evolution Example

Demo of InterSystems IRIS dealing with REST schema evolution and data normalization into a canonical model.

On this demo, we use a data simulator to generate REST calls to IRIS. The simulator can be configured to send the 
REST calls following two different JSON schemas: v1 and v2.

Here is an example of the JSON sent using v1:

```JSON
{
    "account_id": "W1B1",
    "name": "Sandy Yapp",
    "dob": "1992-12-31",
    "address": {
        "state": "DE",
        "city": "Cambridge",
        "street": "Ent Boulevard"
    }
}
```

And here is an example of v2:
```JSON
{
    "account_id": "W1A1",
    "fullName": "Vivian Campbell",
    "dob": "1942-03-02",
    "address": {
        "state": "OH",
        "city": "Orlando",
        "street": "Lime Way",
        "zip": "9KMZB"
    }
}
```

As you can see, the two JSON documents are very similar. The idea is to show how IRIS can deal with **Schema Evolution**. On v2, name has been renamed to fullName and the address now contains an additional field.

Here is the architecture of the demo:

![Architecture of Demo](https://raw.githubusercontent.com/intersystems-community/irisdemo-demo-restm2/master/landing-page.png?raw=true)
