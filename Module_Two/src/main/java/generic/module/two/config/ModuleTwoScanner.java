package generic.module.two.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Silviu on 9/1/16.
 */

/**
 * @Configuration  : This class is a possible bean definition class
 * @ComponentScan  : It also scans for any @Component annotated classes in the
 *                   package defined above (set to be the root package of the module)
 */
@Configuration
@ComponentScan({"generic.module.two"})
public class ModuleTwoScanner {
}
