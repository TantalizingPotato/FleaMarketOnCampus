package com.whu.fleamarket.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Item implements Serializable {

    @ApiModelProperty(value = "商品ID" , hidden = true)
    private Integer id;
    @ApiModelProperty(value = "图片路径")
    private String picUrl;
    @ApiModelProperty(value = "商品名称")
    private String itemName;
    @ApiModelProperty(value = "价格")
    private String price;
    @ApiModelProperty(value = "是否在架上")
    private Boolean onShelf;
    @ApiModelProperty(value = "所属用户的ID")
    private Integer ownerId;
    @ApiModelProperty(value = "分类")
    private String category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getOnShelf() {
        return onShelf;
    }

    public void setOnShelf(Boolean onShelf) {
        this.onShelf = onShelf;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
