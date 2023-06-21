package jpabook.jpashop.dto.request;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JoinMemberRequestDto {
    @NotEmpty
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @Builder
    public JoinMemberRequestDto(String name, String city, String street, String zipcode) {
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
