package fr.deroffal.portail.api.authentification;

import com.openpojo.reflection.filters.FilterNonConcrete;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

class GetSetTest {

	private static final String BASE_PACKAGE = "fr.deroffal.portail";

	@Test
	void testerGetterSetter() {
		Validator validator = ValidatorBuilder.create().with(new SetterTester()).with(new GetterTester()).build();

		//NonConcrete : pas de filtre sur interface, abstract, enum
		validator.validate(PojoClassFactory.getPojoClassesRecursively(BASE_PACKAGE, new FilterNonConcrete()));
	}

}
