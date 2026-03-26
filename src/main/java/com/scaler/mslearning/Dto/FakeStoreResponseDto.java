package com.scaler.mslearning.Dto;

import lombok.Data;

@Data
public class FakeStoreResponseDto {
    public Long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
}
