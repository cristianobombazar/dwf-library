package com.dwf.data;

import com.dwf.mvc.reflection.ClassUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Objects;

public class GenericSearchSpecification<E> implements Specification<E> {

    private SearchParameters searchParameters;

    public GenericSearchSpecification(SearchParameters searchParameters){
        this.searchParameters = searchParameters;
    }
    @Override
    @SuppressWarnings("unchecked")
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (searchParameters.getOperator() == Operator.IGUAL && searchParameters.isFilterByEnum()) {
            Class clazz = ClassUtils.getClass(searchParameters.getEnumClass());
            Enum enumValue = EnumUtils.getEnum(clazz, searchParameters.getValue());
            return builder.equal(getName(root, searchParameters.getKey()), enumValue);
        }else if (searchParameters.getOperator() == Operator.IGUAL) {
            return builder.equal(getName(root, searchParameters.getKey()), searchParameters.getValue());
        } else if (searchParameters.getOperator() == Operator.IS) {
            boolean key = Boolean.parseBoolean(searchParameters.getKey());
            if (key) {
                return builder.isTrue(getName(root, searchParameters.getKey()));
            } else {
                return builder.isFalse(getName(root, searchParameters.getKey()));
            }
        }else if (searchParameters.getOperator() == Operator.MAIOR){
            return builder.greaterThan(getName(root, searchParameters.getKey()), searchParameters.getValue());
        }else if (searchParameters.getOperator() == Operator.MAIOR_IGUAL){
            return builder.greaterThanOrEqualTo(getName(root, searchParameters.getKey()), searchParameters.getValue());
        }else if (searchParameters.getOperator() == Operator.MENOR){
            return builder.lessThan(getName(root, searchParameters.getKey()), searchParameters.getValue());
        }else if (searchParameters.getOperator() == Operator.MENOR_IGUAL){
            return builder.lessThanOrEqualTo(getName(root, searchParameters.getKey()), searchParameters.getValue());
        }else if (searchParameters.getOperator() == Operator.LIKE){
            if (searchParameters.isStartsWith()){
                return builder.like(builder.upper(getName(root, searchParameters.getKey())), searchParameters.getValue().toUpperCase()+"%");
            }else if (searchParameters.isEndsWith()){
                return builder.like(builder.upper(getName(root, searchParameters.getKey())), "%"+searchParameters.getValue().toUpperCase());
            }else if (searchParameters.isContaining()){
                return builder.like(builder.upper(getName(root, searchParameters.getKey())), "%"+searchParameters.getValue().toUpperCase()+"%");
            }
        }
        return null;
    }

    private Path getName(Root<E> root, String key){
        if (key.contains(".")){
            String[] splitedValues = key.split("\\.");
            Path path = null;
            for (String name : splitedValues) {
                if (Objects.isNull(path)){
                    path = root.get(name);
                }else{
                    path = path.get(name);
                }
            }
            return path;
        }else{
            return root.get(key);
        }
    }
}
