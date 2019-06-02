package com.github.dwflibrary.mvc;

import com.github.dwflibrary.data.GenericRepository;
import com.github.dwflibrary.mvc.exception.IdViolationException;
import com.github.dwflibrary.mvc.interfaces.IGenericService;
import com.github.dwflibrary.tenant.TenantSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class GenericService<E extends CoreEntity, ID> extends GenericServiceSearch<E> implements IGenericService<E, ID> {

    private GenericRepository<E, ID> repository;

    public GenericService(GenericRepository<E, ID> repository){
        super(repository);
        this.repository = repository;
    }

    @Override
    public E save(@Valid E entity) throws Exception {
        if (isTenantSupport(entity)){
            generateTenant(entity);
        }
        return repository.save(entity);
    }

    @Override
    public E save(ID id, @Valid E entity) throws Exception {
        if (entity.getId().equals(id)){
            Optional<E> entitySavedOptional = repository.findById(id);
            if (entitySavedOptional.isPresent()){
                E entitySaved = entitySavedOptional.get();
                if (isTenantSupport(entity)){
                    generateTenant(entity);
                }
                BeanUtils.copyProperties(entity, entitySaved, "id");
                return repository.save(entitySaved);
            }
            throw new EntityNotFoundException("Entity "+id+" not found!");
        }
        throw new IdViolationException("id from resource and id from entity doesn't match.", entity.getId(), id);
    }

    @Override
    public Optional<E> findById(ID id) {
        return Optional.ofNullable(repository.getById(id));
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<E> findAll(Example<E> example) {
        return repository.findAll(example);
    }

    @Override
    public List<E> findAll(Example<E> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public void delete(E entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public long countByExample(Example<E> example) {
        return repository.count(example);
    }

    @Override
    public long countAllRows() {
        return repository.count();
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public boolean exists(Example<E> example) {
        return repository.exists(example);
    }

    protected boolean isTenantSupport(E entity){
        return entity instanceof TenantSupport;
    }

    protected void generateTenant(E entity){
        TenantSupport tenantSupport = (TenantSupport) entity;
        if (Objects.isNull(tenantSupport.getTenantId())){
            tenantSupport.setTenantId(UUID.randomUUID().toString());
        }
    }

}
