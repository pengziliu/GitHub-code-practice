package com.example.demo.utils;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationUtils {

    public static Validator getValidator(){
        return validator;
    }

    static Validator validator;
    static{
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator=validatorFactory.getValidator();
    }
}
