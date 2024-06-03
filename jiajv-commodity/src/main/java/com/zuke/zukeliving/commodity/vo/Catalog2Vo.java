package com.zuke.zukeliving.commodity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalog2Vo {

    //以及父分类的id
    private String catalog1Id;

    //三级子分类
    private List<Catalog3Vo> catalog3List;
    private String id;
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Catalog3Vo{
        //父分类、二级分类id
        private String catalog2Id;
        private String id;
        private String name;
    }
}
