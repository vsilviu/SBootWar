package generic.module.two.repository;


import generic.module.two.entity.Community;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mitziuro on 8/24/16.
 */
@Repository
public interface CommunityRepository extends PagingAndSortingRepository<Community, Long> {

}
