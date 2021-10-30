package fr.deroffal.bibliotheque.authentification.adapter.repository.mapping;

import fr.deroffal.bibliotheque.commons.mapping.MapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapperConfiguration.class)
public class PasswordMapper {

    @Autowired
    private PasswordEncodeAdapter passwordEncoder;

    @Named("encodePassword")
    public String encodePassword(final CharSequence password) {
        return passwordEncoder.encodePassword(password);
    }
}
