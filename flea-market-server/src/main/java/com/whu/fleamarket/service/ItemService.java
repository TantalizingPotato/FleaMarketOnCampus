package com.whu.fleamarket.service;

import com.whu.fleamarket.entity.Item;

import java.util.List;

public interface ItemService {


    public List<Item> findAll();

    public List<Item> findAllOnShelf();

    public Boolean add(Item item);

    public Boolean update(Item item);

    public Item queryById(Integer id);

    public List<Item> queryByOwnerId(Integer ownerId);

    public List<Item> queryByItemName(String itemName);

    public Boolean deleteById(Integer id);

}
