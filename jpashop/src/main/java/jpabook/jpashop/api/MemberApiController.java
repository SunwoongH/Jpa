package jpabook.jpashop.api;

import jpabook.jpashop.dto.ApiResponse;
import jpabook.jpashop.dto.request.JoinMemberRequestDto;
import jpabook.jpashop.dto.request.UpdateMemberRequestDto;
import jpabook.jpashop.dto.response.FindMemberResponseDto;
import jpabook.jpashop.dto.response.JoinMemberResponseDto;
import jpabook.jpashop.dto.response.UpdateMemberResponseDto;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping
    public ApiResponse<JoinMemberResponseDto> joinMember(@RequestBody @Valid final JoinMemberRequestDto joinMemberDto) {
        return ApiResponse.of(memberService.join(joinMemberDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<UpdateMemberResponseDto> updateMember(@PathVariable final Long id,
                                                             @RequestBody @Valid final UpdateMemberRequestDto updateMemberDto) {
        return ApiResponse.of(memberService.update(id, updateMemberDto));
    }

    @GetMapping
    public ApiResponse<List<FindMemberResponseDto>> findMembers() {
        return ApiResponse.of(memberService.findMembers());
    }
}
