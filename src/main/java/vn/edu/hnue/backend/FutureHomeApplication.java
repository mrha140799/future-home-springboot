package vn.edu.hnue.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableScheduling

public class FutureHomeApplication implements WebMvcConfigurer {
    @Value("${file.uploadDir}")
    private String uploadDir;


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(15242880);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/fileFolders/**").addResourceLocations("file:" + uploadDir);
    }
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(FutureHomeApplication.class, args);
    }

}
