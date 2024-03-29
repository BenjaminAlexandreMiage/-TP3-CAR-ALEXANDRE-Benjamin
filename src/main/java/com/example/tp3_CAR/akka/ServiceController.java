package com.example.tp3_CAR.akka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/akka")
public class ServiceController {
	
	@Autowired
	private AkkaService service;

	//Page d'accueil
	@GetMapping("/home")
	public String home() {		
		return "/akka/home";
	}
	
	
	
	@PostMapping("/initialisation")
	public String initialisation() {
		service.createActeur();
		System.out.println("Création des acteurs réalisé");
		return "redirect:/akka/home";
	}
	
	
	@PostMapping("/analyser")
	public String analyser(@RequestParam String fichier) {
		service.transmissionLigne(fichier);
		return "redirect:/akka/home";
	}
	
	
}
