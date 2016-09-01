package generic.module.two.entity.decorator;

import java.lang.annotation.*;

/**
 * Created by mitziuro on 8/31/16.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Securizable {
}
