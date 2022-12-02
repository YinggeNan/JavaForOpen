#### 1.Harmcrest测试框架demo例子
1. com/cdb/hamcrestTest/HamcrestDemo.java
2. 自定义matcher例子: com.cdb.hamcrestTest.CustomMatcher
#### 2.Harmcrest简介
1. 是一种断言(assertion)的书写测试风格, "测试可读性"是其最大风格,从代码可以很直接看到测试的目的
2. 可以集成到流行的多种测试框架中,比如Junit、testNG、Jmock1、Jmock2、EasyMock等
3. 主要使用静态方法assertThat()作为assertion的入口,以及丰富的Matcher来断言,matcher也是以静态方法的形式导入

#### 3.依赖
````
dependencies {
    testImplementation 'org.hamcrest:hamcrest:2.2'
}
````
#### 4.主要的matcher
参考[matcher list](https://hamcrest.org/JavaHamcrest/tutorial)  
##### 1. core matcher
1. anything()
2. describedAs()
3. is()
##### 2. logical matcher
1. allOf()
2. anyOf()
3. not()
##### 3. Object matcher
1. equalTo()
2. hasToString()
3. instanceOf()
4. notNullValue(), nullValue()
5. sameInstance()
##### 4. Beans matcher
1. hasProperty()
##### 5. Collections matcher
1. array()
2. hasEntry(), hasKey(), hasValue()
3. hasItem(), hasItems()
4. hasItemInArray()
##### 6. Number matcher
1. closeTo()
2. greaterThan(), greaterThanOrEqualTo(), lessThan(), lessThanOrEqualTo()
##### 7. Text matcher
1. equalToIgnoringCase()
2. equalToIgnoringWhiteSpace(), equalToCompressingWhiteSpace()
3. containsString(), endsWith(), startsWith()
##### 8. Custom matcher
1. 实现Matcher接口的方法,一般提供一个静态工厂方法来new matcher
2. 保证matcher的无状态性,使得使用单例模式时不会出问题