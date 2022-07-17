package com.shahintraining.notification;

public interface BaseMapper<E,D> {
    D toDto(E entity);
    E toEntity(D dto);
}
