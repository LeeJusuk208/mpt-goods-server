package com.mpt.goodsservice.controller;

import com.mpt.goodsservice.service.AuthService;
import com.mpt.goodsservice.service.GoodsService;
import com.mpt.goodsservice.config.UrlConfig;
import com.mpt.goodsservice.dao.GoodsDao;
import com.mpt.goodsservice.domain.Chart;
import com.mpt.goodsservice.domain.Goods;
import com.mpt.goodsservice.dto.ChartDto;
import com.mpt.goodsservice.dto.GoodsDto;
import com.mpt.goodsservice.dto.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {    
    @Autowired
    GoodsService goodsService;

    @Autowired
    AuthService authService;

    @GetMapping("/{id}/chart")
    public ResponseEntity<List<List<Long>>> getChartData(@PathVariable("id") int id, @RequestHeader HttpHeaders httpHeaders) {
        UserResponse userResponse;
        List<List<Long>> list;

        try {
            userResponse = authService.getAuthResponse(httpHeaders);

            if (userResponse.getStatus().equals("Verify Success :Token Refreshed")) {
                list = goodsService.getChartData(id);
            } else {
                // System.out.println("Verify Failed");
                return (ResponseEntity<List<List<Long>>>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return (ResponseEntity<List<List<Long>>>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(list);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GoodsDto> goodsDetail(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .body(new GoodsDto(goodsService.goodsDetail(id)));
    }

    @GetMapping("/")
    public ResponseEntity<List<GoodsDto>> goodsList(@RequestParam(required = false) HashMap<String, String> param) {
        List<Goods> list = goodsService.goodsList(param);
        List<GoodsDto> result = new ArrayList<>();

        for(Goods goods : list) {
            result.add(new GoodsDto(goods));
        }

        return ResponseEntity.ok().body(result);
    }
}