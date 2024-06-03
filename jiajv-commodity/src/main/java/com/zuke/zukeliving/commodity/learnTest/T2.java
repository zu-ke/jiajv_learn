package com.zuke.zukeliving.commodity.learnTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//流式计算：将集合转成map
public class T2 {
    public static void main(String[] args) {
        //先构建一个集合
        ArrayList<stu> stus = new ArrayList<>();
        stus.add(new stu(100, "zk1", "中国1", "game1"));
        stus.add(new stu(200, "zk2", "中国2", "game2"));
        stus.add(new stu(300, "zk3", "中国3", "game3"));
        stus.add(new stu(400, "zk4", "中国4", "game4"));

        //需求
        //将stu -> map k-string v-List<stu2>
        //stu(100,"zk1","中国1","game1") -> 100 -> 两个stu2【("zk1","game1"),("zk1-克隆","game1")】
        Map<String, List<stu2>> maps = stus.stream().collect(Collectors.toMap(k -> {
            return k.getId().toString();
        }, v -> {
            ArrayList<stu2> list = new ArrayList<>();
            list.add(new stu2(v.getName(), v.getHobby()));
            list.add(new stu2(v.getName() + "-克隆", v.getHobby()));
            return list;
        }));
        System.out.println(maps);
    }

}

class stu {
    private Integer id;
    private String name;
    private String address;
    private String hobby;

    @Override
    public String toString() {
        return "stu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public stu(Integer id, String name, String address, String hobby) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.hobby = hobby;
    }

    public stu() {
    }
}

class stu2 {
    private String name;
    private String hobby;

    @Override
    public String toString() {
        return "stu2{" +
                "name='" + name + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public stu2(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }

    public stu2() {
    }
}
