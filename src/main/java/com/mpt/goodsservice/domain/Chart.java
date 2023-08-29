package com.mpt.goodsservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Chart {
    private int id;
    private int price;
    private int goods_rank;
    private Date date;

    @Builder
    public Chart(int id, int price, int goods_rank, Date date) {
        this.id = id;
        this.price = price;
        this.goods_rank = goods_rank;
        this.date = date;
    }

}