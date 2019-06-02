package com.dwf.mvc;

import com.dwf.data.GenericSearchSpecification;
import com.dwf.data.SearchParameters;
import com.dwf.mvc.interfaces.IGenericServiceSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.CollectionUtils;

import java.util.List;

class GenericServiceSearch<E> implements IGenericServiceSearch<E> {

    private final JpaSpecificationExecutor<E> repository;

    public GenericServiceSearch(JpaSpecificationExecutor<E> repository){
        this.repository = repository;
    }

    @Override
    public Page<E> findAll(List<SearchParameters> parameters, Pageable pageable) {
        if (!CollectionUtils.isEmpty(parameters)){
            Specification<E> specification = null;
            boolean firstParameter = true;
            for (SearchParameters parameter : parameters) {
                GenericSearchSpecification<E> searchSpecification = new GenericSearchSpecification<>(parameter);
                if (specification == null){
                    specification = searchSpecification;
                }
                if (firstParameter){
                    specification = Specification.where(searchSpecification);
                }else{
                    specification = specification.and(searchSpecification);
                }
                firstParameter = false;
            }
            return repository.findAll(specification, pageable);
        }
        return repository.findAll(null, pageable);
    }
}
