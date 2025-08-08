package pro.sorokovsky.sorokchatserverspring.storage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.util.Optional;

public interface TokenStorage {
    Optional<String> get(HttpServletRequest request);

    void set(String token, HttpServletResponse response, Instant expirationTime);

    void clear(HttpServletResponse response);
}
