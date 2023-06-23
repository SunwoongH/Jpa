package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * Sample Data Init Class
 */
@RequiredArgsConstructor
@Component
public class InitDatabase {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.createTestDataA();
        initService.createTestDataB();
    }

    @RequiredArgsConstructor
    @Transactional
    @Component
    static class InitService {
        private final EntityManager entityManager;

        public void createTestDataA() {
            Member member = createMember("userA", createAddress("서울", "1", "1111"));
            entityManager.persist(member);
            Book bookA = createBook("JPA1", 10000, 100, "authorA", "isbnA");
            entityManager.persist(bookA);
            Book bookB = createBook("JPA2", 20000, 100, "authorB", "isbnB");
            entityManager.persist(bookB);
            OrderItem orderItemA = OrderItem.createOrderItem(bookA, 10000, 1);
            OrderItem orderItemB = OrderItem.createOrderItem(bookB, 20000, 2);
            Delivery delivery = createDelivery(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItemA, orderItemB);
            entityManager.persist(order);
        }

        public void createTestDataB() {
            Member member = createMember("userB", createAddress("부산", "2", "2222"));
            entityManager.persist(member);
            Book bookA = createBook("Spring1", 30000, 200, "authorA", "isbnA");
            entityManager.persist(bookA);
            Book bookB = createBook("Spring2", 40000, 200, "authorB", "isbnB");
            entityManager.persist(bookB);
            OrderItem orderItemA = OrderItem.createOrderItem(bookA, 30000, 3);
            OrderItem orderItemB = OrderItem.createOrderItem(bookB, 40000, 4);
            Delivery delivery = createDelivery(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItemA, orderItemB);
            entityManager.persist(order);
        }

        private Member createMember(String name, Address address) {
            return Member.builder()
                    .name(name)
                    .address(address)
                    .build();
        }

        private Address createAddress(String city, String street, String zipcode) {
            return Address.builder()
                    .city(city)
                    .street(street)
                    .zipcode(zipcode)
                    .build();
        }

        private Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
            Book book = Book.builder()
                    .author(author)
                    .isbn(isbn)
                    .build();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Delivery createDelivery(Address address) {
            Delivery delivery = new Delivery();
            delivery.setAddress(address);
            return delivery;
        }
    }
}
