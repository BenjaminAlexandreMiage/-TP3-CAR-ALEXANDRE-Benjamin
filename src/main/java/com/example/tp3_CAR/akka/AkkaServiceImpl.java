package com.example.tp3_CAR.akka;

import org.springframework.stereotype.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

@Service
public class AkkaServiceImpl implements AkkaService{

	@Override
	public void createActeur() {
		
		ActorSystem system = ActorSystem.create("MySystem");
		
		ActorRef mapper1,mapper2,mapper3,reducer1,reducer2;
		
		mapper1 = system.actorOf(Props.create(Mapper.class),"mapper1");
		mapper2 = system.actorOf(Props.create(Mapper.class),"mapper2");
		mapper3 = system.actorOf(Props.create(Mapper.class),"mapper3");
		
		reducer1 = system.actorOf(Props.create(Mapper.class),"reducer1");
		reducer2 = system.actorOf(Props.create(Mapper.class),"reducer2");
		
		
	}

}
