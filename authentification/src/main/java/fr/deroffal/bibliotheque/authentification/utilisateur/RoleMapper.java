package fr.deroffal.bibliotheque.authentification.utilisateur;

import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface RoleMapper {

    default String toRole(final RoleEntity role) {
        return role.getRole();
    }
}
