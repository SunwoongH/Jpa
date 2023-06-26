package jpabook.jpashop.api;

import jpabook.jpashop.dto.ApiResponse;
import jpabook.jpashop.dto.response.FindOrderResponseDto;
import jpabook.jpashop.repository.query.FindOrderQueryDto;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("/v1/orders")
    public ApiResponse<List<FindOrderResponseDto>> findOrdersByFetchJoin() {
        return ApiResponse.of(orderService.findOrdersByFetchJoin());
    }

    @GetMapping("/v2/orders")
    public ApiResponse<List<FindOrderQueryDto>> findOrdersByQueryDtos() {
        return ApiResponse.of(orderService.findOrderQueryDtos());
    }
}
