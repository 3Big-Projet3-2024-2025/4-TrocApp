package helha.trocappbackend.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Applies CORS to all requests (all endpoints)
                .allowedOrigins("http://localhost:4200")  // Allow requests from Angular (localhost:4200)
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")  // Authorised HTTP methods
                .allowedHeaders("*")
                .exposedHeaders("Authorization")// Allows all headers
                .allowCredentials(true);  // Allows you to send authentication information (cookies, tokens,...)
    }


    // Adds MappingJackson2HttpMessageConverter to convert JSON to/from Java objects.
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    
    // Configures an ObjectMapper to ignore unknown properties during deserialization.
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}