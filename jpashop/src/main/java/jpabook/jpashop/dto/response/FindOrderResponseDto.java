package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FindOrderResponseDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<FindOrderItemResponseDto> orderItems;

    @Builder
    public FindOrderResponseDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems.stream()
                .map(FindOrderItemResponseDto::of)
                .collect(Collectors.toList());
    }

    public static FindOrderResponseDto of(Order order) {
        return FindOrderResponseDto.builder()
                .orderId(order.getId())
                .name(order.getMember().getName())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getStatus())
                .address(order.getDelivery().getAddress())
                .orderItems(order.getOrderItems())
                .build();
    }
}
