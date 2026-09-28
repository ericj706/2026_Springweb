package example.day09.model.dto;

import java.time.LocalDateTime;

import example.day09.model.entity.ApiEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data @Builder 
public class ApiDto {
    private Integer idx;
    private String subject;
    private String name;
    private String regdate;
    private String content;

    public ApiEntity toEntity(){
        return ApiEntity.builder()
                .idx(this.idx)
                .subject(this.subject)
                .name(this.name)
                .regdate(LocalDateTime.now().toString())
                .content(this.content)
                .build();
    }

    public static ApiDto from(ApiEntity apiEntity){
        return ApiDto.builder()
                .idx(apiEntity.getIdx())
                .subject(apiEntity.getSubject())
                .name(apiEntity.getName())
                .regdate(apiEntity.getRegdate())
                .content(apiEntity.getContent())
                .build();
    }

}
