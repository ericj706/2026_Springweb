package example.TotalPractice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.TotalPractice.model.dto.ReviewDto;
import example.TotalPractice.service.ReviewService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin (value = "http://localhost:5173")
@RestController 
@RequestMapping ("/api/reviews")
public class ReviewController {
    @Autowired 
    private ReviewService reviewService;

    // 1. 제품별 리뷰 전체조회
    @GetMapping("")
    public List<ReviewDto> reviewPrint(@RequestParam(name = "bno") Integer bno){
        return reviewService.reviewPrint(bno);
    }
     
    // 2. 리뷰등록
    @PostMapping("")
    public boolean reviewAdd(@RequestBody ReviewDto reviewDto){
        return reviewService.reviewAdd(reviewDto);
    }

    // 3. 리뷰삭제
    @DeleteMapping 
    public boolean reviewDelete(@RequestParam(name = "rno") Integer rno){
        return reviewService.reviewDelete(rno);
    }
    
}