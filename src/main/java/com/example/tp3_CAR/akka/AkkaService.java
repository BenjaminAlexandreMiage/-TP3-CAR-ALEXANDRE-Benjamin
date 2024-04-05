package com.example.tp3_CAR.akka;


public interface AkkaService {
	
	void createActeur(int nbMapper,int nbReducer);
	
	void transmissionLigne(String fichier);

	int getNbOccurenceByMot(String mot);

}
