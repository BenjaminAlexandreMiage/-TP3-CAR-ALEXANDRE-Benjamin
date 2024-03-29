package com.example.tp3_CAR.akka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
}
