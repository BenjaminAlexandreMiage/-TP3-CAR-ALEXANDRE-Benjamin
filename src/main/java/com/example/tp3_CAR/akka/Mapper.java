package com.example.tp3_CAR.akka;



import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Mapper extends UntypedActor {
	
	private ActorRef reducer1;
	private ActorRef reducer2;

	public Mapper(ActorRef reducer1, ActorRef reducer2) {
		super();
		this.reducer1 = reducer1;
		this.reducer2 = reducer2;
	}

	@Override
	public void onReceive(Object message) {
		
		if(message instanceof GreetingMessage m) {
			
			System.out.println("Mapper : Message de : " + m.who());
			
			String[] mots = m.who().split(" ");
			
			for (String mot : mots) {
				
				if(mot.length()<=4) {
					this.reducer1.tell(new GreetingMessage(mot),getSelf());
				}
				else {
					this.reducer2.tell(new GreetingMessage(mot),getSelf());
				}
	        }
		}
		
	}

}
