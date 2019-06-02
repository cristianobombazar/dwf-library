package com.github.dwflibrary.mvc.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface IGenericResource<E, ID> extends ISuperResource {
    E save(E entity) throws Exception;
    E save(ID id, E entity) throws Exception;
    E findById(ID id, String fields) throws EntityNotFoundException;
    List<E> findAll(String fields);
    Page<E> findAll(Pageable pageable, String fields);
    List<E> findAll(E example, String fields);
    List<E> findAll(E example, Sort sort, String fields);
    void delete(E entity);
    void deleteById(ID id);
    long countByExample(E example);
    long countAllRows();
    boolean existsById(ID id);
    boolean exists(E example);
}
