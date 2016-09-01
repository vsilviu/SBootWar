package generic.module.one.controller;


import generic.module.two.dto.GenericDTO;
import generic.module.two.service.GenericService;
import generic.module.two.service.util.ClassUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

/**
 * Created by mitziuro on 8/16/16.
 */
public abstract class GenericController<T> {

    protected static final String _X_DTO = "X-Dto";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected ClassUtil classUtil;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public T save(@RequestBody T t) throws Exception {
        return getService().save(t);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public T find(@PathVariable("id") Long id) throws Exception {
        return getService().findOne(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Stream<T> find() throws Exception {
        return getService().findAll();
    }

    @RequestMapping(value = "/all/pageable", method = RequestMethod.GET)
    @ResponseBody
    public Page<T> find(Pageable pageable) throws Exception {
        return getService().findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public T delete(@PathVariable("id") Long id) throws Exception {
        return getService().delete(id);
    }

    @RequestMapping(value = "/dto/{id}", method = RequestMethod.GET)
    @ResponseBody
    public GenericDTO findDto(@PathVariable("id") Long id) throws Exception {
        return getService().findOne(id, classUtil.getClassForName(request.getHeader(_X_DTO)));
    }

    @RequestMapping(value = "/dto/all", method = RequestMethod.GET)
    @ResponseBody
    public Stream<GenericDTO> findDto() throws Exception {
        return getService().findAll(classUtil.getClassForName(request.getHeader(_X_DTO)));
    }

    @RequestMapping(value = "/dto/all/pageable", method = RequestMethod.GET)
    @ResponseBody
    public Page<GenericDTO> findDto(Pageable pageable) throws Exception {
        return getService().findAll(pageable, classUtil.getClassForName(request.getHeader(_X_DTO)));
    }

    protected abstract GenericService<T> getService();

}
