#### [插件配置参考](https://maven.apache.org/guides/mini/guide-configuring-plugins.html)

#### 1.插件的基本概念
1. maven的两种插件
    1. Build plugins: <build/>元素中配置
    2. Reporting plugins:  <reporting/>元素中配置
2. 每个插件都必备的元素:
    1. groupId
    2. artifactId
    3. version
3. 始终定义每个插件的版本, 用于保证构建可重复性
4. 插件版本定义可在父POM中的<build><pluginManagement/></build>、<reporting><plugins/></reporting>中定义
5. 插件通过在<configuration>元素中配置, 注意<configuration> 元素的子元素映射到 Mojo 中的字段或setter,记住一个插件由一个或多个 Mojo 组成,
   一个 Mojo 映射到一个goal
#### 2.插件的 Mojo例子
假如有一个插件有一个Mojo, 在一个timeout内对一个URL执行query, 并且有一些选项
```
@Mojo( name = "query" )
public class MyQueryMojo
    extends AbstractMojo
{
    @Parameter(property = "query.url", required = true)
    private String url;
 
    @Parameter(property = "timeout", required = false, defaultValue = "50")
    private int timeout;
 
    @Parameter(property = "options")
    private String[] options;
 
    public void execute()
        throws MojoExecutionException
    {
        ...
    }
}
```
在pom.xml里对这个Mojo配置的例子如下
``` 
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <artifactId>maven-myquery-plugin</artifactId>
        <version>1.0</version>
        <configuration>
          <url>http://www.foobar.com/query</url>
          <timeout>10</timeout>
          <options>
            <option>one</option>
            <option>two</option>
            <option>three</option>
          </options>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ...
</project>
```
##### 2.1 pom.xml -> Mojo:
1. configuration的子元素的名字要和Mojo的字段名直接匹配(一样),比如这里,
   url元素映射到url字段，timeout元素映射到timeout字段，options元素映射到options字段
2. 映射机制可以映射数组类型, 通过检查字段的类型并确定是否可以进行合适的映射
##### 2.2 支持从命令行执行的Mojo
1. 提供一种通过系统属性而不是 POM 中的 <configuration> 部分进行配置mojo的参数
2. 在Mojo类中对字段属性使用@Parameter说明该字段映射到命令行的参数名字,
   比如在上面的 Mojo 中，参数 url 与表达式 ${query.url} 相关联，这意味着它的值可以由系统属性 query.url 指定
3. 系统属性的名称不一定与 mojo 参数的名称匹配,但是惯例是一样, 但是一般为系统属性加上前缀, 以避免与其他系统属性发生名称冲突
```
mvn myquery:query -Dquery.url=http://maven.apache.org
```
#### 3.参数值和Mojo的字段的转化
一般默认使用Java的原语类型和包装类的转化

| Parameter Class | Conversion from String                                                                                                                                                |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Boolean         | Boolean.valueOf(String)                                                                                                                                               |
| Byte            | Byte.decode(String)                                                                                                                                                   |
| Character       | Character.valueOf(char)                                                                                                                                               |
| Class           | 	Class.forName(String)                                                                                                                                                |
| java.util.Date  | SimpleDateFormat.parse(String) for the following patterns: <br/>yyyy-MM-dd hh:mm:ss.s a, <br/>yyyy-MM-dd hh:mm:ssa, <br/>yyyy-MM-dd HH:mm:ss.s or yyyy-MM-dd HH:mm:ss |
| Double          | Double.valueOf(String)                                                                                                                                                |
| Enum            | 	Enum.valueOf(String)                                                                                                                                                 |
| java.io.File    | new File(String)                                                                                                                                                      |
| Float           | new File(String)                                                                                                                                                      |
| Integer         | Float.valueOf(String)                                                                                                                                                 |
| Long            | Long.decode(String)                                                                                                                                                   |
| Short           | Short.decode(String)                                                                                                                                                  |
| StringBuffer    | new StringBuffer(String)                                                                                                                                              |
| StringBuilder   | 	new StringBuilder(String)                                                                                                                                            |
| java.net.URL    | new URL(String)                                                                                                                                                       |

