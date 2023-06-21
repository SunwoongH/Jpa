package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.request.JoinMemberRequestDto;
import jpabook.jpashop.dto.request.UpdateMemberRequestDto;
import jpabook.jpashop.dto.response.FindMemberResponseDto;
import jpabook.jpashop.dto.response.JoinMemberResponseDto;
import jpabook.jpashop.dto.response.UpdateMemberResponseDto;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public JoinMemberResponseDto join(JoinMemberRequestDto joinMemberDto) {
        Member member = joinMemberDto.toEntity();
        validateDuplicateMember(member);
        memberRepository.save(member);
        return JoinMemberResponseDto.of(member);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public UpdateMemberResponseDto update(Long id, UpdateMemberRequestDto updateMemberDto) {
        Member member = memberRepository.findOne(id);
        member.setName(updateMemberDto.getName());
        return UpdateMemberResponseDto.of(member);
    }

    /**
     * 회원 조회
     */
    public FindMemberResponseDto findMember(Long id) {
        return FindMemberResponseDto.of(memberRepository.findOne(id));
    }

    /**
     * 회원 전체 조회
     */
    public List<FindMemberResponseDto> findMembers() {
        return memberRepository.findAll()
                .stream()
                .map(FindMemberResponseDto::of)
                .collect(Collectors.toList());
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
