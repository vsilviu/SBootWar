package generic.module.one.init;

/**
 * Created by Silviu on 8/31/16.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @SpringBootApplication is a collection of three annotations
 *  - @Configuration : marks the class as a potential bean definition class
 *  - @EnableAutoConfiguration : don't know yet
 *  - @ComponentScan : loads the @Component annotated classes into
 *                     the spring annotated context as beans
 *                     if no value is defined, the scan is relative to
 *                     the package holding this class (scanning is for module one!)
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan({"generic.module.one"})
public class AppServletInitializer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppServletInitializer.class, args);
    }

}

