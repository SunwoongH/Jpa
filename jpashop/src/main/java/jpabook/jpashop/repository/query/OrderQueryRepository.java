package jpabook.jpashop.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<OrderItemQueryDto> findOrderItemQueryDtos(Long orderId) {
        return entityManager.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, i.name, i.price, i.stockQuantity)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id = :orders", OrderItemQueryDto.class)
                .setParameter("orders", orderId)
                .getResultList();
    }

    public List<FindOrderQueryDto> findOrdersQueryDtos() {
        return entityManager.createQuery(
                        "select new jpabook.jpashop.repository.query.FindOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                                " join o.member m" +
                                " join o.delivery d", FindOrderQueryDto.class)
                .getResultList();
    }
}
