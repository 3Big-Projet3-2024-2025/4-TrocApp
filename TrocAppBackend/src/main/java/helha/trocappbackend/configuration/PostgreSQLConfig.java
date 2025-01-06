package helha.trocappbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "helha.trocappbackend.repositories"
)
public class PostgreSQLConfig {
}