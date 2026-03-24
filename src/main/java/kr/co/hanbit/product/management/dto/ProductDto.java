package kr.co.hanbit.product.management.dto;

import jakarta.validation.constraints.NotNull;

public class ProductDto {

    private Long id;
    private String name;
    private Integer price;
    private Integer amount;

    public Long getId() {
        return id;
    }
    @NotNull
    public String getName() {
        return name;
    }
    @NotNull
    public Integer getPrice() {
        return price;
    }
    @NotNull
    public Integer getAmount() {
        return amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
