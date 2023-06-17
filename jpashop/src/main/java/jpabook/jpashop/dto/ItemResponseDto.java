package jpabook.jpashop.dto;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    @Builder
    public ItemResponseDto(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }

    public static ItemResponseDto of(Item item) {
        if (item instanceof Book) {
            Book book = (Book) item;
            return ItemResponseDto.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .price(book.getPrice())
                    .stockQuantity(book.getStockQuantity())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .build();
        }
        throw new IllegalArgumentException("등록되지 않은 상품 종류입니다.");
    }
}
