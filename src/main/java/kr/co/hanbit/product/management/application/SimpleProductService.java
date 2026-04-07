package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.dto.ProductDto;
import kr.co.hanbit.product.management.infrastructure.DatabaseProductRepository;
import kr.co.hanbit.product.management.infrastructure.ListProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleProductService {

    private DatabaseProductRepository databaseProductRepository;
    private ValidationService validationService;

    @Autowired
    public SimpleProductService(DatabaseProductRepository databaseProductRepository,
                                ValidationService validationService) {
        this.databaseProductRepository = databaseProductRepository;
//        this.modelMapper = modelMapper;
        this.validationService = validationService;
    }

    public ProductDto add(ProductDto productDto) {

        // 1. ProductDto → Product 변환
        Product product = ProductDto.toEntity(productDto);
//        Product product = modelMapper.map(productDto, Product.class);
        // 유효성 검사 추가
        validationService.checkValid(product);

        // 2. 레포지토리 호출
        Product savedProduct = databaseProductRepository.add(product);

        // 3. Product → ProductDto 변환
        ProductDto savedProductDto = ProductDto.toDto(savedProduct);

        // 4. DTO 반환
        return savedProductDto;
    }

    public ProductDto findById(Long id) {
        Product product = databaseProductRepository.findById(id);
//        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        ProductDto productDto = ProductDto.toDto(product);
        return productDto;
    }

    public List<ProductDto> findAll() {
        List<Product> products = databaseProductRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> ProductDto.toDto(product))
                .toList();
        return productDtos;
    }

    public List<ProductDto> findByNameContaining(String name) {
        List<Product> products = databaseProductRepository.findByNameContaining(name);

        List<ProductDto> productDtos = products.stream()
                .map(product -> ProductDto.toDto(product))
                .toList();

        return productDtos;
    }

    public ProductDto update(ProductDto productDto) {
        Product product = ProductDto.toEntity(productDto);

        Product updatedProduct = databaseProductRepository.update(product);

        ProductDto updatedProductDto =
                ProductDto.toDto(updatedProduct);

        return updatedProductDto;
    }

    public void delete(Long id) {
        databaseProductRepository.delete(id);
    }
}
