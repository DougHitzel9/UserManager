package servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SpringBootApplication
public class ServletApplication extends SpringBootServletInitializer implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ServletApplication.class);

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(ServletApplication.class);
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      String uri = request.getRequestURI();                 // /client/list-roles
      String contextPath = request.getContextPath() + "/";  // /client
      String route = uri.substring(contextPath.length());   // 8

      if (route.equals("...")) {
        return false;
      }
      if (route.equals("login") ||
          route.equals("list-roles") ||
          route.equals("list-users")) {
        logger.info("*** ServletApplication.preHandle() - route: " + route);
        response.sendRedirect("/client/?route=" + route);
        return false;
      }
      return true;
  }

  public static void main(String[] args) {
    SpringApplication sa = new SpringApplication(ServletApplication.class);
    sa.run(args);
  }
}