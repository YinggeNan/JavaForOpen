#### 1.jsonpath的特点
1. 不需要定义java bean、不用对多层map多次迭代，就可以获得json解析树中深层次的节点
2. Jayway的jsonpath解析需要把要解析的json一次加载进内存?
##### 2.1 jsonpath表达式的两种方式
``` 
{
    "tool": 
    {
        "jsonpath": 
        {
            "creator": 
            {
                "name": "Jayway Inc.",
                "location": 
                [
                    "Malmo",
                    "San Francisco",
                    "Helsingborg"
                ]
            }
        }
    },
    "book": 
    [
        {
            "title": "Beginning JSON",
            "price": 49.99
        },

        {
            "title": "JSON at Work",
            "price": 29.99
        }
    ]
}
```
下面两种方式都是获取里层的location节点的第三个元素: 
1. 点号表达式: $.tool.jsonpath.creator.location[2]
2. 中括号表达式: $['tool']['jsonpath']['creator']['location'][2]
##### 2.2 jsonpath的三个符号
1. $: 表示json结构的根节点,可以表示一个对象(object)、数组(array)
2. @: 表示当前正在处理的节点,常作为谓词表达式的一部分,假设我们正在处理book数组节点,那么
    book[?(@.price == 49.99)]指的是book数组内的第一个节点
3. *: 表示指定范围内所有元素,是一个通配符,比如book[*]表示book数组内部所有节点

##### 2.3.1 函数
支持在pattern中使用: min(), max(), avg(), stddev() and length().
##### 2.3.1 过滤器
就是布尔表达式来确定那些node可以作为结果返回: 
1. 相等: ==
2. 正则匹配: =~
3. 包含: in 

过滤器在code中的三种形式: 
1. 使用预定义的Filter api
2. 实现 Predicate 接口,实现自定义filter
3. 在pattern里内联filter逻辑,不创建filter对象
##### 2.4 jsonpath的输入
input通过重载(overload)JsonPath.parse()支持String、Object、InputStream、File、URL对象
##### 2.5 修改默认设置
1. Option.AS_PATH_LIST : 返回节点的path而不是返回值
2. Option.DEFAULT_PATH_LEAF_TO_NULL:所有没有的节点返回null  
...
##### 2.6 SPI
1. JsonProvider SPI: lets us change the ways JsonPath parses and handles JSON documents.  
2. MappingProvider SPI: allows for customization of bindings between node values and returned object types.   
3. CacheProvider SPI:  adjusts the manners that paths are cached, which can help to increase performance.   
#### 3.json解析的一些方式：
1. json->bean: 需要预先定义java bean
2. json->Map: 较为深层次的节点需要多次迭代
3. jsonpath: 使用复杂的jsonpath表达式来直接获取任意层次节点

#### 4.适用场景
1. json->bean: 适合json作为配置文件时，配置文件解析首先考虑可读性，所以优选定义java bean（java bean可读性高）
2. json->Map: 

#### 5.支持几种解析方式的库?

#### 6.库功能支持对比、解析效率对比?