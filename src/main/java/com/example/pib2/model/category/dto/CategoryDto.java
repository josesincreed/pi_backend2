package com.example.pib2.model.category.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto{
    private Long id;
    private String name;
    private String description;
    private String imageUrl;

}
