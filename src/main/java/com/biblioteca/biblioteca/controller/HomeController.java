package com.biblioteca.biblioteca.controller;

import java.time.Year;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// avisa o Spring que essa classe recebe os pedidos do navegador
@Controller
public class HomeController {

    // quando alguém abre o site (localhost:8080/), cai nesse método
    @GetMapping("/")
    public String index(Model model) {

        // o model leva os dados do java pra página html
        model.addAttribute("nomeSistema", "Biblioteca Central");
        model.addAttribute("mensagemBoasVindas", "Gestão de acervo e empréstimos!");
        model.addAttribute("anoAtual", Year.now().getValue()); // pega o ano de hoje
        model.addAttribute("statusOperacional", "Sistema operando normalmente");

        // diz qual página abrir
        return "index";
    }

    // quando alguém abre o sobre
    @GetMapping("/sobre")
    public String sobre(Model model) {
        model.addAttribute("nomeSistema", "Biblioteca Central");
        return "sobre";
    }
}