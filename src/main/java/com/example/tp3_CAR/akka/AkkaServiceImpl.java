package com.example.tp3_CAR.akka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import org.springframework.stereotype.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

@Service
public class AkkaServiceImpl implements AkkaService{
	
	private ActorSystem system;
	private ActorRef mapper1,mapper2,mapper3,reducer1,reducer2;

	@Override
	public void createActeur() {
		
		system = ActorSystem.create("MySystem");
		
		reducer1 = system.actorOf(Props.create(Reducer.class),"reducer1");
		reducer2 = system.actorOf(Props.create(Reducer.class),"reducer2");
		
		mapper1 = system.actorOf(Props.create(Mapper.class,reducer1,reducer2),"mapper1");
		mapper2 = system.actorOf(Props.create(Mapper.class,reducer1,reducer2),"mapper2");
		mapper3 = system.actorOf(Props.create(Mapper.class,reducer1,reducer2),"mapper3");
		
		
		
		
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

}
