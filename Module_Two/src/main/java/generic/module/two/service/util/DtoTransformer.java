package generic.module.two.service.util;


import generic.module.two.dto.GenericDTO;
import generic.module.two.entity.decorator.Identificable;
import generic.module.two.entity.decorator.Transformer;
import generic.module.two.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

/**
 * Created by mitziuro on 8/30/16.
 */

@Component
public class DtoTransformer {

    @SuppressWarnings("unchecked")
    public GenericDTO transform(Identificable identificable, Class<? extends GenericDTO> DTOClass) throws  Exception  {
        Class<? extends GenericTransformer> transformerClass = DTOClass.getAnnotation(Transformer.class).value();
        GenericTransformer transformer = transformerClass.newInstance();

        System.out.println("********" + DTOClass.getCanonicalName());
        System.out.println("********" + identificable);
        System.out.println("*******" + transformerClass.getCanonicalName());

        return (GenericDTO) transformer.getDetails(identificable, DTOClass);
    }
}
