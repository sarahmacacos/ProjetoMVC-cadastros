package com.biblioteca.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biblioteca.biblioteca.model.Autor;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @GetMapping
    public String inicio() {
        return "autores/inicio";
    }

    @GetMapping("/novo")
    public String novoAutor(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/form";
    }

    @PostMapping
    public String cadastrarAutor(@ModelAttribute Autor autor, Model model) {
        model.addAttribute("autor", autor);
        return "autores/confirmacao";
    }
}