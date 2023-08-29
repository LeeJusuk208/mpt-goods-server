package com.mpt.goodsservice.dto;

import com.mpt.goodsservice.domain.Goods;

import lombok.Getter;

@Getter
public class GoodsDto {
    private int id;
    private String name;
    private int del_price;
    private int price;
    private double rating;
    private int rating_count;
    private String img;
    private String url;

    public GoodsDto(Goods goods){
        this.id = goods.getId();
        this.name = goods.getName();
        this.del_price = goods.getDel_price();
        this.price = goods.getPrice();
        this.rating = goods.getRating();
        this.rating_count = goods.getRating_count();
        this.img = goods.getImg();
        this.url = goods.getUrl();
    }
}
