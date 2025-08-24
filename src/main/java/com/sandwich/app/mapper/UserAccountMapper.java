package com.sandwich.app.mapper;

import com.sandwich.app.domain.dto.user.UserAccountDto;
import com.sandwich.app.domain.entity.UserAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserAccountMapper {

    UserAccountDto convert(UserAccountEntity entity);

    @Mapping(target = "id", ignore = true)
    UserAccountEntity convert(@MappingTarget UserAccountEntity entity, UserAccountDto dto);
}
