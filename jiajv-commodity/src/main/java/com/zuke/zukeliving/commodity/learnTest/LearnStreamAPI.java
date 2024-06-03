package com.zuke.zukeliving.commodity.learnTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LearnStreamAPI {
    public static void main(String[] args) {
        // 创建Person实例
        Person person1 = new Person(1, "a", 22);
        Person person2 = new Person(2, "b", 34);
        Person person3 = new Person(3, "c", 10);
        Person person4 = new Person(4, "d", 110);
        Person person5 = new Person(5, "e", 9);

        // 放入到list集合
        List<Person> list = Arrays.asList(person1, person2, person3, person4, person5);
        //System.out.println(list);

        //分割线条，测试上面代码时请注释下面这段代码

        // 需求：从list中过滤出person.id % 2 != 0 的对象
        // 1. 把list转成流对象，目的是为了使用流的各个方法
        // 2. filter() 传入的是Predicate，返回boolean值
        // 3. collect() 传入Collector，将数据收集到集合
        // 4. map操作：希望给过滤得到的person对象加入cat对象，原list中的数据也会改变
        // 5. sorted() 传入Comparator（object1, object2）
        List<Person> list2 = list.stream().filter(person -> {
            return person.getId() % 2 != 0;
        }).map(person -> {
            Cat cat = new Cat(person.getId() + 100, "小花猫", "橘色");
            person.setCat(cat);
            return person;
        }).sorted((p1, p2) -> {
            return p1.getId() - p2.getId(); // 按照id升序排列
            //return p2.getId() - p1.getId(); // 按照id降序排列
            //return p2.getAge() - p1.getAge(); // 按照年龄从大到小排列
        }).collect(Collectors.toList());
        System.out.println("list2==>" + list2);
        System.out.println("list==>" + list);

        //分割线条，测试上面代码时请注释上面这段代码

        // 其他常用的stream API
        // limit使用
        // 需求：要显示list集合的前两个数据
        list.stream().limit(2).forEach(person -> {
            System.out.println(person);
        });

        // count使用
        long count = list.stream().count();
        System.out.println(count);

        // 先过滤再counter
        long count1 = list.stream().filter(person -> {
            return person.getAge() > 10;
        }).count();
        System.out.println(count1);
    }
}

// Person类
class Person {
    private Integer id;
    private String name;
    private Integer age;
    private Cat cat;

    public Person(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", cat=" + cat +
                '}';
    }
}

// Cat类
class Cat {
    private Integer id;
    private String name;
    private String color;

    public Cat(Integer id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
