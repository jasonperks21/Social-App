package com.cs334.project3.service;

import com.cs334.project3.dto.CategoryDTO;
import com.cs334.project3.model.Category;
import com.cs334.project3.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category getById(Long catId){
        return categoryRepository.getById(catId);
    }

    public List<CategoryDTO> getCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList;

        if (categoryList == null) {
            throw new NullPointerException();
        } else {
            categoryDTOList = new ArrayList<>();
            for (Category c:categoryList) {
                categoryDTOList.add(new CategoryDTO(c));
            }
            return categoryDTOList;
        }
    }
}

