package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.response.FindOrderResponseDto;
import jpabook.jpashop.dto.response.FindSimpleOrderResponseDto;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberOriginalJpaRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.query.FindOrderQueryDto;
import jpabook.jpashop.repository.query.OrderFlatQueryDto;
import jpabook.jpashop.repository.query.OrderQueryRepository;
import jpabook.jpashop.repository.simplequery.FindSimpleOrderQueryDto;
import jpabook.jpashop.repository.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberOriginalJpaRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // entity 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        // 주문 저장
        // Cascade Option 이 설정되어 있기 때문에 Delivery 와 OrderItem 이 같이 persist 된다.
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // entity 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     * 주문 조회 - API
     */
    public List<FindSimpleOrderResponseDto> findSimpleOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch)
                .stream()
                .map(FindSimpleOrderResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<FindOrderResponseDto> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch)
                .stream()
                .map(FindOrderResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 주문 조회 - API fetch join
     */
    public List<FindSimpleOrderResponseDto> findSimpleOrdersByFetchJoin() {
        return orderRepository.findAllWithMemberDelivery()
                .stream()
                .map(FindSimpleOrderResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<FindOrderResponseDto> findOrdersByFetchJoin() {
        return orderRepository.findAllWithMemberDelivery()
                .stream()
                .map(FindOrderResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<FindOrderResponseDto> findOrdersByOptimizeFetchJoin(int offset, int limit) {
        return orderRepository.findAllWithMemberDelivery(offset, limit)
                .stream()
                .map(FindOrderResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 주문 조회 - API JPA에서 DTO 바로 조회
     */
    public List<FindSimpleOrderQueryDto> findSimpleOrderQueryDtos() {
        return orderSimpleQueryRepository.findSimpleOrderQueryDtos();
    }

    public List<FindOrderQueryDto> findOrderQueryDtos() {
        return orderQueryRepository.findOrderQueryDtosWithOrderItemQueryDto();
    }

    public List<FindOrderQueryDto> findOptimizeOrderQueryDtos() {
        return orderQueryRepository.findOrdersQueryDtosWithOptimizeOrderItemQueryDto();
    }

    public List<OrderFlatQueryDto> findOrderFlatQueryDtos() {
        return orderQueryRepository.findOrderFlatQueryDtos();
    }

    public List<FindOrderQueryDto> findOrderQueryDtosWithOrderFlatQueryDto() {
        return orderQueryRepository.findOrderQueryDtosWithOrderFlatQueryDto();
    }

    /**
     * 주문 조회 - Thymeleaf
     */
    public List<Order> findOrdersForView(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
