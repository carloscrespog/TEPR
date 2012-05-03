// Agent travelAgent skeleton
//
// 'findTravel' external action is provided. The exact format of the action
// is as follows:

//    findTravel(Queryid, From, To, Day, Month, Year)

// where the Queryid is a number that may be used to identify the the results
// of a particular call. this external action always successes. When the data 
// is processed it includes the beliefs in the agent belief-base. The format
// of the beliefs is as follows:

//   journey(<From>, <To>, <Departure>, <Arrival>, <Fare>)[query(Queryid)]
//   journey(madrid, barcelona, time(11,00), time(14,30), fare(FName, FPrice))[query(12345)]


/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

@demo_find
+!demo_find :true
	<- 	findTravel(12345, madrid, barcelona, 9, 5, 2012).

/* Store results */
@storejourney1
+journey(From, To, Departure, Arrival, fare(FName, FPrice))[query(Query)]
	: 	true
	<- 	.print(journey(From, To, Departura, Arrival, fare(FName, FPrice)));
		+journey(From, To, Departure, Arrival, fare(FName, FPrice)).

