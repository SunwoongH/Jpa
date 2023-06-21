package jpabook.jpashop.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateMemberRequestDto {
    @NotEmpty
    private String name;
}
