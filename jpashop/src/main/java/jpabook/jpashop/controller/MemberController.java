package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/create-member-form";
    }

    @PostMapping("new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/create-member-form";
        }
        Member member = Member.builder()
                .name(memberForm.getName())
                .address(Address.builder()
                        .city(memberForm.getCity())
                        .street(memberForm.getStreet())
                        .zipcode(memberForm.getZipcode())
                        .build())
                .build();
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "members/member-list";
    }
}
