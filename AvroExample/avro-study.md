#### 1.avro的schema
1. avro读取数据时用写入时的schema来读取
2. avro存储数据时,模式也随着存进去
3. avro用作RPC时，客户端和服务器在连接握手时交换模式，所以客户端和服务器都具有对方的完整模式
4. avro的schema用JSON定义的
#### 2.avro VS thrift、protoBuffer
1.Dynamic typing: Avro 不需要生成代码，允许在没有代码生成的情况下完全处理该数据，有助于构建通用数据处理系统和语言。
#### 3.avro maven依赖
1. code依赖
2. code生成plugin依赖
``` 
    <dependencies>
        <dependency>
            <groupId>org.apache.avro</groupId>
            <artifactId>avro</artifactId>
            <version>1.11.1</version>
        </dependency>
    </dependencies>
    <!--下面的plugin用于生成avro对应的java code-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.avro</groupId>
                <artifactId>avro-maven-plugin</artifactId>
                <version>1.11.1</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>schema</goal>
                        </goals>
                        <configuration>
                            // 放置avro schema的位置,默认位置是/src/main/avro
                            <sourceDirectory>${project.basedir}/src/main/resources/avro/</sourceDirectory>
                            // 定义输出代码的位置,默认位置是target/generated-sources/avro
                            <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
#### 4.定义avro的schema
1. 类型
    1. 原语类型(primitive types): null, boolean, int, long, float, double, bytes, and string
    2. 复杂类型(complex types): record, enum, array, map, union, and fixed
    3. 参考 [avro specification](https://avro.apache.org/docs/1.11.1/specification/)了解更多
2. 定义schema例子: user.avsc
   1. 一个schema文件只能包含一个schema定义
   2. 一个record类型至少要包含type("type": "record")、name(“name”: “User)、fields字段
   3. 这里的namespace和name一起指定了这个schema的完整名字(example.avro.User)
   4. Fields用对象数组定义, 每个对象都定义了一个name、type字段,而其他属性是可选的
   5. 一个field的type属性是另一个schema对象(原语类型、复杂类型),这里的User的name字段类型是原语类型string
   ,favorite_number和favorite_color都是union类型(用一个JSON数组表示),union是一个复杂类型,用其他类型构成的数组组成
   6. 比如favorite_number字段可以是string或null,使其可空
``` 
{"namespace": "example.avro",
 "type": "record",
 "name": "User",
 "fields": [
     {"name": "name", "type": "string"},
     {"name": "favorite_number",  "type": ["int", "null"]},
     {"name": "favorite_color", "type": ["string", "null"]}
 ]
} 
```
#### 5.代码生成
1. 切换到pom.xml目录,使用以下命令行:
```
mvn clean install
```
2. 使用IDEA maven插件
编辑配置->添加新配置->选择maven->选择工作目录,选择本地及其运行->输入命令: clean install->执行
#### 5.1 bean创建
1. 直接new 构造器方法
2. builder模式
3. builder VS 构造器创建
   1. builder会设置字段默认值
   2. builder会校验设置的数据, 构造器创建的对象会在序列化时校验字段
   3. 构造器创建方式性能更好, 因为builder创建时要复制一份数据结构
   4. builder模式需要设置所有没有默认值的字段,构造器不需要(比如可为null的就不设置)
4. 例子
```
User user1 = new User();
user1.setName("Alyssa");
user1.setFavoriteNumber(256);
// Leave favorite color null

// Alternate constructor
User user2 = new User("Ben", 7, "red");

// Construct via builder
User user3 = User.newBuilder()
             .setName("Charlie")
             .setFavoriteColor("blue")
             .setFavoriteNumber(null)
             .build();

```
#### 5.2 序列化
``` 
// Serialize user1, user2 and user3 to disk
DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
dataFileWriter.create(user1.getSchema(), new File("users.avro"));
dataFileWriter.append(user1);
dataFileWriter.append(user2);
dataFileWriter.append(user3);
dataFileWriter.close();
```
1. SpecificDatumWriter从avro schema生成的类中提取schema
2. DatumWriter将java bean转化为内存序列化格式
3. DataFileWriter将序列化的数据和schema一起写入指定的文件中
#### 5.3 反序列化
``` 
// Deserialize Users from disk
DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
DataFileReader<User> dataFileReader = new DataFileReader<User>(file, userDatumReader);
User user = null;
while (dataFileReader.hasNext()) {
   // Reuse user object by passing it to next(). This saves us from
   // allocating and garbage collecting many objects for files with
   // many items.
   user = dataFileReader.next(user);
   System.out.println(user);
}
```
1. SpecificDatumWriter将内存中序列化对象转化为java bean(这里是User)
2. DataFileReader从序列化数据中读取数据和schema
3. 数据根据提供给reader的schema和writer写时的schema(数据中)来读取,如何两个schema之间有差异,就根据schema解析规范来解析
4. 这里反序列化为对象时有一个优化,通过使用dataFileReader.next()只创建一个对象,每次反序列化之后的数据赋值到这同一个对象中，防止大量分配对象的开销和垃圾回收的压力
5. 如果不关心性能的话,可以使用
``` 
for (User user : dataFileReader)
```
#### 6.不用代码生成的序列化和反序列化
##### 6.1 创建对象
1. 因为avro会把内容和schema一起存储到序列化之后的数据中,所以可使用无代码生辰的方式来序列化和反序列化
``` 
GenericRecord user1 = new GenericData.Record(schema);
user1.put("name", "Alyssa");
user1.put("favorite_number", 256);
// Leave favorite color null

GenericRecord user2 = new GenericData.Record(schema);
user2.put("name", "Ben");
user2.put("favorite_number", 7);
user2.put("favorite_color", "red");
```
2. 序列化和反序列化  
序列化:
``` 
// Serialize user1 and user2 to disk
File file = new File("users.avro");
DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
dataFileWriter.create(schema, file);
dataFileWriter.append(user1);
dataFileWriter.append(user2);
dataFileWriter.close();
```
反序列化:
```
// Deserialize users from disk
DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
GenericRecord user = null;
while (dataFileReader.hasNext()) {
// Reuse user object by passing it to next(). This saves us from
// allocating and garbage collecting many objects for files with
// many items.
user = dataFileReader.next(user);
System.out.println(user);
```
#### 6.2 avro guide
参考 [avro guide](https://avro.apache.org/docs/1.11.1/getting-started-java/)