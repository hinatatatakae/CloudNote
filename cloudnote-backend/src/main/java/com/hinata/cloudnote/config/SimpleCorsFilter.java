package com.hinata.cloudnote.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {

    // すべてのオリジンを許可
    response.setHeader("Access-Control-Allow-Origin", "*");
    // プリフライトで必要な設定
    response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "*");
    // クレデンシャルなしなら以下はfalseでもOK
    response.setHeader("Access-Control-Allow-Credentials", "false");

    // OPTIONS はここで終わらせる
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
      return;
    }

    // その他は通常のフィルタチェインへ
    filterChain.doFilter(request, response);
  }
}
