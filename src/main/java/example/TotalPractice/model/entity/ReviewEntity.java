package example.TotalPractice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table (name = "review")
@NoArgsConstructor @AllArgsConstructor @Builder @Data 
public class ReviewEntity {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer rno;
    private String reviewer;
    private String content;
    private Integer rating;

    @ManyToOne 
    @JoinColumn(name = "bno")
    private ProductEntity productEntity;
    
}
