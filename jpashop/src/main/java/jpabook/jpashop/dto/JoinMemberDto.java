package jpabook.jpashop.dto;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinMemberDto {
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @Builder
    public JoinMemberDto(String name, String city, String street, String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .address(Address.builder()
                        .city(city)
                        .street(street)
                        .zipcode(zipcode)
                        .build())
                .build();
    }
}
