package me.gunayeong.GuBlog.service;

import lombok.RequiredArgsConstructor;
import me.gunayeong.GuBlog.config.jwt.TokenProvider;
import me.gunayeong.GuBlog.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    //전달받은 리프레시 토큰으로 토큰유효성검사 진행, 유효한 토큰일때 리프레시 토큰으로 사용자 ID 찾는 메소드
    public String createNewAccessToken(String refreshToken) {
        //토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}