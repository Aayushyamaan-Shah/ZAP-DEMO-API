package in.teamelementals.zap.zap_attendance_system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariDataSource;

public class DatasourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.postgres")
    public HikariDataSource postgresHikariDataSource(){
        return DataSourceBuilder
        .create()
        .type(HikariDataSource.class)
        .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource){
        return new JdbcTemplate(hikariDataSource);
    }

}
