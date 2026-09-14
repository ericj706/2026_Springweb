package example.TotalPractice.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table (name = "category")
@NoArgsConstructor @AllArgsConstructor @Builder @Data 
public class CategoryEntity {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer cno;
    private String name;

    @OneToMany (mappedBy = "categoryEntity" , cascade = CascadeType.ALL)
    @ToString .Exclude
    @Builder .Default
    private List<ProductEntity> productEntities = new ArrayList<>();
    
}