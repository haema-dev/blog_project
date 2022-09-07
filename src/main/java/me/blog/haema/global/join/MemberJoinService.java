package me.blog.haema.global.join;

import lombok.RequiredArgsConstructor;
import me.blog.haema.domain.MemberDto;
import me.blog.haema.domain.persist.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberJoinService {

    private final MemberJoinRepository memberJoinRepository;

    // member 데이터 저장
    public Member join(MemberDto memberDto) {
        return memberJoinRepository.save(memberDto.toEntity());
    }

    // member 데이터를 수정
    public void edit(MemberDto memberDto) {
        Member member = findMember(memberDto);
        // member Entity 에 추가한 member 정보 수정 메소드를 호출
        // 더티체킹으로 Entity 변경 감지 후, db 데이터가 수정된다.
        member.changeEmailAndNickname(memberDto.getEmail(), memberDto.getNickname());
    }

    // member 데이터를 삭제
    public void delete(MemberDto memberDto) {
        Member member = memberDto.toEntity();
        // 해당 아이디 존재 여부 확인
        boolean isExistMember = memberJoinRepository.existsById(member.getId());
        if (isExistMember) {
            memberJoinRepository.delete(member);
        } else {
            throw new IllegalArgumentException("해당 member id가 없습니다.");
        }
    }

    // member 를 조회
    public Member findMember(MemberDto memberDto) {
        // db 에서 member id 를 통해 entity 를 조회하도록 한다.
        return memberJoinRepository.findById(memberDto.getId())
               .orElseThrow(() -> new IllegalArgumentException("해당 member id가 없습니다."));
                // orElseThrow 처리 해주면 Optional 로 return 받지 않고 바로 member Entity return 받을 수 있음
    }

    // member 전체 조회
    public List<Member> findAllMembers() {
        return memberJoinRepository.findAll();
    }
}
