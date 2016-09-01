package generic.module.two.entity.decorator;


import generic.module.two.transformer.GenericTransformer;

import java.lang.annotation.*;

/**
 * Created by Catalin on 8/31/2016 .
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transformer {
    Class<? extends GenericTransformer> value();
}
