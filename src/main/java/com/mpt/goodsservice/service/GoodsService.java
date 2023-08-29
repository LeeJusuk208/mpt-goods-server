package com.mpt.goodsservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.mpt.goodsservice.config.UrlConfig;
import com.mpt.goodsservice.dao.ChartDao;
import com.mpt.goodsservice.dao.GoodsDao;
import com.mpt.goodsservice.domain.Chart;
import com.mpt.goodsservice.domain.Goods;
import com.mpt.goodsservice.dto.ChartDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsService {
    @Autowired
    private final GoodsDao goodsDao;

    @Autowired
    private final ChartDao chartDao;
    
    public List<List<Long>> getChartData(int id) {
        List<List<Long>> list = new LinkedList<>();

        List<Chart> chart = chartDao.getChartData(id);
        for (Chart ch : chart) {
            list.add(ChartDto.jsonList(ch));
        }
        return list;
    }

    public Goods goodsDetail(int id) {
        return goodsDao.getGoodsById(id);
    }

    public List<Goods> goodsList(HashMap<String, String> param) {
        return goodsDao.getGoods(param);
    }
}
