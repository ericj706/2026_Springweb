package example.TotalPractice.model.dto;

import java.util.ArrayList;
import java.util.List;

import example.TotalPractice.model.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Builder @Data 
public class CategoryDto {
    private Integer cno;
    private String name;

    @Builder.Default
    private List<ProductDto> productDtos = new ArrayList<>();

    // toEntity
    public CategoryEntity toEntity(){
        return CategoryEntity.builder()
                .name(this.name).build();
    }

    // from
    public static CategoryDto from( CategoryEntity categoryEntity){
        return CategoryDto.builder()
                .cno(categoryEntity.getCno())
                .name(categoryEntity.getName())
                .build();
    }
    
}