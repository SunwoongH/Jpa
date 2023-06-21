package jpabook.jpashop.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemRequestDto {
    private String name;
    private int price;
    private int stockQuantity;

    @Builder
    public UpdateItemRequestDto(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
