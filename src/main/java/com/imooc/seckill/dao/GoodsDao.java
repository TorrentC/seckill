package com.imooc.seckill.dao;

import com.imooc.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date from goods g, miaosha_goods mg where mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date from goods g, miaosha_goods mg where mg.goods_id = g.id and g.id = #{id}")
    GoodsVo listGoodsVoById(@Param("id") Long id);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsVo.id} and stock_count > 0")
    void reduceStock(@Param("goodsVo") GoodsVo goodsVo);
}
