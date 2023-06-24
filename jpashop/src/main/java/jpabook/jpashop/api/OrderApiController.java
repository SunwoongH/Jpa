package jpabook.jpashop.api;

import jpabook.jpashop.dto.ApiResponse;
import jpabook.jpashop.dto.response.FindOrderResponseDto;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping
    public ApiResponse<List<FindOrderResponseDto>> orders() {
        return ApiResponse.of(orderService.findOrders(new OrderSearch()));
    }
}
