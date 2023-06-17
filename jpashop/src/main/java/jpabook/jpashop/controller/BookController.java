package jpabook.jpashop.controller;

import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/items")
@Controller
public class BookController {
    private final ItemService itemService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("itemForm", new BookForm());
        return "items/create-item-form";
    }

    @PostMapping("/new")
    public String create(BookForm bookForm) {
        itemService.saveItem(bookForm.toSaveItemDto());
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "items/item-list";
    }

    @GetMapping("/{itemId}/edit")
    public String updateForm(@PathVariable Long itemId, Model model) {
        model.addAttribute("itemForm", BookForm.of(itemService.findOne(itemId)));
        return "items/update-item-form";
    }

    @PostMapping("/{itemId}/edit")
    public String update(@PathVariable Long itemId, BookForm bookForm) {
        itemService.updateItem(itemId, bookForm.toUpdateItemDto());
        return "redirect:/items";
    }
}
