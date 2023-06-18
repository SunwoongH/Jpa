package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.ItemResponseDto;
import jpabook.jpashop.dto.SaveItemDto;
import jpabook.jpashop.dto.UpdateItemDto;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public void saveItem(SaveItemDto saveItemDto) {
        itemRepository.save(saveItemDto.toBookEntity());
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void updateItem(Long id, UpdateItemDto updateItemDto) {
        Item item = itemRepository.findOne(id);
        item.update(updateItemDto.getName(), updateItemDto.getPrice(), updateItemDto.getStockQuantity());
    }

    /**
     * 상품 조회
     */
    public ItemResponseDto findOne(Long id) {
        return ItemResponseDto.of(itemRepository.findOne(id));
    }

    /**
     * 상품 전체 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
