package springsamurais.toyswapbackend;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private final Properties properties = new Properties();
    @Getter
    private final String configFilePath = "config.properties";

    public Config() {
        loadProperties();
    }

    private void loadProperties() {
        try (FileInputStream configFileReader = new FileInputStream(configFilePath)) {
            properties.load(configFileReader);
        } catch (IOException e) {
            System.err.println("Failed to load configuration file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getProperty("db.driver-class-name"));
        dataSource.setUrl(getProperty("db.url"));
        dataSource.setUsername(getProperty("db.username"));
        dataSource.setPassword(getProperty("db.password"));
        return dataSource;

    }
}