package me.blog.haema.global.join;

import me.blog.haema.domain.MemberDto;
import me.blog.haema.domain.persist.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
public class MemberJoinServiceTest {

    @Autowired
    MemberJoinService memberJoinService;

    @Autowired
    MemberJoinRepository memberJoinRepository;

    @Test
    @DisplayName("유저 회원가입")
    @Commit
    @Transactional
    void join(){
        // given
        //MemberDto dto = new MemberDto(1L, "ab1234@gmail.com", "1234!@#", "해마");
        MemberDto dto = new MemberDto(0L, "ab12@gmail.com", "1234!@#", "해마");

        // when
        Member member = memberJoinService.join(dto);

        // then
        assertThat(member.getEmail()).isEqualTo(dto.toEntity().getEmail());
    }

    @Test
    @DisplayName("유저 정보 수정")
    @Commit
    @Transactional
    void edit(){
        // given
        MemberDto dto = new MemberDto(4L, "ab123@gmail.com", "1234!@#", "해마");
        Member member = memberJoinRepository.findById(4L)
                .orElseThrow(() -> new IllegalArgumentException("해당 member id가 없습니다."));

        // when
        member.changeEmailAndNickname(dto.getEmail(), dto.getNickname());

        // then
        assertThat(member.getEmail()).isEqualTo(dto.toEntity().getEmail());
    }

    @Test
    @DisplayName("유저 정보 삭제")
    @Commit
    @Transactional
    void delete(){
        // given
        Member member = memberJoinRepository.findById(4L)
                .orElseThrow(() -> new IllegalArgumentException("해당 member id가 없습니다."));

        // when
        memberJoinRepository.delete(member);

        // then
        assertFalse(memberJoinRepository.existsById(member.getId()));
        // https://junit.org/junit5/docs/current/user-guide/
    }

    @Test
    @DisplayName("유저 전체 조회")
    @Commit
    @Transactional
    void findAll(){
        // given
        List<Member> allMembers = memberJoinRepository.findAll();

        // when

        // then
        assertThat(allMembers.stream().count()).isEqualTo(1);
    }
}
