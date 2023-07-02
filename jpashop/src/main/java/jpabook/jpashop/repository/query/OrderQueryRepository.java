package jpabook.jpashop.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {
    private final EntityManager entityManager;

    public List<FindOrderQueryDto> findOrderQueryDtosWithOrderItemQueryDto() {
        List<FindOrderQueryDto> findOrderQueryDtos = findOrdersQueryDtos();
        findOrderQueryDtos.forEach(order -> {
            List<OrderItemQueryDto> orderItemQueryDtos = findOrderItemQueryDtos(order.getOrderId());
            order.setOrderItems(orderItemQueryDtos);
        });
        return findOrderQueryDtos;
    }

    public List<FindOrderQueryDto> findOrdersQueryDtosWithOptimizeOrderItemQueryDto() {
        List<FindOrderQueryDto> findOrderQueryDtos = findOrdersQueryDtos();
        List<Long> orderIds = findOrderQueryDtosToOrderIds(findOrderQueryDtos);
        List<OrderItemQueryDto> orderItemQueryDtos = findOptimizeOrderItemQueryDtos(orderIds);
        Map<Long, List<OrderItemQueryDto>> orderItemQueryDtoMap = createOrderItemQueryDtoMap(orderItemQueryDtos);
        findOrderQueryDtos.forEach(order -> order.setOrderItems(orderItemQueryDtoMap.get(order.getOrderId())));
        return findOrderQueryDtos;
    }

    public List<FindOrderQueryDto> findOrderQueryDtosWithOrderFlatQueryDto() {
        List<OrderFlatQueryDto> orderFlatQueryDtos = findOrderFlatQueryDtos();
        Map<FindOrderQueryDto, List<OrderItemQueryDto>> findOrderQueryDtoMap = orderFlatQueryDtos.stream()
                .collect(groupingBy(orderFlatQueryDto ->
                                new FindOrderQueryDto(orderFlatQueryDto.getOrderId(), orderFlatQueryDto.getName(), orderFlatQueryDto.getOrderDate(),
                                        orderFlatQueryDto.getOrderStatus(), orderFlatQueryDto.getAddress()),
                        Collectors.mapping(orderFlatQueryDto ->
                                new OrderItemQueryDto(orderFlatQueryDto.getOrderId(), orderFlatQueryDto.getItemName(), orderFlatQueryDto.getOrderPrice(),
                                        orderFlatQueryDto.getCount()), Collectors.toList())));
        return findOrderQueryDtoMap.entrySet().stream()
                .map(entry -> new FindOrderQueryDto(entry.getKey().getOrderId(), entry.getKey().getName(), entry.getKey().getOrderDate(),
                        entry.getKey().getOrderStatus(), entry.getKey().getAddress(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<OrderFlatQueryDto> findOrderFlatQueryDtos() {
        return entityManager.createQuery(
                "select new jpabook.jpashop.repository.query.OrderFlatQueryDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatQueryDto.class
        ).getResultList();
    }

    public List<FindOrderQueryDto> findOrdersQueryDtos() {
        return entityManager.createQuery(
                        "select new jpabook.jpashop.repository.query.FindOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                                " join o.member m" +
                                " join o.delivery d", FindOrderQueryDto.class)
                .getResultList();
    }

    public List<OrderItemQueryDto> findOrderItemQueryDtos(Long orderId) {
        return entityManager.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, i.name, i.price, i.stockQuantity)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<Long> findOrderQueryDtosToOrderIds(List<FindOrderQueryDto> findOrderQueryDtos) {
        return findOrderQueryDtos.stream()
                .map(FindOrderQueryDto::getOrderId)
                .collect(Collectors.toList());
    }

    private List<OrderItemQueryDto> findOptimizeOrderItemQueryDtos(List<Long> orderIds) {
        return entityManager.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, i.name, i.price, i.stockQuantity)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class
                ).setParameter("orderIds", orderIds)
                .getResultList();
    }

    private Map<Long, List<OrderItemQueryDto>> createOrderItemQueryDtoMap(List<OrderItemQueryDto> orderItemQueryDtos) {
        return orderItemQueryDtos.stream()
                .collect(groupingBy(OrderItemQueryDto::getOrderId));
    }
}
