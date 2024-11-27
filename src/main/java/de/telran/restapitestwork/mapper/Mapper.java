package de.telran.restapitestwork.mapper;

import de.telran.restapitestwork.model.entity.user.User;

import java.util.Optional;
import java.util.Set;

public interface Mapper<Entity, Dto> {

    Dto toDto(User user);
    Set<Dto> toDtoSet(Set<Entity> entitySet);

    Entity toEntity(Dto dto);

    Set<Entity> toEntitySet(Set<Dto> dtoSet);
}
