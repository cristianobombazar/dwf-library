package com.dwf.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    E getById(ID id);
}
