package br.com.petsalus.configurations;

import br.com.petsalus.properties.DataSourceProperties;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "br.com.petsalus.repositories")
public class DataSourceConfig {

    private final DataSourceProperties properties;

    @Bean
    public DataSource dataSource() {
    	return DataSourceBuilder.create()
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .driverClassName(properties.getDriver())
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory dataSource) {
        return new JpaTransactionManager(dataSource);
    }
}
