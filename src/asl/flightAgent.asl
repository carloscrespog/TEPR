+find(City_From,City_To,Day,Month,Year): true
	<-  
		findFlight(12345,City_From, City_To,Day, Month, Year).
		
+journey(From, To, Departure, Arrival, Fare,Company)[query(Query)]
	: 	true
	<- 	.print(journey(From, To, Departure, Arrival, Fare,Company));
		+journey(From, To, Departure, Arrival, Fare,Company);
		.send(userAgent,tell,journey(From, To, Departure, Arrival,Fare,Company)).
