package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateMemberResponseDto {
    private final Long id;
    private final String name;

    public static UpdateMemberResponseDto of(Member member) {
        return UpdateMemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }
}