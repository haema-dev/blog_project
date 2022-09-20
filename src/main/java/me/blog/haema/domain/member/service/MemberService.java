package me.blog.haema.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.blog.haema.domain.member.dto.MemberResponseDto;
import me.blog.haema.domain.member.entity.persist.Member;
import me.blog.haema.domain.member.error.DuplicateMemberEmailException;
import me.blog.haema.domain.member.error.MemberNotFoundException;
import me.blog.haema.domain.member.repository.MemberRepository;
import me.blog.haema.domain.member.dto.JoinResponseDto;
import me.blog.haema.global.error.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public JoinResponseDto join(final Member member) {

        existEmail(member.getEmail());

        return new JoinResponseDto(memberRepository.save(member).getId(), true);
    }

    @Transactional
    public void edit(final Member updateMember, final String email) {

        Member member = memberRepository.findByEmail(email);
        member.change(updateMember.getPassword(), updateMember.getNickname());
    }

    @Transactional
    public void delete(final Long memberId) {

        memberRepository.findById(memberId).orElseThrow(
                        () -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND))
                        .delete();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMember(final Long id) {

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        return new MemberResponseDto(member.getNickname(),member.getEmail());
    }

    @Transactional(readOnly = true)
    public Page<MemberResponseDto> getMembers(final Pageable pageable) {

        Page<Member> members = memberRepository.findAll(pageable);

        return members.map(MemberResponseDto::of); // :: 참조 매핑 새로 해준다는 뜻
    }

    private void existEmail(final String email) {

        boolean isExistEmail = memberRepository.existsByEmail(email);
        if (isExistEmail) {
            throw new DuplicateMemberEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
    }
}