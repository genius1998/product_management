package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class SimpleProductServiceTest {
    @Autowired
    SimpleProductService simpleProductService;

    @Test
    @DisplayName("상품을 추가한 후 id로 조회하면 해당 상품이 조회되어야 한다.")
    void productAddAndFindByIdTest (){
        ProductDto productDto = new ProductDto("연필", 300, 20);

        ProductDto savedProductDto = simpleProductService.add(productDto);
        Long savedProductId = savedProductDto.getId();

        ProductDto foundProductDto = simpleProductService.findById(savedProductId);

        System.out.println(savedProductDto.getId().equals(foundProductDto.getId()));
        System.out.println(savedProductDto.getName().equals(foundProductDto.getName()));
        System.out.println(savedProductDto.getPrice().equals(foundProductDto.getPrice()));
        System.out.println(savedProductDto.getAmount().equals(foundProductDto.getAmount()));


    }
}