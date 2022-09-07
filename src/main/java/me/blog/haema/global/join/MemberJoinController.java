package me.blog.haema.global.join;

import lombok.RequiredArgsConstructor;
import me.blog.haema.domain.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberJoinController {

    private final MemberJoinService memberJoinService;

    @PostMapping("/join")
    public ResponseEntity join(@Valid @RequestBody MemberDto memberDto) {
        // @Valid : 유효성 검사. null 을 체크할 수도 있고, 정의한 유효성을 체크할 수도 있다.
        // @RequestBody : HttpMessageConverter 를 통해 Json 형태로 파싱
        memberJoinService.join(memberDto);
        return new ResponseEntity(HttpStatus.CREATED); //201
    }

    @PostMapping("/edit")
    public ResponseEntity edit(@Valid @RequestBody MemberDto memberDto) {
        memberJoinService.edit(memberDto);
        return new ResponseEntity(HttpStatus.CREATED); //201
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@Valid @RequestBody MemberDto memberDto) {
        memberJoinService.delete(memberDto);
        return new ResponseEntity(HttpStatus.OK); //200
    }
}
