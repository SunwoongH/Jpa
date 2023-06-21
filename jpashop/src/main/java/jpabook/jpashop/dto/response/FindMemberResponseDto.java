package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindMemberResponseDto {
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public static FindMemberResponseDto of(Member member) {
        return FindMemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .build();
    }
}
