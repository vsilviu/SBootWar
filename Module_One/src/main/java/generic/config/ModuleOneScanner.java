package generic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Silviu on 8/31/16.
 */

/**
 * This class is a possible bean definition class
 * It also scans for any @Component annotated classes in the
 * package defined above (set to be the root package of the module)
 */
@Configuration
@ComponentScan(basePackages = {"generic"})
public class ModuleOneScanner {
}
