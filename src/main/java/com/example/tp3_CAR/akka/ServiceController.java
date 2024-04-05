package com.example.tp3_CAR.akka;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String home(){	
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
	
	@PostMapping("/afficherOccurence")
	public String afficherOccurence(@RequestParam String mot,Model model) {
		int nb_occurence = service.getNbOccurenceByMot(mot);
		System.out.println(nb_occurence);
		
		model.addAttribute("mot",mot);
		model.addAttribute("nbOccurence",nb_occurence);
		
		return "redirect:/akka/home";
	}
	
	
	
}
