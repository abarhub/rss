package org.rss.beans;

import com.google.common.collect.Lists;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.impl.PojoClassImpl;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;
import org.rss.beans.flux.DateTimeZone;
import org.rss.beans.flux.RssChannel;
import org.rss.beans.flux.RssItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Alain on 28/08/2016.
 */
public class BeansTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(BeansTest.class);

	private String packageNameParam = "org.rss.beans.param";
	private String packageNameFlux = "org.rss.beans.flux";

	@Test
	public void validateParam() {
		LOGGER.info("Validation des beans : "+packageNameParam);
		Validator validator = ValidatorBuilder.create()
				.with(new SetterMustExistRule(),
						new GetterMustExistRule())
				.with(new SetterTester(),
						new GetterTester())
				.build();
		validator.validate(packageNameParam);
	}

	@Test
	public void validateFlux() {
		LOGGER.info("Validation des beans : "+packageNameFlux);
		Validator validator = ValidatorBuilder.create()
				.with(new SetterMustExistRule(),
						new GetterMustExistRule())
				.with(new SetterTester(),
						new GetterTester())
				.build();
		List<PojoClass> liste= PojoClassFactory.getPojoClasses(packageNameFlux,new FilterTestClasses());//=PojoClass.
		validator.validate(liste);
	}

	private static class FilterTestClasses implements PojoClassFilter {
		public boolean include(PojoClass pojoClass) {
			return pojoClass.getClazz()!= DateTimeZone.class;
			//return !pojoClass.getSourcePath().contains("/test-classes/");
		}
	}
}
