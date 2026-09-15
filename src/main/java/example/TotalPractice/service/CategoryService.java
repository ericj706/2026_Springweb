package example.TotalPractice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import example.TotalPractice.model.dto.CategoryDto;
import example.TotalPractice.model.entity.CategoryEntity;
import example.TotalPractice.model.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CategoryService {
        private final CategoryRepository categoryRepository;

        // 1. 카테고리 등록
        public CategoryDto createCategory(CategoryDto dto) {
            CategoryEntity entity = CategoryEntity.builder()
            .name(dto.getName())
            .build();

            CategoryEntity saved = categoryRepository.save(entity);
            return CategoryDto.builder()
            .cno(saved.getCno())
            .name(saved.getName())
            .build();
        }

    // 2. 카테고리 전체 조회
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> entityList = categoryRepository.findAll();
        List<CategoryDto> dtoList = new ArrayList<>();
        for (CategoryEntity entity : entityList) {
            dtoList.add(CategoryDto.builder()
                    .cno(entity.getCno())
                    .name(entity.getName())
                    .build());
        }
        return dtoList;
    }

    // 3. 카테고리 삭제
    public boolean deleteCategory(Integer cno) {
        if (categoryRepository.existsById(cno)) {
            categoryRepository.deleteById(cno);
            return true;
        }
        return false;
    }
}