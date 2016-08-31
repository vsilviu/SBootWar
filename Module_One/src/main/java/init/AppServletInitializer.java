package init;

/**
 * Created by Silviu on 8/31/16.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class AppServletInitializer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "dev");
        SpringApplication.run(AppServletInitializer.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppServletInitializer.class).web(true);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(8080);
        factory.setSessionTimeout(30, TimeUnit.MINUTES);
        factory.setContextPath("/context");
        return factory;
    }

}

