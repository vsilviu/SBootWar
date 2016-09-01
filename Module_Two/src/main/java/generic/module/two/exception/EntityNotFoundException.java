package generic.module.two.exception;

/**
 * Created by mitziuro on 8/17/16.
 */
public class EntityNotFoundException extends KommunityException {

    private final Long id;
    private final Class clazz;

    public EntityNotFoundException(Long id, Class clazz) {
        super(KommunityExceptionCode.NOT_FOUND);
        this.id = id;
        this.clazz = clazz;
    }

    public Long getId() {
        return id;
    }

    public Class getClazz() {
        return clazz;
    }

}
