package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.dto.ProductDto;
import kr.co.hanbit.product.management.infrastructure.DatabaseProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleProductServiceUnitTest {

    @Mock
    private DatabaseProductRepository databaseProductRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private SimpleProductService simpleProductService;

    @Test
    @DisplayName("상품 추가 시 저장된 상품 정보를 반환한다.")
    void productAddTest() {
        ProductDto productDto = new ProductDto("포카", 300, 20);
        Long productId = 1L;

        Product product = ProductDto.toEntity(productDto);
        product.setId(productId);
        when(databaseProductRepository.add(any())).thenReturn(product);

        ProductDto savedProductDto = simpleProductService.add(productDto);

        assertEquals(productId, savedProductDto.getId());
        assertEquals(productDto.getName(), savedProductDto.getName());
        assertEquals(productDto.getPrice(), savedProductDto.getPrice());
        assertEquals(productDto.getAmount(), savedProductDto.getAmount());
    }
}
