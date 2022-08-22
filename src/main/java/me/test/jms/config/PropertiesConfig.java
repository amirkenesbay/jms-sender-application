package me.test.jms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class PropertiesConfig {
    @Bean(name = "jms_props")
    public Properties properties() throws IOException {
        Properties props = new Properties();

        props.load(new FileReader("app.properties"));

        return props;
    }
}
