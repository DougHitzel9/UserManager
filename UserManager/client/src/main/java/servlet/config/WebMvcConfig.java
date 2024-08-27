package servlet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import servlet.ServletApplication;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

	private final String[] PATH_PATTERNS = { "/**" };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		logger.info("*** addInterceptors()");
        registry.addInterceptor(new ServletApplication())
                .addPathPatterns(PATH_PATTERNS);
    }
}