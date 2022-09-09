package me.blog.haema.domain.member.api;

import lombok.RequiredArgsConstructor;
import me.blog.haema.domain.member.dto.*;
import me.blog.haema.domain.member.repository.persist.Member;
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

    // ResponseEntity<?> 제네릭 꼭 사용해줄 것. 데이터 일관성 유지.
    @PostMapping
    public ResponseEntity<JoinResponseDto> join(@Valid @RequestBody JoinRequestDto dto) {
        // @Valid : 유효성 검사. null 을 체크할 수도 있고, 정의한 유효성을 체크할 수도 있다.
        // @RequestBody : HttpMessageConverter 를 통해 Json 형태로 파싱
        return ResponseEntity.status(HttpStatus.CREATED) //201
                .body(memberService.join(dto.toEntity()));
    }

    // read
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findMember(@PathVariable Long id) {
        return ResponseEntity.ok().body(memberService.findMember(id)); // 200
    }

    // update
    @PatchMapping
    public ResponseEntity<Void> edit(@Valid @RequestBody MemberUpdateRequestDto dto) {
        memberService.edit(dto.toEntity(), dto.toEntity().getEmail());
        return ResponseEntity.ok().build(); //200
    }

    // delete
    @DeleteMapping
    public ResponseEntity<Void> delete(@Valid @RequestBody MemberRequestDto dto) {
        memberService.delete(dto.toEntity());
        return ResponseEntity.accepted().build(); //202
    }

    // findAll
    @GetMapping("/findAll")
    public ResponseEntity<Page<MemberResponseDto>> findAll(
            @PageableDefault(sort = "id", size = 3, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(memberService.findAllMembers(pageable));
    }
}