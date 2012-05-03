// Agent userAgent skeleton
//
// Messages from the user are expressed as beliefs with the format

//    user_msg("message string here")

// 'sendUser' external action is provided. It can be used to send a literal 
// or a string to the user.

//    sendUser(<literal>|<string>)

// When the literal has the specific format

//    journey(From, To, Departure, Arrival, fare(FName, FPrice))

// the client prints me data in a special format, as it recognizes
// it as a journey.

/* Initial beliefs and rules */

/* Initial goals */

/* Plans*/

@msg
+user_msg(Msg) : true <- sendUser("Hello world!!").
