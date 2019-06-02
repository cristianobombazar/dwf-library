package com.github.dwflibrary.mvc;

import com.github.dwflibrary.data.SearchParameters;
import com.github.dwflibrary.mvc.interfaces.IGenericResourceSearch;
import com.github.dwflibrary.mvc.interfaces.IGenericServiceSearch;
import com.github.dwflibrary.util.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class GenericResourceSearch<E> implements IGenericResourceSearch<E> {

    private final IGenericServiceSearch<E> serviceSearch;

    public GenericResourceSearch(IGenericServiceSearch<E> serviceSearch){
        this.serviceSearch = serviceSearch;
    }

    @Override
    @PostMapping("/search")
    public Page<E> findAll(Pageable pageable, @RequestBody List<SearchParameters> search, @RequestParam(required = false) String fields) {
        if (!CollectionUtils.isEmpty(search)){
            if (!StringUtils.isEmpty(fields)){
                Page<E> page = serviceSearch.findAll(search, pageable);
                return handleFieldsWithPage(pageable, fields, page);
            }
            return serviceSearch.findAll(search, pageable);
        }
        return null;
    }

    Page<E> handleFieldsWithPage(Pageable pageable, String fields, Page<E> page){
        if (!CollectionUtils.isEmpty(page.getContent())){
            ObjectMapper objectMapper = getObjectMapperSquiggly(fields);
            String content = ObjectMapperUtil.toString(objectMapper, page.getContent());
            List result = ObjectMapperUtil.toObject(getObjectMapper(), content, List.class);
            return new PageImpl<E>(result, pageable, page.getTotalElements());
        }
        return page;
    }

}
