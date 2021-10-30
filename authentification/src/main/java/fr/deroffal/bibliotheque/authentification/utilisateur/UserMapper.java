package fr.deroffal.bibliotheque.authentification.utilisateur;

import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class, uses = { RoleMapper.class, PasswordMapper.class })
public interface UserMapper {

    UserDto toDto(final UserEntity user);

    /**
     * Création d'en Entity pour un utilisateur.
     * Pas besoin de mapper l'ID et les rôles qui vont être déterminés par la suite (insertion en bdd, ou dans un service). Le mot de passe sera encodé dans le mapper.
     *
     * @param user un utilisateur à créer.
     * @return un {@link UserEntity} initialisé pour l'insertion en base.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "roles", ignore = true)
    UserEntity toEntityAndEncorePassword(final UserDto user);
}
