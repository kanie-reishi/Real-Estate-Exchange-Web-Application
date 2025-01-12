package devcamp.realestateexchange.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    // Ghi log lỗi
    logger.error("Unauthorized error: {}", authException.getMessage());

    // Kiểm tra đường dẫn truy cập để xác định loại trang login
    String path = request.getServletPath();
    String redirectUrl;

    if (path.startsWith("/admin")) {
      // Tài nguyên liên quan đến admin
      redirectUrl = request.getContextPath() + "/login/admin?error=true";
    } else {
      // Tài nguyên liên quan đến người dùng thông thường
      redirectUrl = request.getContextPath() + "/login/user?error=true";
    }

    // Điều hướng đến trang login tương ứng
    response.sendRedirect(redirectUrl);
  }

}
