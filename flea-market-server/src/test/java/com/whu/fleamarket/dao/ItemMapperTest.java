package com.whu.fleamarket.dao;

import com.whu.fleamarket.FirstSpringbootApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.whu.fleamarket.entity.Item;
import java.util.List;

class ItemMapperTest extends FirstSpringbootApplicationTests {
@Autowired
private ItemMapper itemMapper;
    @Test
    void findall() {
        List<Item> items =itemMapper.findAll();
        for(Item item : items){
            System.out.println(item);
        }
    }
}