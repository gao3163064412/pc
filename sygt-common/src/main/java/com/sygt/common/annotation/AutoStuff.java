package com.sygt.common.annotation;

import com.sygt.common.enums.AnnoEnum;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AutoStuff {
    AnnoEnum annoType();
}
