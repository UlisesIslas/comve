package mx.edu.utez.comverecu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import mx.edu.utez.comverecu.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long>, PagingAndSortingRepository<Category, Long> {
    public Category findByName(String name);
}
