package fr.deroffal.bibliotheque.authentification;

import com.openpojo.reflection.filters.FilterClassName;
import com.openpojo.reflection.filters.FilterNonConcrete;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

class GetSetTest {

    private static final String BASE_PACKAGE = "fr.deroffal.bibliotheque";

    @Test
    void testerGetterSetter() {
        Validator validator = ValidatorBuilder.create()
            .with(new SetterTester(), new GetterTester())
            .build();

        //NonConcrete : pas de filtre sur interface, abstract, enum
        validator.validateRecursively(
            BASE_PACKAGE,
            new FilterNonConcrete(), new FilterClassName(".+Dto$")
        );
    }
}
