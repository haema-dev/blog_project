package me.blog.haema.global.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.blog.haema.domain.member.error.MemberNotFoundException;
import me.blog.haema.domain.member.repository.MemberRepository;
import me.blog.haema.global.error.exception.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // resource 절약하기 위해 static
    public static final MemberNotFoundException NOT_FOUND_EXCEPTION =
            new MemberNotFoundException(ErrorCode.USER_NOT_FOUND);

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return CustomUserDetails.of(memberRepository.findByEmail(username));
    }
}