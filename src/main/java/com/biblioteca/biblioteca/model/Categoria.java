package com.biblioteca.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity avisa o JPA que essa classe vira uma tabela no banco (tabela categoria)
@Entity
public class Categoria {

    // @Id é a chave primária, o banco cria o número sozinho (1, 2, 3...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable = false: não aceita vazio no banco
    // unique = true: o banco não deixa ter duas categorias com o mesmo nome
    // length = 100: tamanho máximo do nome
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}