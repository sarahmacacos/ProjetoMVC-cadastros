package com.biblioteca.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biblioteca.biblioteca.model.Livro;

import java.util.ArrayList;
import java.util.List;

// avisa o Spring que essa classe recebe os pedidos do navegador
@Controller
@RequestMapping("/livros")
public class LivroController {

    // a lista fica guardada na memória enquanto o programa estiver ligado
    // se reiniciar o programa, os livros somem
    private final List<Livro> livros = new ArrayList<>();

    // quando alguém abre /livros mostra a tabela com todos os livros
    @GetMapping
    public String listar(Model model) {
        // manda uma cópia da lista pra página (assim ninguém mexe na lista enquanto a página é montada)
        synchronized (livros) {
            model.addAttribute("livros", new ArrayList<>(livros));
        }
        return "livros/lista";
    }

    // quando alguém abre o exemplo
    @GetMapping("/exemplo")
    public String exemplo(Model model) {
        // cria um livro e preenche com os dados na mão
        Livro livro = new Livro();
        livro.setTitulo("Harry Potter");
        livro.setAutor("J.K. Rowling");
        livro.setIsbn("001");

        // manda o livro pra página usar com o nome "livro"
        model.addAttribute("livro", livro);
        return "livros/livro";
    }

    // quando alguém abre livro novo
    @GetMapping("/novo")
    public String novoLivro(Model model) {

        // manda um livro vazio pro formulário preencher
        model.addAttribute("livro", new Livro());
        return "livros/form";
    }

    // quando o formulário é enviado (POST /livros)
    // @ModelAttribute pega o que foi digitado no formulário e coloca dentro do Livro
    @PostMapping
    public String cadastrarLivro(@ModelAttribute Livro livro) {
        // só guarda se o título foi preenchido
        if (livro.getTitulo() != null && !livro.getTitulo().isBlank()) {
            // coloca o livro na lista
            synchronized (livros) {
                livros.add(livro);
            }
        }
        // redirect: manda o navegador abrir /livros de novo, assim o F5 não cadastra o livro duas vezes
        return "redirect:/livros";
    }

    // quando clica no botão Excluir (POST /livros/excluir/0, /livros/excluir/1...)
    // @PathVariable pega o número que veio na URL e coloca em indice
    @PostMapping("/excluir/{indice}")
    public String excluir(@PathVariable int indice) {
        synchronized (livros) {
            // só tira da lista se essa posição existe (senão dá erro)
            if (indice >= 0 && indice < livros.size()) {
                livros.remove(indice);
            }
        }
        return "redirect:/livros";
    }
}