package com.example.tp3_CAR.akka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
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
	
	private List tabReducer = new ArrayList();
	private List tabMapper = new ArrayList();
	
	private Inbox inbox;

	
	@Override
	public void createActeur(int nbMapper, int nbReducer) {
		
		system = ActorSystem.create("MySystem");
		
		inbox = Inbox.create(system);
		
		for(int i=0;i<nbReducer;i++) {
			tabReducer.add(system.actorOf(Props.create(Reducer.class),"reducer"+i));
		}
		
		for(int i=0;i<nbMapper;i++) {
			tabMapper.add(system.actorOf(Props.create(Mapper.class),"mapper"+i));
		}
		
		this.tabMapper.forEach(m -> this.tabReducer.forEach(r -> inbox.send((ActorRef) m,new requestReducer(r))));
		
	}

	@Override
	public void transmissionLigne(String fichier) {
		
		
		try {
			
			FileReader fileReader = new FileReader(fichier);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line = reader.readLine();
			int nmapper = 0;
			while (line != null) {
				System.out.println(nmapper);
				if(nmapper<this.tabMapper.size()-1) {
	
					((ActorRef) tabMapper.get(nmapper)).tell(new GreetingMessage(line),ActorRef.noSender());
					
					nmapper += 1;
				}
				else{
					
					((ActorRef) tabMapper.get(nmapper)).tell(new GreetingMessage(line),ActorRef.noSender());
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
			inbox.send((ActorRef) tabReducer.get(0), new requestMessage(mot));
			
		}
		
		else {
			inbox.send((ActorRef) tabReducer.get(1), new requestMessage(mot));
			
		}
		
		Object reply = null;
		
		try {
			
			reply = inbox.receive(FiniteDuration.create(5,TimeUnit.SECONDS));
			
		} catch (TimeoutException e) {
			
		}
		
		if( reply instanceof reponseMessage rm ){
			
			System.out.println("Réponse : "+rm.reponse()); 
			
			reponse = rm.reponse();
		
		}
		
		return reponse;
		
	}

}
