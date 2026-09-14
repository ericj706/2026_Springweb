package example.TotalPractice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.TotalPractice.model.entity.CategoryEntity;

@Repository 
public interface CategoryRepository extends JpaRepository<CategoryEntity , Integer > {
    
}