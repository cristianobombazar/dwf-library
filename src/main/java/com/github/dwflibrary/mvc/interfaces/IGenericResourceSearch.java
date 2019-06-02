package com.github.dwflibrary.mvc.interfaces;

import com.github.dwflibrary.data.SearchParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenericResourceSearch<E> extends ISuperResource {
    Page<E> findAll(Pageable pageable, List<SearchParameters> searchParametersList, String fields);
}
