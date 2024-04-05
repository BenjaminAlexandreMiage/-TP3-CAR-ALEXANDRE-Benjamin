package com.example.tp3_CAR.akka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import scala.concurrent.duration.FiniteDuration;

@Service
public class AkkaServiceImpl implements AkkaService{
	
	private ActorSystem system;
	private ActorRef mapper1,mapper2,mapper3,reducer1,reducer2;
	private Inbox inbox;

	@Override
	public void createActeur() {
		
		system = ActorSystem.create("MySystem");
		
		reducer1 = system.actorOf(Props.create(Reducer.class),"reducer1");
		reducer2 = system.actorOf(Props.create(Reducer.class),"reducer2");
		
		mapper1 = system.actorOf(Props.create(Mapper.class,reducer1,reducer2),"mapper1");
		mapper2 = system.actorOf(Props.create(Mapper.class,reducer1,reducer2),"mapper2");
		mapper3 = system.actorOf(Props.create(Mapper.class,reducer1,reducer2),"mapper3");
		
		
		inbox = Inbox.create(system);
		
	}

	@Override
	public void transmissionLigne(String fichier) {
		
		try {
			
			FileReader fileReader = new FileReader(fichier);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line = reader.readLine();
			int nmapper = 0;
			while (line != null) {
				
				if(nmapper==0 || nmapper==1) {
					if(nmapper==0) {
						mapper1.tell(new GreetingMessage(line),ActorRef.noSender());
					}
					else {
						mapper2.tell(new GreetingMessage(line),ActorRef.noSender());
					}
					nmapper += 1;
				}
				else{
					mapper3.tell(new GreetingMessage(line),ActorRef.noSender());
					nmapper=0;
				}
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			
		}
		
	}
	
	
	
	public int getNbOccurenceByMot(String mot) {
		
		int reponse = 0;
		
		if(mot.length()<=4) {
			inbox.send(reducer1, new requestMessage(mot));
		}
		
		else {
			inbox.send(reducer2, new requestMessage(mot));
		}
		
		Object reply = null;
		
		try {
			
			reply = inbox.receive(FiniteDuration.create(5,TimeUnit.SECONDS));
			
		} catch (TimeoutException e) {
			//e.printStackTrace();
		}
		
		if( reply instanceof reponseMessage rm ){
			
			System.out.println("Réponse : "+rm.reponse()); 
			
			reponse = rm.reponse();
		
		}
		
		return reponse;
		
	}

}
