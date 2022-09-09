package me.blog.haema.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.blog.haema.domain.member.dto.MemberRequestDto;
import me.blog.haema.domain.member.dto.MemberResponseDto;
import me.blog.haema.domain.member.error.DuplicateMemberEmailException;
import me.blog.haema.domain.member.error.MemberNotFoundException;
import me.blog.haema.domain.member.repository.persist.MemberRepository;
import me.blog.haema.domain.member.dto.JoinResponseDto;
import me.blog.haema.domain.member.repository.persist.Member;
import me.blog.haema.global.error.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // member 데이터 저장
    public JoinResponseDto join(Member member) {
        return new JoinResponseDto(memberRepository.save(member).getId(), true);
    }

    // member 데이터를 수정
    public void edit(Member updateMember, String email) {
        Member member = memberRepository.findByEmail(email);
        // 더티체킹으로 Entity 변경 감지 후, db 데이터가 수정된다.
        member.change(updateMember.getPassword(), updateMember.getNickname());
    }

    // member 데이터를 삭제
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
        return new MemberResponseDto(member.getNickname(),member.getEmail());
    }

    // member 전체 조회
    @Transactional(readOnly = true)
    public Page<MemberResponseDto> findAllMembers(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberResponseDto::of);
    }
}
