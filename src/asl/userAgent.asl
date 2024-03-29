// Agent userAgent skeleton
//
// Messages from the user are expressed as beliefs with the format

//    user_msg("message string here")

// 'sendUser' external action is provided. It can be used to send a literal 
// or a string to the user.

//    sendUser(<literal>|<string>)

// When the literal has the specific format

//    journey(From, To, Departure, Arrival, fare(FName, FPrice)), journey(madrid, valencia, time(13,30), time(15,45), fare(turista, 22,50))

// the client prints me data in a special format, as it recognizes
// it as a journey.

/* Initial beliefs and rules */

/* Initial goals */

/* Plans*/

@msg
//+user_msg(Msg) : true <- sendUser(journey(madrid, valencia, time(13,30), time(15,45), fare(turista, 22,50))).
+user_msg(Msg) : true <- .send(nluAgent, tell, msg_NLU(Msg)).
/*
+journey(From, To, Departure, Arrival, fare(FName, FPrice)): true
	<-sendUser(journey(From,To,Departure,Arrival,fare(FName,FPrice)));
	.abolish(journey(From, To, Departure, Arrival, fare(FName, FPrice))).
	*/

+journey(From, To, Departure, Arrival, Fare, Company): true
	<-sendUser(journey(From,To,Departure,Arrival,fare(Company,Fare)));
	.abolish(journey(From, To, Departure, Arrival, Fare, Company)).

