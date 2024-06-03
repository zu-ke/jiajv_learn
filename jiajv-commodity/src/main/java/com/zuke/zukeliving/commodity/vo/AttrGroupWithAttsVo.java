package com.zuke.zukeliving.commodity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.zuke.zukeliving.commodity.entity.AttrEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//������ص����ݣ��ǵ����Żص�ǰ��ʵ��������������ǰ������
//�������������vo�࣬����ǰ�����������ϣ������ֶΡ�ɾ���ֶΣ���̬������
//������ʾǰ����ҪAttrgroupEntity��AttrEntity��Ϸ���
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttrGroupWithAttsVo {
    /**
     * id
     */
    private Long id;
    /**
     * ����
     */
    private String name;
    /**
     * ����
     */
    private Integer sort;
    /**
     * ˵��
     */
    private String description;
    /**
     * ��ͼ��
     */
    private String icon;
    /**
     * �������� id
     */
    private Long categoryId;

    private List<AttrEntity> attrs;

}
