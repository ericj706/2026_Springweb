package example.TotalPractice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.TotalPractice.model.entity.ProductEntity;

@Repository 
public interface  ProductRepository extends JpaRepository<ProductEntity,Integer> {

}
