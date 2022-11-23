### 1.maven是什么
1. maven将patterns（最佳实践）应用到项目的构建基础设施来提高生产力和对项目的理解
2. maven本质上就是一个项目管理和项目理解工具
3. maven提供了下列方式来帮助管理项目
   1. Builds
   2. Documentation
   3. Reporting
   4. Dependencies
   5. SCMs
   6. Releases
   7. Distribution
4. 参考下列资源来对maven有更深入的理解
   1. [maven的哲学](https://maven.apache.org/background/philosophy-of-maven.html)
   2. [maven的历史](https://maven.apache.org/background/history-of-maven.html)

### 2.maven对开发流程有什么帮助
1. Maven 可以通过采用标准约定和实践(standard conventions and practices)来加速您的开发周期(development cycle)
### 3.如何setup maven
1. maven的默认设置对大多数开发都足够了
2. 可以自定义配置来修改比如缓存位置、HTTP Proxy等, 参考[配置maven指南](https://maven.apache.org/guides/mini/guide-configuring-maven.html)

### 4.如何创建一个maven project
#### 4.1 maven基本例子
1. 我们使用maven的原型机制(archetype mechanism)来创建项目, 一个原型就是一种模式(pattern)、模型(model),其规定了一类事物的组成方式
2. maven的原型就是一个项目模板(template of project), 其可以和用户的一些输入参数结合来生成根据用户需求定制的maven项目
3. [maven原型机制介绍](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html)
4. 执行下列命令创建一个最简单的maven项目
    ```
    mvn -B archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4
    ```
5. maven命令解释
   1. 上述命令会创建一个名为 my-app 的目录
   2. my-app目录中包含一个pom.xml文件
   3. pom.xml内容如下
   4. pom.xml是这个项目的 项目对象模型(Project Object Model-POM), POM是maven的基本工作单元, maven以项目为中心,一切以项目概念展开,
      POM中包含关于该项目的所有重要信息, 参考[Introduction to the POM.](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)获取更多信息
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
 
  <groupId>com.mycompany.app</groupId>
  <artifactId>my-app</artifactId>
  <version>1.0-SNAPSHOT</version>
 
  <name>my-app</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>
 
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.7</maven.compiler.source>
    <maven.compiler.target>1.7</maven.compiler.target>
  </properties>
 
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
 
  <build>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
       ... lots of helpful plugins
    </pluginManagement>
  </build>
</project>
```

#### 4.2 POM中包含的关键元素
1. **project**: 所有maven的pom.xml的顶级元素(top-level)
2. **modelVersion**: 指明这个pom文件使用的对象模型的版本, 对象模型版本很少更改,但是开发人员认为有必要时可以修改,为了确保使用的稳定性,这个元素是必选的
3. **groupId**: 创建该项目的组织的唯一标识符,groupId是project的关键标识符之一,一般基于您组织的完全限定域名,比如org.apache.maven.plugins 是所有 Maven 插件的指定 groupId
4. **artifactId**: 指明此项目生成的主要工件的唯一基本名称,项目生成的主要工件一般是JAR包,像源包(source bundles)这样的次要工件也使用 artifactId 作为它们最终名称的一部分,
Maven 生成的典型工件的格式为 <artifactId>-<version>.<extension>（例如，myapp-1.0.jar）
5. **version**: 表示项目生成的工件的版本,你经常会在版本中看到 SNAPSHOT 指示符，这表明项目处于开发状态
6. **name**: 指示用于项目的显示名称, 在 Maven 生成的文档中经常使用
7. **url**: 项目网站的位置, 这在 Maven 生成的文档中经常使用
8. **properties**: 包含可在 POM 中的任何位置访问的值占位符
9. **dependencies**: 该元素的子元素列出了依赖项
10. **build**: 这个元素处理诸如声明项目的目录结构(directory structure)和管理插件之类的事情
11. 查看[POM参考](https://maven.apache.org/ref/3.8.6/maven-model/maven.html)获取POM中可用的所有元素信息
#### 4.3 项目的目录结构
上面执行的命令会生成如下的目录结构
```
my-app
|-- pom.xml
`-- src
    |-- main
    |   `-- java
    |       `-- com
    |           `-- mycompany
    |               `-- app
    |                   `-- App.java
    `-- test
        `-- java
            `-- com
                `-- mycompany
                    `-- app
                        `-- AppTest.java
```
1. 上图表示,这个原型结构有一个pom.xml文件和一个应用资源树结构、测试资源树结构 
2. 这是maven项目的标准布局( standard layout),即应用资源在目录${basedir}/src/main/java, 测试资源在${basedir}/src/test/java, ${basedir}代表包含pom.xml的目录 
3. 这个目录结构是maven的约定,maven的很多默认处理过程都基于这个约定,所以强烈建议使用这个约定的目录结构,参考[maven目录结构介绍](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)获取详细信息 
### 5.如何编译应用
切换到包含pom.xml的目录下,执行如下命令:
``` 
mvn compile
```
输出如下:
```
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------------< com.mycompany.app:my-app >----------------------
[INFO] Building my-app 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:3.0.2:resources (default-resources) @ my-app ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory <dir>/my-app/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ my-app ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to <dir>/my-app/target/classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.899 s
[INFO] Finished at: 2020-07-12T11:31:54+01:00
[INFO] ------------------------------------------------------------------------
```
1. 第一次执行此（或任何其他）命令时，Maven 将需要下载执行该命令所需的所有插件和相关依赖项,这个过程耗时比较长
2. 您再次执行该命令，Maven 就已拥有它需要的东西，因此它不需要下载任何新的东西并且能够更快地执行该命令
3. 从输出中可以看出，编译后的类位于 ${basedir}/target/classes, 这是 Maven 采用的另一个标准约定
4. 所以通过遵循标准的 Maven 约定, 你不必明确告诉 Maven 任何源在哪里或输出应该去哪里

### 6.如何编译应用的测试代码并运行
想要编译和执行单元测试时,执行如下命令
```
mvn test
```
输出如下:
```
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------------< com.mycompany.app:my-app >----------------------
[INFO] Building my-app 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:3.0.2:resources (default-resources) @ my-app ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory <dir>/my-app/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ my-app ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:3.0.2:testResources (default-testResources) @ my-app ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory <dir>/my-app/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.0:testCompile (default-testCompile) @ my-app ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to <dir>/my-app/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ my-app ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.mycompany.app.AppTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.025 s - in com.mycompany.app.AppTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.881 s
[INFO] Finished at: 2020-07-12T12:00:33+01:00
[INFO] ------------------------------------------------------------------------
```
1. 从输出里可以看到, Maven 这次下载了更多的依赖项, 这些是执行测试所必需的依赖项和插件（它已经具有编译所需的依赖项，不会再次下载它们) 
2. 在编译和执行测试之前，Maven会编译应用代码(因为自上次编译以来我们没有改变任何东西,所以所有的class文件都是最新的)
3. 如果您只是想编译您的测试源代码,但不执行测试,可以执行以下命令：
```
 mvn test-compile
```
### 7.如何创建JAR包基于install到本地仓库
#### 7.1 package & install
1. 构建jar包
``` 
mvn package
```
jar包默认会输出到 ${basedir}/target 目录
2. 将生成的jar包安装到本地存储库(默认是${user.home}/.m2/repository目录), 参考[仓库介绍](https://maven.apache.org/guides/introduction/introduction-to-repositories.html)获取更多信息
``` 
mvn install
```
install命令输出:
```
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------------< com.mycompany.app:my-app >----------------------
[INFO] Building my-app 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:3.0.2:resources (default-resources) @ my-app ---
...
[INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ my-app ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:3.0.2:testResources (default-testResources) @ my-app ---
...
[INFO] --- maven-compiler-plugin:3.8.0:testCompile (default-testCompile) @ my-app ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ my-app ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.mycompany.app.AppTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.025 s - in com.mycompany.app.AppTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- maven-jar-plugin:3.0.2:jar (default-jar) @ my-app ---
[INFO] Building jar: <dir>/my-app/target/my-app-1.0-SNAPSHOT.jar
[INFO] 
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ my-app ---
[INFO] Installing <dir>/my-app/target/my-app-1.0-SNAPSHOT.jar to <local-repository>/com/mycompany/app/my-app/1.0-SNAPSHOT/my-app-1.0-SNAPSHOT.jar
[INFO] Installing <dir>/my-app/pom.xml to <local-repository>/com/mycompany/app/my-app/1.0-SNAPSHOT/my-app-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.678 s
[INFO] Finished at: 2020-07-12T12:04:45+01:00
[INFO] ------------------------------------------------------------------------
```
请注意，surefire 插件（执行测试）会查找包含在具有特定命名约定的文件中的测试, 默认情况下包含的测试是：
1. **/*Test.java  
2. **/Test*.java  
3. **/*TestCase.java  
默认排除的文件是:
1. **/Abstract*Test.java
2. **/Abstract*TestCase.java
#### 7.2 maven的开箱即用的项目网站插件
```
mvn site
```
#### 7.3 clean goal
在build之前删除 target 目录以及其内的所有build data
``` 
mvn clean
```
### 8.SNAPSHOT version是什么
1. 有时候,你会注意到pom.xml中的version标签的值有 -SNAPSHOT 后缀
2. SNAPSHOT版本 指的是开发分支中的“最新”代码，不保证代码稳定或不变, 相反，release 版本（任何没有后缀 SNAPSHOT的version值）中的代码是不变的
3. 换句话说, SNAPSHOT 版本是最终“release”版本之前的“开发”版本, SNAPSHOT 比其发布版本“旧”
4. 发布过程时, SNAPSHOT 版本号为 x.y-SNAPSHOT, 发布该版本, 则修改为 x.y,然后新的 SNAPSHOT 版本号就为 x.(y+1)-SNAPSHOT; 比如1.0-SNAPSHOT发布为1.0,新的开发版本就是1.1-SNAPSHOT
### 9.如何使用插件(plugins)
### 10.如何添加资源(resources)到JAR包中
### 11.如何过滤资源文件(filter resource files)
### 12.如何使用外部依赖（external dependencies)
### 13.如何部署jar包到远程仓库
### 14.如何创建应用的文档
### 15.如何构建其他类型的项目
### 16.如何同时构建多个项目
### [maven官方参考](https://maven.apache.org/guides/getting-started/index.html#How_can_Maven_benefit_my_development_process)