package generic.module.one.controller;

import generic.module.two.entity.Community;
import generic.module.two.service.CommunityService;
import generic.module.two.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Silviu on 8/31/16.
 */

/**
 * The @RestController is a collection of two annotations
 *  - @Controller   - standard Spring MVC controller
 *  - @ResponseBody - the contents of the response body
 */
@RestController
@RequestMapping("/community")
public class CommunityController extends GenericController<Community> {

    @Autowired
    private CommunityService communityService;

    @Override
    protected GenericService<Community> getService() {
        return communityService;
    }
}
