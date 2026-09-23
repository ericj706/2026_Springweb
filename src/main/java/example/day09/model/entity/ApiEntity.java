package example.day09.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table (name = "board")
@AllArgsConstructor @NoArgsConstructor 
@Builder @Data 
public class ApiEntity {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idx;
    private String subject;
    private String name;
    private String regdate;
    private String contents;

}
