package generic.module.one.controller;

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
public class CommunityController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello world!";
    }
}
