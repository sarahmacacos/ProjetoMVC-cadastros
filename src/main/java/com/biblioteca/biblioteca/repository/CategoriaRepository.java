package com.biblioteca.biblioteca.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.biblioteca.model.Categoria;

// o Spring cria sozinho o save, findAll, deleteById e outros
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // o Spring entende o nome do método e monta a consulta sozinho:
    // existe alguma categoria com esse nome, sem diferenciar maiúscula de minúscula?
    boolean existsByNomeIgnoreCase(String nome);
}