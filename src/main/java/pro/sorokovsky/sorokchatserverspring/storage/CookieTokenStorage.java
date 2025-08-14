package pro.sorokovsky.sorokchatserverspring.storage;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

public class CookieTokenStorage implements TokenStorage {
    private static final String COOKIE_NAME = "__Host-4V5CcKvkhqt2pPYqDVhjpFFyswj5dqpG";

    @Override
    public Optional<String> get(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();
        return Stream.of(request.getCookies())
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .map(Cookie::getValue)
                .findFirst();
    }

    @Override
    public void set(String token, HttpServletResponse response, Instant expirationTime) {
        final var maxAge = (int) ChronoUnit.SECONDS.between(Instant.now(), expirationTime);
        set(response, token, maxAge);
    }

    @Override
    public void clear(HttpServletResponse response) {
        set(response, "", 0);
    }

    private void set(HttpServletResponse response, String token, int maxAge) {
        final var cookie = new Cookie(COOKIE_NAME, token);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setDomain(null);
        response.addCookie(cookie);
    }
}
