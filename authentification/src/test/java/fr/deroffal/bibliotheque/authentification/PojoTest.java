package fr.deroffal.bibliotheque.authentification;

import static nl.jqno.equalsverifier.Warning.SURROGATE_KEY;

import java.util.List;

import com.openpojo.reflection.filters.FilterClassName;
import com.openpojo.reflection.filters.FilterNonConcrete;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import fr.deroffal.bibliotheque.authentification.adapter.repository.RoleEntity;
import fr.deroffal.bibliotheque.authentification.adapter.repository.UserEntity;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PojoTest {

    private static final String BASE_PACKAGE = "fr.deroffal.bibliotheque";

    @Test
    @DisplayName("valider les getters et les setters")
    void testGetterAndSetterContract() {
        Validator validator = ValidatorBuilder.create()
            .with(new SetterTester(), new GetterTester())
            .build();

        //NonConcrete : pas de filtre sur interface, abstract, enum
        validator.validateRecursively(
            BASE_PACKAGE,
            new FilterNonConcrete(), new FilterClassName(".+Dto$")
        );
    }

    @Test
    @DisplayName("valider les méthodes equals & hashCode")
    void testEqualsAndHashCodeContract() {
        List.of(RoleEntity.class, UserEntity.class)
            .forEach(clazz ->
                EqualsVerifier.forClass(clazz)
                    .suppress(SURROGATE_KEY)
                    .verify()
            );
    }
}
