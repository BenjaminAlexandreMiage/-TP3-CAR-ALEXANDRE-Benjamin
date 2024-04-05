package com.example.tp3_CAR.akka;

import java.util.HashMap;

import akka.actor.UntypedActor;

public class Reducer extends UntypedActor {
	
	private HashMap<String,Integer> mots = new HashMap<String,Integer>(); 

	@Override
	public void onReceive(Object message){
		
		if(message instanceof GreetingMessage m) {	
			
			System.out.println("Reducer : Message de : " + m.who());
			
			if(mots.get(m.who())==null) {
				mots.put(m.who(),1);
			}
			else {
				mots.put(m.who(),mots.get(m.who())+1);
			}
			
			
			System.out.println(mots);
		}
		
		if(message instanceof requestMessage rm) {
			
			System.out.println("Reducer : Message de : " + rm.mot());
		
			getSender().tell(new reponseMessage(mots.get(rm.mot())),getSelf());
	
		}
	}
	

}