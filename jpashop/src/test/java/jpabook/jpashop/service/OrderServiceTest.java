package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class OrderServiceTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @DisplayName("상품을 주문한다.")
    @Test
    void orderTest() {
        // given
        Member member = createMember();
        entityManager.persist(member);
        Book book = createBook();
        entityManager.persist(book);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, findOrder.getStatus());
        assertEquals(1, findOrder.getOrderItems().size());
        assertEquals(book.getPrice() * orderCount, findOrder.getTotalPrice());
        assertEquals(10 - orderCount, book.getStockQuantity());
    }

    @DisplayName("상품 주문 시 재고 수량이 초과되면 예외가 발생한다.")
    @Test
    void itemStockQuantityExceedTest() {
        // given
        Member member = createMember();
        entityManager.persist(member);
        Book book = createBook();
        entityManager.persist(book);
        int orderCount = 11;

        // when
        Assertions.assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    @DisplayName("상품 주문을 취소한다.")
    @Test
    void orderCancelTest() {
        // given
        Member member = createMember();
        entityManager.persist(member);
        Book book = createBook();
        entityManager.persist(book);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, findOrder.getStatus());
        assertEquals(10, book.getStockQuantity());
    }

    private Member createMember() {
        return Member.builder()
                .name("joy")
                .address(Address.builder()
                        .city("a")
                        .street("b")
                        .zipcode("c")
                        .build())
                .build();
    }

    private Book createBook() {
        Book book = Book.builder()
                .author("author")
                .isbn("isbn")
                .build();
        book.setName("name");
        book.setPrice(10000);
        book.setStockQuantity(10);
        return book;
    }
}