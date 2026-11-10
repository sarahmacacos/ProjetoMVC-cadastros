package com.biblioteca.biblioteca.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.model.Livro;

// o Spring cria sozinho o save, findAll, deleteById e outros
public interface LivroRepository extends JpaRepository<Livro, Long> {
}