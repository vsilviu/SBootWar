package generic.module.two.aspectj.decorator;

import java.lang.annotation.*;

/**
 * Created by mitziuro on 8/30/16.
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface JVMCacheable {
}
