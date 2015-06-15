Currency Fair Engineering Hiring Test
=====================================
**IMPORTANT**
Requires Java 8 or higher

Back-end
--------
Change to message-processor directory (module)
```
cd message-processor
```
Create database tables
----------------------
```
mvn clean compile flyway:migrate
```

Generate jar Package
-------------------------
```
mvn clean install package
```

Create messages data
---------------------

```
java -jar target/message-processor-1.0-SNAPSHOT.jar create_messages config.yml

Creating messages:

How many messages do you want to generate?:
5

Placed in how many days before the current date?:
1

{"id":1,"userId":1000013,"currencyFrom":"CHF","currencyTo":"SEK","amountSell":1027.87,"amountBuy":1594.73,"rate":0.62,"timePlaced":"10-JUN-2015 23:58:42","originatingCountry":"GL"}
{"id":2,"userId":1000008,"currencyFrom":"NOK","currencyTo":"DKK","amountSell":1346.05,"amountBuy":1985.05,"rate":0.63,"timePlaced":"11-JUN-2015 01:15:40","originatingCountry":"NO"}
{"id":3,"userId":1000013,"currencyFrom":"USD","currencyTo":"AUD","amountSell":1174.14,"amountBuy":1622.72,"rate":0.51,"timePlaced":"11-JUN-2015 04:07:34","originatingCountry":"JP"}
{"id":4,"userId":1000009,"currencyFrom":"NOK","currencyTo":"HKD","amountSell":1629.95,"amountBuy":1545.34,"rate":0.69,"timePlaced":"11-JUN-2015 08:13:36","originatingCountry":"FM"}
{"id":5,"userId":1000008,"currencyFrom":"CHF","currencyTo":"DKK","amountSell":1965.60,"amountBuy":1094.23,"rate":0.54,"timePlaced":"11-JUN-2015 01:54:56","originatingCountry":"GG"}

```
Remember: *more messages you create, better images and reports you get.*

Start back-end server
-------------------------
```
java -jar target/message-processor-1.0-SNAPSHOT.jar server config.yml 
```


API REST Authentication
------------------------
The message processor use Basic Authorization. For simplicity reasons, the app has a default *user*: **currency** with **fair** as *password*. So, using base64 the encoded value for the text currency:fair is **Y3VycmVuY3k6ZmFpcg==**

Getting a list of messages
------------------------------------

```
curl -v -X GET localhost:8888/messages  -H "Authorization: Basic Y3VycmVuY3k6ZmFpcg=="
```
Will return:

```
[{"id":1,"userId":1000013,"currencyFrom":"CHF","currencyTo":"SEK","amountSell":1027.87,"amountBuy":1594.73,"rate":0.62,"timePlaced":"10-JUN-2015 23:58:42","originatingCountry":"GL"},
{"id":2,"userId":1000008,"currencyFrom":"NOK","currencyTo":"DKK","amountSell":1346.05,"amountBuy":1985.05,"rate":0.63,"timePlaced":"11-JUN-2015 01:15:40","originatingCountry":"NO"},
{"id":3,"userId":1000013,"currencyFrom":"USD","currencyTo":"AUD","amountSell":1174.14,"amountBuy":1622.72,"rate":0.51,"timePlaced":"11-JUN-2015 04:07:34","originatingCountry":"JP"},
{"id":4,"userId":1000009,"currencyFrom":"NOK","currencyTo":"HKD","amountSell":1629.95,"amountBuy":1545.34,"rate":0.69,"timePlaced":"11-JUN-2015 08:13:36","originatingCountry":"FM"},
{"id":5,"userId":1000008,"currencyFrom":"CHF","currencyTo":"DKK","amountSell":1965.60,"amountBuy":1094.23,"rate":0.54,"timePlaced":"11-JUN-2015 01:54:56","originatingCountry":"GG"}]
```

Getting a message by id
---------------------------

```
curl -v -X GET localhost:8888/messages/1  -H "Authorization: Basic Y3VycmVuY3k6ZmFpcg=="
```

Will return:

```
{"id":1,"userId":1000013,"currencyFrom":"CHF","currencyTo":"SEK","amountSell":1027.87,"amountBuy":1594.73,"rate":0.62,"timePlaced":"10-JUN-2015 23:58:42","originatingCountry":"GL"}
```

Deleting a message
-----------------------

```
curl -v -X DELETE localhost:8888/messages/1  -H "Authorization: Basic Y3VycmVuY3k6ZmFpcg=="
```

Will return:

```
* Adding handle: conn: 0x23a2e98
* Adding handle: send: 0
* Adding handle: recv: 0
* Curl_addHandleToPipeline: length: 1
* - Conn 0 (0x23a2e98) send_pipe: 1, recv_pipe: 0
* About to connect() to localhost port 8888 (#0)
*   Trying ::1...
* Connected to localhost (::1) port 8888 (#0)
> DELETE /messages/1 HTTP/1.1
> User-Agent: curl/7.30.0
> Host: localhost:8888
> Accept: */*
> Authorization: Basic Y3VycmVuY3k6ZmFpcg==
>
< HTTP/1.1 200 OK
< Date: Fri, 12 Jun 2015 21:27:07 GMT
< Content-Length: 0
<
* Connection #0 to host localhost left intact
```

Front-end
---------
Change to message-frontend directory 
```
cd message-frontend
```

Create the app
----------------------
```
mvn clean install
```

Start the app
-------------------------
```
mvn jetty:run
```
If everything is OK, you can open the web app at http://localhost:8998/

Stop the app
-------------------------
```
mvn jetty:stop
```