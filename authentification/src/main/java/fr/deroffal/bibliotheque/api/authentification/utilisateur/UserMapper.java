package fr.deroffal.bibliotheque.api.authentification.utilisateur;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "roles", ignore = true)
    public abstract UserDto toDto(final UserEntity user);

    @AfterMapping
    void afterEntityToDto(final UserEntity userIn, @MappingTarget final UserDto userOut) {
        Collection<RoleEntity> roles = userIn.getRoles();
        if (roles != null) {
            userOut.setRoles(roles.stream().map(RoleEntity::getRole).collect(Collectors.toList()));
        }
    }

    /**
     * Création d'en Entity pour un utilisateur.
     * Pas besoin de mapper l'ID et les rôles qui vont être déterminés par la suite (insertion en bdd, ou dans un service). Le mot de passe sera encodé dans le mapper.
     *
     * @param user un utilisateur à créer.
     * @return un {@link UserEntity} initialisé pour l'insertion en base.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    public abstract UserEntity toEntityAndEncorePassword(final UserDto user);

    @AfterMapping
    void afterDtoToEntity(final UserDto userIn, @MappingTarget final UserEntity userOut) {
        userOut.setPassword(passwordEncoder.encode(userIn.getPassword()));
    }
}
