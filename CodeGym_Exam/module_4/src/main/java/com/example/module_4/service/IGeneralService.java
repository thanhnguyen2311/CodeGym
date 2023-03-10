package com.example.module_4.service;

import java.util.Optional;

public interface IGeneralService <T>{
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    boolean save(T t);

    void remove(Long id);
}
