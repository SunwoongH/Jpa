package jpabook.jpashop.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Book extends Item {
    private String author;
    private String isbn;

    @Builder
    public Book(String author, String isbn) {
        this.author = author;
        this.isbn = isbn;
    }
}
