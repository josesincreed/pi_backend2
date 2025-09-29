package com.example.pib2.repository;

import com.example.pib2.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name); // ejemplo: verificar si ya existe una categoría
}