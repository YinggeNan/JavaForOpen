package com.cdb.jsonparse;

import com.cdb.FileUtil;
import com.jayway.jsonpath.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author yingge
 * @Date 2022/11/22 20:19
 */
public class JsonPathParserTest {
    public static void main(String[] args) {
//        parseWithSystemPathTest();
//        parseWithClassPathTest();
//        parseWithClassPathUsingPredicatesTest();
//        parseWithClassPathUsingCustomPredicatesTest();
//        parseWithClassPathUsingInlinePredicatesTest();
//        testJsonPathExample3();
//        testJsonPathExample3WithFunction();
        testJsonPathExample3CalculateHighestRevenue();
    }

    /**
     * 使用systemPath指定json文件地址
     */
    public static void parseWithSystemPathTest(){
        String filePath = "D:\\workspace\\JavaForOpen\\Utils\\src\\main\\resources\\JsonExampleForJsonPathBasic.json";
        String jsonpathCreatorNamePath = "$['tool']['jsonpath']['creator']['name']";
        String jsonpathCreatorLocationPath = "$['tool']['jsonpath']['creator']['location'][*]";
        String s1 = JsonPathParser.<String>parseWithSystemPath(filePath, jsonpathCreatorNamePath);
        List<String> s2 = JsonPathParser.<List<String>>parseWithSystemPath(filePath, jsonpathCreatorLocationPath);
        System.out.println();
    }

    /**
     * 使用classPath指定json文件地址
     */
    public static void parseWithClassPathTest(){
        String filePath = "JsonExampleForJsonPathBasic.json";
        String jsonpathCreatorNamePath = "$['tool']['jsonpath']['creator']['name']";
        String jsonpathCreatorLocationPath = "$['tool']['jsonpath']['creator']['location'][*]";
        String s1 = JsonPathParser.<String>parseWithClassPath(filePath, jsonpathCreatorNamePath);
        List<String> s2 = JsonPathParser.<List<String>>parseWithClassPath(filePath, jsonpathCreatorLocationPath);
        System.out.println();
    }

    /**
     * 用预定义的filter api, pattern里的?是给后面的谓词对象预留位置
     */
    public static void parseWithClassPathUsingPredicatesTest(){
        String filePath = "JsonExampleForJsonPathPredicates.json";
        Filter expensiveFilter = Filter.filter(Criteria.where("price").gt(20.00));
        String pattern = "$['book'][?]";
        List<Map<String, Object>> res = JsonPathParser.parseWithClassPath(filePath, pattern, expensiveFilter);
        System.out.println();
    }

    /**
     * 自定义filter, pattern里的?是给后面的谓词对象预留位置
     */
    public static void parseWithClassPathUsingCustomPredicatesTest(){
        String filePath = "JsonExampleForJsonPathPredicates.json";
        Predicate expensivePredicate  = new Predicate() {

            @Override
            public boolean apply(PredicateContext ctx) {
                String price = ctx.item(Map.class).get("price").toString();
                return Float.valueOf(price)>20.00;
            }
        };
        String pattern = "$['book'][?]";
        List<Map<String, Object>> res = JsonPathParser.parseWithClassPath(filePath, pattern, expensivePredicate);
        System.out.println();
    }

    /**
     * 内联谓词 filter: 不需要创建 Predicate 对象,直接把过滤条件放到 Pattern 字符串中
     */
    public static void parseWithClassPathUsingInlinePredicatesTest(){
        String filePath = "JsonExampleForJsonPathPredicates.json";
        String inlinePredicatePattern = "$['book'][?(@['price'] > $['price range']['medium'])]";
        List<Map<String, Object>> res = JsonPathParser.parseWithClassPath(filePath, inlinePredicatePattern);
        System.out.println();
    }

    /**
     * 对 JsonExampleForJsonPath3.json(电影例子)进行测试
     */
    public static void testJsonPathExample3(){
        String filePath = "JsonExampleForJsonPath3.json";
        String movieIdPattern = "$[?(@.id == 2)]";
        Object  obj = JsonPathParser.parseWithClassPath(filePath, movieIdPattern);
        String startNamePattern = "$[?('Eva Green' in @['starring'])]";
        List<Map<String, Object>> dataList = JsonPathParser.parseWithClassPath(filePath, startNamePattern);
        String title = (String)dataList.get(0).get("title");
        System.out.println();
    }

    /**
     * 在pattern里使用length()函数
     */
    public static void testJsonPathExample3WithFunction(){
        String filePath = "JsonExampleForJsonPath3.json";
        String lengthPattern = "$.length()";
        Integer  len = JsonPathParser.parseWithClassPath(filePath, lengthPattern);
        Long revenue = 0L;
        for (int i = 0; i < len; i++) {
            revenue += (Long)JsonPathParser.parseWithClassPath(filePath,"$[" + i + "]['box office']", Long.class);
        }
        System.out.println(revenue);
    }

    /**
     * 修改Jsonpath默认返回节点值的设置为返回path
     * 问题1: 求具有最高box office值节点的title
     * 此guide给的解法是通过3次解析json获得具有最高revenue的node, 效率很低说实话,完全可以提取一次所有节点,然后写一个自定Comparator来排序的
     * 1. 获取所有‘box office’的值,排序得到最大值
     * 2. 用上面获取的最大值返回节点位置
     * 3. 用上面得到的节点位置获取节点
     */
    public static void testJsonPathExample3CalculateHighestRevenue(){
        String filePath = "JsonExampleForJsonPath3.json";
        String highestRevenuePattern = "$[*]['box office']";
        List<Object> revenueList= JsonPathParser.parseWithClassPath(filePath, highestRevenuePattern);
        Integer[] revenueArray = revenueList.toArray(new Integer[0]);
        Arrays.sort(revenueArray);
        int highestRevenue = revenueArray[revenueArray.length - 1];
        Configuration pathConfiguration =
                Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> pathList = JsonPath.using(pathConfiguration).parse(FileUtil.readFileToStringFromClassPath(filePath))
                .read("$[?(@['box office'] == " + highestRevenue + ")]");
        String highestPath = pathList.get(0);
        Map<String, String> dataRecord= JsonPathParser.parseWithClassPath(filePath, highestPath);
        System.out.println(dataRecord.get("title"));
    }
    /**
     * 问题2: 求director是'Sam Mende'导演的最后一部作品,也将jsonpath返回值修改为返回路径
     * pattern可以是 "$[?(@.director == 'Sam Mendes')]"
     */


}
