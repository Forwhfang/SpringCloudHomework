### 1. 配置

- 数据库：book
  - 数据库表：tb_book
- 端口号：
  - eureka：8001
  - config：8002
  - gateway：8003
  - order：8004

### 2. 启动项目

1. 在Navicat新建一个数据库book
2. 把`library-config\src\main\resources\config\library-order-dev.yml`中的数据库用户名和密码改成自己的设置
3. Application启动顺序：

- eureka👉config👉gateway👉order

4. 启动完成之后：

- 测试数据库：打开Navicat看看有没有新建表tb_book

- 测试接口：<http://localhost:8003/orderservice/hello>：输出hello world!

- 测试CURD：

  > 使用postman（如果没有postman可以用chrome console）

  - 新建：

    ```
    [post]
    url: 
    	http://127.0.0.1:8003/orderservice/save
    body:
    	{
            "goodsName":"《西游记》",
            "address":"广东省",
            "name":"张大山",
            "phone":"13302735200",
            "count":10,
            "money":100
        }
    ```

    - send之后到Navicat看看是否新增成功

  - 修改

    ```
    [put]
    url: 
    	http://127.0.0.1:8003/orderservice/update
    body:
    {
    	"id":1,
    	"goodsName":"《红楼梦》",
    	"address":"广东省",
    	"name":"张大山",
    	"phone":"13302735200",
    	"count":10,
    	"money":100
    }
    ```

    - send之后到Navicat看看是否修改成功

  - 查询

    - 查询所有文档

    ```
    [get]
    url: 
    	http://127.0.0.1:8003/orderservice/findAll
    ```

    - 根据id进行查询

    ```
    [get]
    url: 
    	http://127.0.0.1:8003/orderservice/findById/1
    ```

  - 删除

    ```
    [delete]
    url: 
    	http://127.0.0.1:8003/orderservice/delete/1
    ```

    - send之后到Navicat看看是否删除成功

### 3. 文件目录说明

- 数据库配置：

  - `library-config\src\main\resources\config`

- 数据元：

  - BookVO、BookForm:

    `library-order\src\main\java\com\library\order\dto`

- 增删查改操作：

  - `library-order\src\main\java\com\library\order\service\BookServiceImpl.java`