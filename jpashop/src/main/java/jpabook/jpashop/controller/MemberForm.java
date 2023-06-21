package jpabook.jpashop.controller;

import jpabook.jpashop.dto.request.JoinMemberRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @Builder
    public MemberForm(String name, String city, String street, String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public JoinMemberRequestDto toJoinMemberDto() {
        return JoinMemberRequestDto.builder()
                .name(name)
                .city(city)
                .street(street)
                .zipcode(zipcode)
                .build();
    }
}
