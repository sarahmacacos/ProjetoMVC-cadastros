package com.biblioteca.biblioteca.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biblioteca.biblioteca.model.Categoria;
import com.biblioteca.biblioteca.repository.CategoriaRepository;

// avisa o Spring que essa classe recebe os pedidos do navegador
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    // o repository é quem conversa com o banco de dados
    private final CategoriaRepository repository;

    // o Spring entrega o repository pronto aqui (injeção de dependência pelo construtor)
    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    // ENDPOINT DE LISTAR: quando alguém abre /categorias mostra a tabela
    @GetMapping
    public String listarCategorias(Model model) {
        // findAll busca todas as categorias da tabela
        model.addAttribute("categorias", repository.findAll());
        return "categorias/lista";
    }

    // quando alguém abre /categorias/nova mostra o formulário
    @GetMapping("/nova")
    public String novaCategoria(Model model) {
        // manda uma categoria vazia pro formulário preencher
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    // ENDPOINT DE CADASTRO: quando o formulário é enviado (POST /categorias)
    // @ModelAttribute pega o que foi digitado e coloca dentro da Categoria
    @PostMapping
    public String cadastrarCategoria(@ModelAttribute Categoria categoria) {
        // tira espaços do começo e do fim ("  Terror " vira "Terror")
        String nome = categoria.getNome() == null ? "" : categoria.getNome().trim();

        // se estiver vazio ou passar de 100 letras, volta pro formulário com aviso
        if (nome.isEmpty()) {
            return "redirect:/categorias/nova?erro=vazio";
        }
        if (nome.length() > 100) {
            return "redirect:/categorias/nova?erro=longo";
        }

        // se já existe uma categoria com esse nome, não cadastra de novo
        if (repository.existsByNomeIgnoreCase(nome)) {
            return "redirect:/categorias/nova?erro=duplicada";
        }

        // zera o id: quem manda no id é o banco. Sem isso alguém poderia
        // mandar um id que já existe e sobrescrever outra categoria
        categoria.setId(null);
        categoria.setNome(nome);

        try {
            // save grava a categoria no banco
            repository.save(categoria);
        } catch (DataIntegrityViolationException e) {
            // acontece se duas pessoas cadastrarem o mesmo nome ao mesmo tempo:
            // o banco barra (unique) e a gente avisa em vez de dar erro 500
            return "redirect:/categorias/nova?erro=duplicada";
        }

        // redirect: manda o navegador abrir /categorias de novo, assim o F5 não cadastra duas vezes
        return "redirect:/categorias";
    }

    // ENDPOINT DE EXCLUSÃO: quando clica em Excluir (POST /categorias/excluir/1, /2...)
    // é POST de propósito: um link GET poderia apagar sem querer (prefetch, ou outro site mandando o clique)
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        // deleteById apaga a categoria que tem esse id (se não existir, não faz nada)
        repository.deleteById(id);
        return "redirect:/categorias";
    }
}