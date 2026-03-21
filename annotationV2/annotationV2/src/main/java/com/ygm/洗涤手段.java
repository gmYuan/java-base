package com.ygm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
		ElementType.TYPE,
		ElementType.CONSTRUCTOR,
		ElementType.FIELD,
		ElementType.METHOD,
		ElementType.PARAMETER,
		ElementType.PACKAGE,
		ElementType.TYPE_PARAMETER
})
@Retention(RetentionPolicy.SOURCE)
public @interface 洗涤手段 {
	String[] value() default "水洗";

	String 成分();
}
