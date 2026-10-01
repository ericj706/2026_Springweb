package example.day13;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberDto {
    private String data;

    private List<String> list;

    public static MemberDto from(List<String> list){
        return MemberDto.builder()
                        .list(list)
                        .build();
    }
}