package com.lms.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private Integer bookId;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    @Size(min=3,max=15)//num of char
    private String author;
    private String category;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;
    private int numberOfCopy;

    private List<Integer> userIds;
}