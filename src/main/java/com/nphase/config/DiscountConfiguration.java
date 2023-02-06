package com.nphase.config;

// WARN: Non production configuration
// In case for production such configuration are picked from external source i.e either form DB, property file or from any config server
public interface DiscountConfiguration {

    Integer ItemAmount = 3;
    Double discountPercentage= 0.1;
}
