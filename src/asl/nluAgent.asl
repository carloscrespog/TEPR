// Agent nluAgent skeleton

// A sample plan has been given. The format of the triggering event is
// optional, since it depends on the message send by the userAgent (defined
// by the programmer)

// In order to develop the multi-agent system before the graphs of the unitex
// module, a plan like the 'demo_msg' may be used. That plan uses the 

// 'sendNLU' external action is provided. It must be used to send data to the
// unitex module. The format of the action is as follows:

//   sendNLU("msg to nlu")

// this external action always successes. When the data is processed it 
// includes the beliefs in the agent belief-base.


/* Initial beliefs and rules */

/* Initial goals */
!demo_nlu.

/* Plans */

@demo_msg
+!demo_msg(Msg, Queryid)[source(Ag)] : true
		<-  .send(Ag, tell, location_from(madrid));
			.send(Ag, tell, location_to(barcelona));
			.send(Ag, tell, departure_date(8,5,2012));
			.send(Ag, tell, num_people(3)).

@demo_nlu
+!demo_nlu : true 
	<- sendNLU("Quiero un viaje para 3 personas de madrid a valencia").
	
