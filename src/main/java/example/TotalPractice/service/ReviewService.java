package example.TotalPractice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.TotalPractice.model.dto.ReviewDto;
import example.TotalPractice.model.entity.ProductEntity;
import example.TotalPractice.model.entity.ReviewEntity;
import example.TotalPractice.model.repository.ProductRepository;
import example.TotalPractice.model.repository.ReviewRepoistory;

@Service 
public class ReviewService {
    @Autowired private ReviewRepoistory reviewRepository;
    @Autowired private ProductRepository productRepository;

    // 1. 제품별 리뷰 전체 조회
    public List<ReviewDto>reviewPrint(Integer bno){
        // 해당 제품의 리뷰 Entity 여러개 조회
        List<ReviewEntity>reviewEntities = reviewRepository.findByProductEntity_Bno(bno);

        // DTO 여러개를 저장할 리스트
        List<ReviewDto>reviewDtos = new ArrayList<>();

        // Entity -> DTO
        for (ReviewEntity reviewEntity : reviewEntities) {
            ReviewDto reviewDto = ReviewDto.from(reviewEntity);
            reviewDtos.add(reviewDto);
        }
        return reviewDtos;
    }

    // 2. 리뷰 등록
    public boolean reviewAdd(ReviewDto reviewDto){
        // bno에 해당하는 제품 찾기
        ProductEntity productEntity = productRepository.findById(reviewDto.getBno()).orElse(null);
        // 제품이 존재하지 않으면 등록 실패
        if(productEntity == null){
            return false;
        }
        // DTO -> Entity
        ReviewEntity reviewEntity = reviewDto.toEntity(productEntity);
                
        // 리뷰저장
        ReviewEntity savedEntity = reviewRepository.save(reviewEntity);

        // 저장 성공여부 반환
        if(savedEntity.getRno()>=1){
            return true;
        }else{
            return false;
        }
    }

    // 3. 리뷰삭제
    public boolean reviewDelete(int rno){
        // 해당 리뷰가 존자하는지 확인
        boolean result = reviewRepository.existsById(rno);
        // 존재하지 않으면 삭제 실패
        if(result == false){
            return false;
        }
        // 리뷰삭제
        reviewRepository.deleteById(rno);
        return true;
    }
    
}