package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinMemberResponseDto {
    private final Long id;

    public static JoinMemberResponseDto of(Member member) {
        return new JoinMemberResponseDto(member.getId());
    }
}
