package generic.module.one.config;

import generic.module.two.config.ModuleTwoScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Silviu on 8/31/16.
 */

/**
 * - @Configuration  : This class is a possible bean definition class
 * - @ComponentScan  : It also scans for any @Component annotated classes in the
 *                   package defined above (set to be the root package of the module)
 * - @Import         : It imports the other modules' config files here with @Import
 *                   (a dependency to the other modules had to be defined in module one's pom)
 * - @PropertySource : Defines the location of the application properties file
 */
@Configuration
@Import(value = {ModuleTwoScanner.class})
@ComponentScan(basePackages = {"generic.module.one"})
@PropertySource("classpath:bootApplication.properties")
public class ModuleOneScanner {
}
