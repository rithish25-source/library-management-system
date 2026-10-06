package com.lms.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDto {

    private Integer libraryId;
    @NotNull
    @NotBlank
    private String libraryName;
    @Min(6000000000L)
    @Max(9999999999L)
    private long phoneNumber;

    private Integer addressId;

    private List<Integer> bookIds;
}