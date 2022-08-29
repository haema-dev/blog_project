package me.blog.haema;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class HelloTest {

    @Test
    @DisplayName("test 코드 연습")
    void hello() {
        //given
        String str = "hello";

        //when
        str += "안녕하세요 테스트 입니다";

        //then
        assertThat(str).isEqualTo("hello안녕하세요 테스트 입니다");
    }
}
