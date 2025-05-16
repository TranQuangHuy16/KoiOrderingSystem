package com.project.KoiOrderingSystem.config;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.exception.AuthException;
import com.project.KoiOrderingSystem.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/login",
            "/api/register",
            "/api/forgot-password",
            "/api/auth/google",
            "/api/auth/facebook",
            "/api/auth/google-firebase"
    );

    public boolean checkIsPublic(String uri) {
        // uri : /api/register
        // nếu gặp những api cho phép ở trên cho truy cập luôn => true
        AntPathMatcher pathMatch = new AntPathMatcher();
        // check token => false
        return AUTH_PERMISSION.stream().anyMatch(pattern -> pathMatch.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // check xem api mà người dùng yêu cầu có phải là 1 public api không
        boolean isPublicAPI = checkIsPublic(request.getRequestURI());

        if (isPublicAPI) {
            filterChain.doFilter(request, response);
        } else {
            String token = getToken(request);
            if (token == null) {
                //không cho phép truy cập
                resolver.resolveException(request, response, null, new AuthException("Empty token"));
                return;
            }
            // => có token
            // check xem token có đúng hay không => lấy thông tin account từ token
            Account account;
            try {
                account = tokenService.getAccountByToken(token);
            } catch (ExpiredJwtException e) {
                // response token hết hạn
                resolver.resolveException(request, response, null, new AuthException("Expired token"));
                return;
            } catch (MalformedJwtException malformedJwtException) {
                // response token sai
                resolver.resolveException(request, response, null, new AuthException("Invalid token"));
                return;
            }

            // => token chuẩn
            // cho phép truy cập
            // => lưu lại thông tin account
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (account, token, account.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // token ok cho vào
            filterChain.doFilter(request, response);
        }
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.substring(7);
    }
}
