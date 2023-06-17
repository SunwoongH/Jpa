package jpabook.jpashop.dto;

import jpabook.jpashop.domain.item.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveItemDto {
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    @Builder
    public SaveItemDto(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }

    public Book toBookEntity() {
        Book book = Book.builder()
                .author(author)
                .isbn(isbn)
                .build();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }
}
