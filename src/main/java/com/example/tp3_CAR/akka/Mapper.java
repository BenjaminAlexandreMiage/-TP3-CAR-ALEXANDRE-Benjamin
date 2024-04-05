package com.example.tp3_CAR.akka;



import java.util.*;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Mapper extends UntypedActor {
	
	private List tabReducer = new ArrayList();
	

	@Override
	public void onReceive(Object message) {
		
		if(message instanceof GreetingMessage m) {
			
			System.out.println(this.tabReducer);
			
			System.out.println("Mapper : Message de : " + m.who());
			
			String[] mots = m.who().split(" ");
			
			for (String mot : mots) {
				
				if(mot.length()<=4) {
					((ActorRef) this.tabReducer.get(0)).tell(new GreetingMessage(mot),getSelf());
					
				}
				else {
					((ActorRef) this.tabReducer.get(1)).tell(new GreetingMessage(mot),getSelf());
					
				}
	        }
		}
		
		if (message instanceof requestReducer r) {
			System.out.println("r est : "+r.r());
			this.tabReducer.add(r.r());
		}
		
	}

}
