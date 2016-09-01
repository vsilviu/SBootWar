package generic.module.two.entity.decorator;

/**
 * Created by User Nou on 25.08.2016.
 */
public interface SoftDelete {
    void setDeleted(Boolean deleted);
    Boolean isDeleted();
}
