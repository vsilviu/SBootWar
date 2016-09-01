package generic.module.two.entity.decorator;


import generic.module.two.entity.User;

/**
 * Created by mitziuro on 8/16/16.
 */
public interface Ownable {

    User getOwner();

    void setOwner(User owner);

}
