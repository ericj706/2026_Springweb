package example.TotalPractice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.TotalPractice.model.dto.ProductDto;
import example.TotalPractice.service.ProductService;

@RestController @RequestMapping ("/api/products")
public class ProductController {
    @Autowired private ProductService productService;

    @PostMapping ("")
    public boolean 제품등록(@RequestBody ProductDto productDto){
        return productService.제품등록(productDto);
    }

    @GetMapping ("")
    public List<ProductDto> 제품전체조회(){
        return productService.제품전체조회();
    }

}
