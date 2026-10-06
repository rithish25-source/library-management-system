package com.lms.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private Integer addressId;
    @Min(100)
    private int houseNumber;
    @NotNull(message="area cannot be null")//null
    private String area;
    @NotBlank
    private String city;
    private String state;
    private String country;
    @Digits(integer=6,fraction=0)
    private long pinCode;
	
    
    
}