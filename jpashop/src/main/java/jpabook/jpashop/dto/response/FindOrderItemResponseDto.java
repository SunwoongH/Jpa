package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.OrderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FindOrderItemResponseDto {
    private String name;
    private int orderPrice;
    private int count;

    @Builder
    public FindOrderItemResponseDto(String name, int orderPrice, int count) {
        this.name = name;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static FindOrderItemResponseDto of(OrderItem orderItem) {
        return FindOrderItemResponseDto.builder()
                .name(orderItem.getItem().getName())
                .orderPrice(orderItem.getOrderPrice())
                .count(orderItem.getCount())
                .build();
    }
}
