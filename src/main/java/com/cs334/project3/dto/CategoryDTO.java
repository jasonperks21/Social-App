package com.cs334.project3.dto;

import com.cs334.project3.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String catergoryName, categoryDescription;

    public CategoryDTO(Category category) {
        this.categoryId = category.getCategory_id();
        this.catergoryName =category.getCategoryName();
        this.categoryDescription = category.getDescription();
    }

}
