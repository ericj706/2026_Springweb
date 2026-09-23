package example.day09.model.dto;

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
    private String contents;

    public ApiEntity toEntity(ApiDto apiDto){
        return ApiEntity.builder()
                .idx(this.idx)
                .subject(this.subject)
                .name(this.name)
                .regdate(this.regdate)
                .contents(this.contents)
                .build();
    }

    public static ApiDto from(ApiEntity apiEntity){
        return ApiDto.builder()
                .subject(apiEntity.getSubject())
                .name(apiEntity.getName())
                .regdate(apiEntity.getRegdate())
                .contents(apiEntity.getContents())
                .build();
    }

}
