package com.whu.fleamarket.dao;

import java.util.List;
import com.whu.fleamarket.entity.Item;
import org.springframework.stereotype.Repository;

//@Mapper
@Repository
public interface ItemMapper {

    public List<Item> findAll();

    public List<Item> findAllOnShelf();

    public Integer add(Item item);

    public Integer update(Item item);

    public Item queryById(Integer id);

    public List<Item> queryByOwnerId(Integer ownerId);

    public List<Item> queryByItemName(String itemName);

    public Integer deleteById(Integer id);

    public Integer deleteByOwnerId(String ownerId);
}
