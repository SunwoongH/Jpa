package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
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
    MemberRepository memberRepository;

    @DisplayName("회원가입을 한다.")
    @Test
    void joinTest() {
        // given
        Member member = Member.builder()
                .name("joy")
                .address(Address.builder()
                        .city("a")
                        .street("b")
                        .zipcode("c")
                        .build())
                .build();

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @DisplayName("기존 회원 이름과 회원가입 하려는 이름의 중복을 확인한다.")
    @Test
    void validateDuplicateMemberTest() {
        // given
        Member memberA = Member.builder()
                .name("joy")
                .address(Address.builder()
                        .city("a")
                        .street("b")
                        .zipcode("c")
                        .build())
                .build();
        Member memberB = Member.builder()
                .name("joy")
                .address(Address.builder()
                        .city("a")
                        .street("b")
                        .zipcode("c")
                        .build())
                .build();

        // when
        memberService.join(memberA);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(memberB));
    }
}