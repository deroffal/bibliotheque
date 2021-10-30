package fr.deroffal.bibliotheque.authentification.adapter.repository.mapping;

import fr.deroffal.bibliotheque.authentification.adapter.repository.RoleEntity;
import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
interface RoleMapper {

    default String toRole(final RoleEntity role) {
        return role.getRole();
    }
}
