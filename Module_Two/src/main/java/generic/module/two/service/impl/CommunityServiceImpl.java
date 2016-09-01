package generic.module.two.service.impl;


import generic.module.two.entity.Community;
import generic.module.two.repository.CommunityRepository;
import generic.module.two.service.CommunityService;
import generic.module.two.transformer.CommunityTransformer;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * Created by mitziuro on 8/24/16.
 */
@Service
public class CommunityServiceImpl extends GenericServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    private CommunityTransformer communityTransformer = new CommunityTransformer();

    @Override
    protected PagingAndSortingRepository<Community, Long> getRepository() {
        return communityRepository;
    }

    @Override
    protected DetachedCriteria getCriteria() {
        return DetachedCriteria.forClass(Community.class);
    }
}
