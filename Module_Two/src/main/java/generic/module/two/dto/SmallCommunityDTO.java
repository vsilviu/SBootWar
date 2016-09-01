package generic.module.two.dto;


import generic.module.two.entity.decorator.Transformer;
import generic.module.two.transformer.CommunityTransformer;

/**
 * Created by Catalin on 8/29/2016 .
 */

@Transformer(CommunityTransformer.class)
public class SmallCommunityDTO extends GenericDTO {

    private Long id;
    private String name;

    public SmallCommunityDTO() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
