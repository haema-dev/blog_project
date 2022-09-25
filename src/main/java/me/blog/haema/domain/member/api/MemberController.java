package me.blog.haema.domain.member.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.blog.haema.domain.member.dto.*;
import me.blog.haema.domain.member.service.MemberService;
import me.blog.haema.global.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    // create
    @PostMapping("/public/members")
    public ResponseEntity<JoinResponseDto> join(@Valid @RequestBody JoinRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.join(dto.toEntity()));
    }

    // read
    @GetMapping("/members/details")
    public ResponseEntity<MemberResponseDto> findMember() {

        return ResponseEntity.ok().body(memberService.findMember(getPrincipal().getId()));
    }

    // update
    @PatchMapping("/members")
    public ResponseEntity<Void> edit(@Valid @RequestBody MemberUpdateRequestDto requestDto) {

        memberService.edit(requestDto.toEntity(), this.getPrincipal().getId());

        return ResponseEntity.ok().build();
    }

    // delete
    @DeleteMapping("/members")
    public ResponseEntity<Void> delete() {

        memberService.delete(getPrincipal().getId());

        return ResponseEntity.noContent().build();
    }

    // findAll
    @GetMapping("/members")
    public ResponseEntity<Page<MemberResponseDto>> getMembers(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok().body(memberService.getMembers(pageable));
    }

    private CustomUserDetails getPrincipal() {
        log.debug("principal : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}