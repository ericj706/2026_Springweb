package example.TotalPractice.model.dto;

import example.TotalPractice.model.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Builder @Data 
public class ReviewDto {
    private Integer rno;
    private String reviewer;
    private String content;
    private Integer rating;
    private Integer bno;

    // toEntity
    public ReviewEntity toEntity(){
        return ReviewEntity.builder()
                .reviewer(this.reviewer)
                .content(this.content)
                .rating(this.rating)
                .build();
    }

    // from
    public static ReviewDto from(ReviewEntity entity){
        return ReviewDto.builder()
                .rno(entity.getRno())
                .reviewer(entity.getReviewer())
                .content(entity.getContent())
                .rating(entity.getRating()).build();
    }
    
}