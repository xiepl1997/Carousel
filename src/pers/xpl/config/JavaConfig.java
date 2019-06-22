package pers.xpl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xpl.util.FunctionsOfMysql;

@Configuration
public class JavaConfig {
    @Bean
    public FunctionsOfMysql functionsOfMysql(){
        return new FunctionsOfMysql();
    }
}
