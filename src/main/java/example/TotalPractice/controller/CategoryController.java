package example.TotalPractice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.TotalPractice.model.dto.CategoryDto;
import example.TotalPractice.service.CategoryService;
import lombok.RequiredArgsConstructor;

@CrossOrigin (value = "http://localhost:5173")
@RestController 
@RequestMapping ("/api/categories")
@RequiredArgsConstructor 
public class CategoryController {
    private final CategoryService categoryService;
    
     // 1. 카테고리 등록
    @PostMapping 
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

     // 2. 카테고리 전체 조회
    @GetMapping 
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // 3. 카테고리 삭제
    @DeleteMapping 
    public boolean deleteCategory(@RequestParam Integer cno) {
        return categoryService.deleteCategory(cno);
    }
}