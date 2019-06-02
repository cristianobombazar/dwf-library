package com.dwf.mvc;

import com.dwf.mvc.interfaces.IGenericResource;
import com.dwf.mvc.interfaces.IGenericService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

public abstract class GenericResource<E, ID> extends GenericResourceSearch<E> implements IGenericResource<E, ID> {

    private final IGenericService<E, ID> service;
    private Class<E> clazz;

    public GenericResource(IGenericService<E, ID> service){
        super(service);
        this.service = service;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    @PostMapping
    public E save(@Valid @RequestBody E entity) throws Exception {
        return service.save(entity);
    }

    @Override
    @PutMapping("/{id}")
    public E save(@PathVariable ID id, @Valid @RequestBody E entity) throws Exception {
        return service.save(id, entity);
    }

    @Override
    @GetMapping("/{id}")
    public E findById(@PathVariable ID id, @RequestParam(required = false) String fields) throws EntityNotFoundException {
        if (Objects.nonNull(fields) && !StringUtils.isEmpty(fields)){
            ObjectMapper objectMapper = getObjectMapperSquiggly(fields);
            E entity = service.findById(id).orElseThrow(EntityNotFoundException::new);
            return SquigglyUtils.objectify(objectMapper, entity, clazz);
        }
        return service.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @GetMapping("/")
    @SuppressWarnings("unchecked")
    public List<E> findAll(@RequestParam(required = false) String fields) {
        if (Objects.nonNull(fields) && !StringUtils.isEmpty(fields)){
            ObjectMapper objectMapper = getObjectMapperSquiggly(fields);
            return (List<E>) SquigglyUtils.listify(objectMapper, service.findAll());
        }
        return service.findAll();
    }

    @Override
    @GetMapping
    @SuppressWarnings("unchecked")
    public Page<E> findAll(Pageable pageable, @RequestParam(required = false) String fields) {
        if (!StringUtils.isEmpty(fields)){
            Page<E> page = service.findAll(pageable);
            return handleFieldsWithPage(pageable, fields, page);
        }
        return service.findAll(pageable);
    }

    @Override
    @PostMapping("/by-example")
    @SuppressWarnings("unchecked")
    public List<E> findAll(@RequestBody E entity, @RequestParam(required = false) String fields) {
        if (Objects.nonNull(fields) && !StringUtils.isEmpty(fields)) {
            ObjectMapper objectMapper = getObjectMapperSquiggly(fields);
            return (List<E>) SquigglyUtils.listify(objectMapper, service.findAll(Example.of(entity)));
        }
        return service.findAll(Example.of(entity));
    }

    @Override
    @PostMapping("/by-example-and-sort")
    @SuppressWarnings("unchecked")
    public List<E> findAll(@RequestBody E entity, Sort sort, @RequestParam(required = false) String fields) {
        if (Objects.nonNull(fields) && !StringUtils.isEmpty(fields)) {
            ObjectMapper objectMapper = getObjectMapperSquiggly(fields);
            return (List<E>) SquigglyUtils.listify(objectMapper, service.findAll(Example.of(entity), sort));
        }
        return service.findAll(Example.of(entity), sort);
    }

    @Override
    @DeleteMapping
    public void delete(@RequestBody E entity) {
        service.delete(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable ID id) {
        service.deleteById(id);
    }

    @Override
    @PostMapping("/count")
    public long countByExample(@RequestBody E entity) {
        return service.countByExample(Example.of(entity));
    }

    @Override
    @GetMapping("/count-all")
    public long countAllRows() {
        return service.countAllRows();
    }

    @Override
    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable ID id) {
        return service.existsById(id);
    }

    @Override
    @PostMapping("/exists")
    public boolean exists(@RequestBody E entity) {
        return service.exists(Example.of(entity));
    }

}
