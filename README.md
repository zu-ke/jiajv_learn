# 家居项目

> 基于分布式微服务的产品发布解决方案落地实现

> (SpringBoot + Spring Cloud + Spring Cloud Alibaba + Vue + ElementUI + MyBatis-Plus + MySQL + Git + Maven + Linux + Nginx + Docker 等，前后端分离)

## 技术栈

### 前端
* **技术**:
  HTML, CSS, JavaScript, Vue, ElementUI, Axios, jQuery, Node.js

### 后端
* **框架**:
  Spring Boot, Spring Cloud, MyBatis-Plus

### 数据库
* **数据库**:
  MySQL

### 项目管理
* **工具**:
  Git, Maven

### 中间件
* **Alibaba/Nacos**:
  注册中心, 配置中心
* **Gateway**:
  网关服务
* **Alibaba/云存储 OOS**

### 快速开发技术
* Renren-fast

### 其他
* **工具**:
  Linux(Centos7), Docker, VirtualBox6, Vagrant, ApiPost, Vmware17, nginx(反向代理, 负载均衡, 动静分离)

  

## 简单梳理

1. **虚拟机创建**:
    - 使用 VirtualBox 创建 CentOS 虚拟机。

2. **Docker 安装**:
    - 在 Docker 中安装 MySQL 数据库和 Nginx。

3. **项目管理**:
    - 使用 GitHub 进行项目管理。

4. **基础框架搭建**:
    - 使用 Renren-fast 和 Renren-fast-vue 开源项目搭建基础框架，借助 Renren-generator 快速开发。

5. **数据处理**:
    - 主要使用 Java8 新特性 Stream 流进行数据处理。

6. **前端开发**:
    - 使用 ElementUI 组件库进行前端页面搭建。

7. **文件上传**:
    - 使用阿里巴巴 OSS 进行文件上传。

8. **服务注册与配置**:
    - 使用 Nacos 作为服务注册和发现中心，以及配置中心。

9. **网关服务**:
    - 使用 Gateway 提供网关服务。

10. **表单校验**:
    - 实现前端表单校验和后端表单校验（JSR303），同时针对integer数据类型的属性，使用自定义校验器。

11. **全局异常处理**:
    - 实现全局异常处理。

12. **跨域处理**:
    - 处理跨域问题。

13. **分页插件**:
    - 使用 MyBatis-Plus 分页插件。

14. **统一响应状态码**:
    - 实现统一的响应状态码。

15. **解决方案**:
    - 在涉及SKU和SPU部分，前后端JSON数据交互较为复杂，采用设计vo的方式解决。
    - 处理一些比较复杂的数据时，采用stream流事半功倍。
    - ......

16. **后端架构**:
    - 采用经典的三层架构。

17. **Nginx 配置**:
    - 使用 Nginx 进行反向代理、负载均衡和动静分离。

## 部署
* 数据库创建hspliving_manage和hspliving_commodity，执行`./资料/`下面对应.sql文件。
* 安装且启动`./资料/`下的nacos服务
* 注册各个服务的端口，最好不要修改端口

## 感悟

整个项目的开发过程中，将前面学习的知识进行了融会贯通，
更重要的是学习了一些问题的经典解决方案。

