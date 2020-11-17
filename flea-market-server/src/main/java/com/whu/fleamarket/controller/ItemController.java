package com.whu.fleamarket.controller;

import com.whu.fleamarket.service.ItemService;
import com.whu.fleamarket.entity.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/item", produces = "application/json") //配置返回值 application/json
@Api(description = "商品管理")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @ApiOperation(value = "获取商品列表", notes = "获取所有商品信息")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public Object findAllItem() {

        List<Item> items =itemService.findAll();

        return items;
    }

    @ApiOperation(value = "获取在架上的商品列表", notes = "获取所有在架上的商品的信息")
    @RequestMapping(value = {"/ofOnShelf"}, method = RequestMethod.GET)
    public Object findAllOnShelfItem() {

        List<Item> items =itemService.findAllOnShelf();

        return items;
    }

    @ApiOperation(value="更新商品", notes="根据url指定的id和传来的user对象，更新数据库中相应的商品信息")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putItem(@ApiParam(value = "商品id", required = true) @PathVariable String id, @RequestBody Item item) {
        item.setId(Integer.parseInt(id));
        itemService.update(item);
        return "success";
    }


    @ApiOperation(value = "添加商品", notes = "根据Item对象向数据库中添加商品信息")
//    @ApiImplicitParam(name = "item", value = "商品详细实体item", required = true, dataType = "Item")
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String postItem(Item item) {

        itemService.add(item);

        return "success" ;
    }

    @ApiOperation(value="查找商品", notes="根据商品id获取商品详细信息")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Integer" , paramType ="path")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Item getItem(@PathVariable String id) {

        return itemService.queryById(Integer.parseInt(id));
    }

    @ApiOperation(value="使用物主id查找商品", notes="根据物主id，获取该用户拥有的所有商品")
    @ApiImplicitParam(name = "ownerId", value = "物主id", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value="/ofOwnerId/{ownerId}", method=RequestMethod.GET)
    public List<Item> getItemByOwnerId( @PathVariable String ownerId) {

        return itemService.queryByOwnerId(Integer.parseInt(ownerId));
    }

    @ApiOperation(value="使用商品名查找商品", notes="根据商品名查询商品详细信息，返回所有名称相符的商品")
    @RequestMapping(value="/ofItemName/{itemName}", method=RequestMethod.GET)
    public List<Item> getItemByItemName(@ApiParam(value = "商品名", required = true) @PathVariable String itemName) {

        return itemService.queryByItemName(itemName);
    }

    @ApiOperation(value="删除商品信息", notes="根据url中给定的id，删除数据库中相应的商品信息")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteItem(@PathVariable String id) {
        itemService.deleteById(Integer.parseInt(id));
        return "success";
    }


}
