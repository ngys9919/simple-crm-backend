package sg.edu.ntu.simple_crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SimpleCrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCrmApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://ngys9919.github.io/simple-crm-frontend")
						.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE");
			}
		};
	}
}
