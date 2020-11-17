package com.whu.fleamarket.service.impl;

import com.whu.fleamarket.dao.ItemMapper;
import com.whu.fleamarket.entity.Item;
import com.whu.fleamarket.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;


    @Override
    public List<Item> findAll() {
        return itemMapper.findAll();
    }

    @Override
    public List<Item> findAllOnShelf() {
        return itemMapper.findAllOnShelf();
    }

    @Override
    public Boolean add(Item item) {
        return itemMapper.add(item) > 0;
    }

    @Override
    public Boolean update(Item item) {
        return itemMapper.update(item) > 0;
    }

    @Override
    public Item queryById(Integer id) {
        return itemMapper.queryById(id);
    }

    @Override
    public List<Item> queryByOwnerId(Integer ownerId) {
        return itemMapper.queryByOwnerId(ownerId);
    }

    @Override
    public List<Item> queryByItemName(String itemName) {
        return itemMapper.queryByItemName(itemName);
    }

    @Override
    public Boolean deleteById(Integer id) {
        return itemMapper.deleteById(id) > 0;
    }
}
