package jpabook.jpashop.service;

import jpabook.jpashop.dto.request.JoinMemberRequestDto;
import jpabook.jpashop.dto.response.JoinMemberResponseDto;
import jpabook.jpashop.repository.MemberOriginalJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional // 테스트 환경에서는 rollback 된다.
@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberOriginalJpaRepository memberRepository;

    @DisplayName("회원가입을 한다.")
    @Test
    void joinTest() {
        // given
        JoinMemberRequestDto joinMemberRequestDto = JoinMemberRequestDto.builder()
                .name("joy")
                .city("a")
                .street("b")
                .zipcode("c")
                .build();

        // when
        JoinMemberResponseDto joinMemberResponseDto = memberService.join(joinMemberRequestDto);

        // then
        assertEquals(joinMemberResponseDto.getId(), memberRepository.findOne(joinMemberResponseDto.getId()).getId());
    }

    @DisplayName("기존 회원 이름과 회원가입 하려는 이름의 중복을 확인한다.")
    @Test
    void validateDuplicateMemberTest() {
        // given
        JoinMemberRequestDto joinMemberRequestDtoA = JoinMemberRequestDto.builder()
                .name("joy")
                .city("a")
                .street("b")
                .zipcode("c")
                .build();
        JoinMemberRequestDto joinMemberRequestDtoB = JoinMemberRequestDto.builder()
                .name("joy")
                .city("a")
                .street("b")
                .zipcode("c")
                .build();

        // when
        memberService.join(joinMemberRequestDtoA);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(joinMemberRequestDtoB));
    }
}