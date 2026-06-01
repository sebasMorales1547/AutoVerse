package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AutoverseAplicacion implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AutoverseAplicacion.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = "file:" + System.getProperty("user.dir") + "/uploads/";
        registry.addResourceHandler("/uploads/**")
        .addResourceLocations(uploadPath);
}
}