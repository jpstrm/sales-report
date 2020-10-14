package br.com.sales.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
public class SalesConfiguration {

    private String delimiter;
    private String delimiterItem;
    private String delimiterItemField;
    private String allowedExtension;
    private String fileExtensionPattern;
    private String path;
    private String pathIn;
    private String pathOut;

}
