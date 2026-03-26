package com.scaler.mslearning.Dto;

import lombok.Data;

@Data
public class FakeStoreRequestDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
}
