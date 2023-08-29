package com.mpt.goodsservice.dao;

import com.mpt.goodsservice.config.JdbcUtil;
import com.mpt.goodsservice.domain.Goods;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class GoodsDao {
    private final JdbcUtil jdbcUtil;
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;
    private final int PAGE_LIMIT = 40;

    public GoodsDao(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public Goods getGoodsById(int id) {
        Goods goods = null;

        try {
            conn = jdbcUtil.getConnection();
            String sql = " select * from goods where id=?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            rs = psmt.executeQuery();
            if (rs.next()) {
                goods = new Goods(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("del_price"),
                        rs.getInt("price"),
                        rs.getDouble("rating"),
                        rs.getInt("rating_count"),
                        rs.getString("img"),
                        rs.getString("url"));
            }
        } catch (Exception e) {
            goods = Goods.builder()
                        .id(-1)
                        .name("none")
                        .build();
        } finally {
            jdbcUtil.close(rs, psmt, conn);
        }
        return goods;
    }

    public ArrayList<Goods> getGoods(HashMap<String, String> param) {
        Goods goods = null;
        ArrayList<Goods> list = new ArrayList<>();
        String sql = null;
        try {
            conn = jdbcUtil.getConnection();
            if (param.get("page").equals("-1")) {
                sql = "select * from goods ";
            }
            else if (param.get("saleType").equals("priceComparison")) { // 정가 대비 할인율 순
                sql = "select * from goods order by ((del_price - price)/del_price) desc LIMIT ?, ?";
            } else {
                sql = "select g.* from goods g left join price_information p on g.id = p.id order by p.drop_rate asc LIMIT ?, ?";
            }

            int pageNumber = Integer.parseInt(param.get("page"));
            int offset = (pageNumber - 1) * PAGE_LIMIT;

            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, offset);
            psmt.setInt(2, PAGE_LIMIT);

            rs = psmt.executeQuery();
            while (rs.next()) {
                goods = new Goods(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("del_price"),
                        rs.getInt("price"),
                        rs.getDouble("rating"),
                        rs.getInt("rating_count"),
                        rs.getString("img"),
                        rs.getString("url"));
                list.add(goods);
            }
        } catch (Exception e) {
            // return empty list
        } finally {
            jdbcUtil.close(rs, psmt, conn);
        }
        return list;
    }
}