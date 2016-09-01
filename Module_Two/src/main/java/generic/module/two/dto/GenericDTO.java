package generic.module.two.dto;

import generic.module.two.entity.decorator.Identificable;
import generic.module.two.entity.decorator.Transformer;
import generic.module.two.transformer.GenericTransformer;

@Transformer(GenericTransformer.class)
public abstract class GenericDTO implements Identificable {

    @Override
    public Long getId() {
        return 1L;
    }
}