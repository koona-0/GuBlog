package me.gunayeong.GuBlog.service;

import lombok.RequiredArgsConstructor;
import me.gunayeong.GuBlog.domain.RefreshToken;
import me.gunayeong.GuBlog.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    //전달받은 리프레시 토큰으로 객체를 검색해서 전달하는 메소드
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}