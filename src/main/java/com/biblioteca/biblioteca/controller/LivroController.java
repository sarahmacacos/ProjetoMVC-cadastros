package com.biblioteca.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biblioteca.biblioteca.model.Livro;

// avisa o Spring que essa classe recebe os pedidos do navegador
@Controller
@RequestMapping("/livros")
public class LivroController {

    // quando alguém abre livros
    @GetMapping
    public String inicio() {
        return "livros/inicio";
    }

    // quando alguém abre o exemplo
    @GetMapping("/exemplo")
    public String exemplo(Model model) {
        model.addAttribute("livro", new Livro());
        // cria um livro e preenche com os dados na mão
        Livro livro = new Livro();
        livro.setTitulo("Harry Potter");
        livro.setAutor("J.K. Rowling");
        livro.setIsbn("001");

        // manda o livro pra página usar com o nome "livro"
        model.addAttribute("livro", livro);
        return "livros/livro";
    }

    // quando alguém abre livro novos
    @GetMapping("/novo")
    public String novoLivro(Model model) {

        // manda um livro vazio pro formulário preencher
        model.addAttribute("livro", new Livro());
        return "livros/form";
    }

    @PostMapping
    // @ModelAttribute pega o que foi digitado no formulário e coloca dentro do Livro
    public String cadastrarLivro(@ModelAttribute Livro livro, Model model) {
        // manda o livro preenchido pra página de confirmação mostrar
        model.addAttribute("livro", livro);
        return "livros/confirmacao";
    }
}