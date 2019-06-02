package com.dwf.mvc.interfaces;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IGenericService<E, ID> extends IGenericServiceSearch<E> {
    E save(E entity) throws Exception;
    E save(ID id, E entity) throws Exception;
    Optional<E> findById(ID id);
    List<E> findAll();
    Page<E> findAll(Pageable pageable);
    List<E> findAll(Example<E> example);
    List<E> findAll(Example<E> example, Sort sort);
    void delete(E entity);
    void deleteById(ID id);
    long countByExample(Example<E> example);
    long countAllRows();
    boolean existsById(ID id);
    boolean exists(Example<E> example);
}
