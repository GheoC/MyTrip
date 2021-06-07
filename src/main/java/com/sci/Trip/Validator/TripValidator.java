package com.sci.Trip.Validator;

import com.sci.Trip.Model.Trip;
import com.sci.Trip.Service.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//reference: https://docs.spring.io/spring-framework/docs/3.0.0.M4/reference/html/ch05s02.html

@Component
public class TripValidator implements Validator {

    TripsService tripsService;
    @Autowired
    public void setTripsService(TripsService tripsService) {
        this.tripsService = tripsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Trip.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Trip trip = (Trip) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"tripName","NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"description","NotEmpty");


    }
}
