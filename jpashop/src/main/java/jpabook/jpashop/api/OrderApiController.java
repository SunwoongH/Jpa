package jpabook.jpashop.api;

import jpabook.jpashop.dto.ApiResponse;
import jpabook.jpashop.dto.response.FindOrderResponseDto;
import jpabook.jpashop.dto.response.FindSimpleOrderResponseDto;
import jpabook.jpashop.repository.query.FindOrderQueryDto;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("/v1/simple/orders")
    public ResponseEntity<ApiResponse<List<FindSimpleOrderResponseDto>>> findSimpleOrdersByFetchJoin() {
        return ResponseEntity.ok(ApiResponse.of(orderService.findSimpleOrdersByFetchJoin()));
    }

    @GetMapping("/v2/simple/orders")
    public ResponseEntity<ApiResponse<List<FindOrderQueryDto>>> findSimpleOrdersByQueryDtos() {
        return ResponseEntity.ok(ApiResponse.of(orderService.findOrderQueryDtos()));
    }

    @GetMapping("/v1/orders")
    public ResponseEntity<ApiResponse<List<FindOrderResponseDto>>> findOrdersByFetchJoin() {
        return ResponseEntity.ok(ApiResponse.of(orderService.findOrdersByFetchJoin()));
    }

    @GetMapping("/v2/orders")
    public ResponseEntity<ApiResponse<List<FindOrderResponseDto>>> findOrdersByOptimizeFetchJoin(
            @RequestParam(value = "offset", defaultValue = "0") final int offset,
            @RequestParam(value = "limit", defaultValue = "100") final int limit) {
        return ResponseEntity.ok(ApiResponse.of(orderService.findOrdersByOptimizeFetchJoin(offset, limit)));
    }
}
