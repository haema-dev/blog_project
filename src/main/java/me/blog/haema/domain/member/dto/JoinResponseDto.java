package me.blog.haema.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinResponseDto implements Serializable {
    private final long serialVersionUID = 1L;

    /**
     * Entity 나 중요한 데이터를 return 받으면 안 되므로 id 와 flag 만 return 받기 위한 Dto 생성
     * */
    @JsonProperty
    private Long id;
    @JsonProperty
    private boolean flag;
}
