package com.mpt.goodsservice.dto;

import com.mpt.goodsservice.domain.Chart;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ChartDto {
    private long date;
    private int price;

    public ChartDto(Chart chart) {
        this.date = chart.getDate().getTime();
        this.price = chart.getPrice();
    }

    public static List<Long> jsonList(Chart chart) {
        List<Long> list = new LinkedList<>();
        list.add(chart.getDate().getTime() + 9 * 60 * 60 * 1000);
        list.add((long) chart.getPrice());
        return list;
    }

}