package fr.deroffal.bibliotheque.livre.adapter.authentification;

import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration;
import fr.deroffal.bibliotheque.securite.details.JwtUserDetails;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface UserDetailsMapper {

    JwtUserDetails toUserDetails(User user);
}
