package com.mercelab.seguimiento._setting._database;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "seguimientoEntityManagerFactory",
        transactionManagerRef = "seguimientoTransactionManager",
        basePackages = {"com.mercelab.seguimiento.repositories._r_seguimiento"}
)
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.datasource.seguimiento")
public class ConfigurationMariaDbSeguimiento {

    @Autowired
    private Environment env;

    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.seguimiento")
    @Bean(name = "seguimientoDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.seguimiento.url"))
                .username(env.getProperty("spring.datasource.seguimiento.username"))
                .password(env.getProperty("spring.datasource.seguimiento.password"))
                .build();
    }
    @Primary
    @Bean(name = "seguimientoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("seguimientoDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.mercelab.seguimiento.models._m_seguimiento")
                .persistenceUnit("seguimiento")
                .build();
    }

    @Primary
    @Bean(name = "seguimientoTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("seguimientoEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
