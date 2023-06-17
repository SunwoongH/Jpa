package jpabook.jpashop.controller;

import jpabook.jpashop.dto.ItemResponseDto;
import jpabook.jpashop.dto.SaveItemDto;
import jpabook.jpashop.dto.UpdateItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BookForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    @Builder
    public BookForm(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }

    public SaveItemDto toSaveItemDto() {
        return SaveItemDto.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .author(author)
                .isbn(isbn)
                .build();
    }

    public UpdateItemDto toUpdateItemDto() {
        return UpdateItemDto.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

    public static BookForm of(ItemResponseDto itemResponseDto) {
        return BookForm.builder()
                .id(itemResponseDto.getId())
                .name(itemResponseDto.getName())
                .price(itemResponseDto.getPrice())
                .stockQuantity(itemResponseDto.getStockQuantity())
                .author(itemResponseDto.getAuthor())
                .isbn(itemResponseDto.getIsbn())
                .build();
    }
}
