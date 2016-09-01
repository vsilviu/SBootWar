package generic.module.two.service;


import generic.module.two.entity.Community;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mitziuro on 8/24/16.
 */
@Transactional
public interface CommunityService extends GenericService<Community> {

}
