package example.TotalPractice.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.TotalPractice.model.entity.ReviewEntity;

@Repository 
public interface  ReviewRepoistory extends JpaRepository<ReviewEntity, Integer> {
    List<ReviewEntity> findByProductEntity_Bno(int bno);
}