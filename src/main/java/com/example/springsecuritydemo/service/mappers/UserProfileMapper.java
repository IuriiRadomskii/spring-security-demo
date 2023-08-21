package com.example.springsecuritydemo.service.mappers;

import com.example.springsecuritydemo.dto.UserProfileDto;
import com.example.springsecuritydemo.model.entity.UserProfile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserProfileMapper {

    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "birthTown", source = "birthTown")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "about", source = "about")
    UserProfile toEntity(UserProfileDto userProfileDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "birthTown", source = "birthTown")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "about", source = "about")
    UserProfileDto toDto(UserProfile userProfile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UserProfileDto userProfileDto, @MappingTarget UserProfile userProfile);

}
