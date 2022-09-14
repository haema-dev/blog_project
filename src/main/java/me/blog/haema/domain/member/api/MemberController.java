package me.blog.haema.domain.member.api;

import lombok.RequiredArgsConstructor;
import me.blog.haema.domain.member.dto.*;
import me.blog.haema.domain.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<JoinResponseDto> join(@Valid @RequestBody JoinRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.join(dto.toEntity()));
    }

    // read
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> findMember(@PathVariable Long memberId) {

        return ResponseEntity.ok().body(memberService.findMember(memberId));
    }

    // update
    @PatchMapping
    public ResponseEntity<Void> edit(@Valid @RequestBody MemberUpdateRequestDto requestDto) {

        memberService.edit(requestDto.toEntity(), requestDto.toEntity().getEmail());

        return ResponseEntity.ok().build();
    }

    // delete
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> delete(@PathVariable Long memberId) {

        memberService.delete(memberId);

        return ResponseEntity.noContent().build();
    }

    // findAll
    @GetMapping
    public ResponseEntity<Page<MemberResponseDto>> getMembers(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok().body(memberService.getMembers(pageable));
    }
}