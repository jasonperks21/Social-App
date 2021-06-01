package com.cs334.project3.service;

import com.cs334.project3.model.Category;
import com.cs334.project3.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category getById(Long catId){
        return categoryRepository.getById(catId);
    }
}

