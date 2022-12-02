package com.cdb.hamcrestTest;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static com.cdb.hamcrestTest.CustomMatcher.*;

/**
 * @Author yingge
 * @Date 2022/12/2 17:08
 * @reference https://hamcrest.org/JavaHamcrest/tutorial
 * 使用 hamcrest的特点测试代码可读性非常高,可以很清楚地知道 ”测试目标"是什么
 */
public class HamcrestDemo {

    @Test
    public void testEquals() {
        Biscuit theBiscuit = new Biscuit("Ginger");
        Biscuit myBiscuit = new Biscuit("Ginger");
        // equalTo: call the object equal method to compare, so you need to override in most cases
        assertThat(theBiscuit, equalTo(myBiscuit));
        // include a identifier to point out the failure
        assertThat("chocolate chips", theBiscuit.getChocolateChipCount(), equalTo(10));
        assertThat("hazelnuts", theBiscuit.getHazelnutCount(), equalTo(3));
        // anything: always matches
        assertThat(10, anything("ok"));
        // describedAs: override the matcher description when test failed
//        assertThat("failed ", theBiscuit.getChocolateChipCount(), describedAs("compare whether is same between each other",
//                equalTo(11)));
        assertThat("not equal", 3, equalTo(3));
        // is: improve the failed message readability , you can see it add the is prefix before the failed value
        assertThat("not equal", 3, is(equalTo(3)));

    }

    /**
     * 1.anything() - always matches, useful if you don’t care what the object under test is<br/>
     * 2.describedAs() - decorator to adding custom failure description<br/>
     * 3.is() - decorator to improve readability<br/>
     */
    @Test
    public void coreMatcherTest() {
        Biscuit theBiscuit = new Biscuit("Ginger");
        Biscuit myBiscuit = new Biscuit("Ginger");
        // anything: always matches
        assertThat(10, anything("ok"));
        // describedAs: override the matcher description when test failed
//        assertThat("failed ", theBiscuit.getChocolateChipCount(), describedAs("compare whether is same between each other",
//                equalTo(11)));
        assertThat("not equal", 3, equalTo(3));
        // is: improve the failed message readability , you can see it add the is prefix before the failed value
        assertThat("not equal", 3, is(equalTo(3)));
    }

    /**
     * 1.allOf - matches if all matchers match, short circuits (like Java &&)<br/>
     * 2.anyOf - matches if any matchers match, short circuits (like Java ||)<br/>
     * 3.not - matches if the wrapped matcher doesn’t match and vice versa<br/>
     */
    @Test
    public void logicMatcherTest() {
        // test for allOf(), allOf can used as nested type
        assertThat("must bigger than 10 and less than 50", 35, allOf(allOf(lessThan(50), greaterThan(10)), lessThan(36)));
        // test for anyOf()
        assertThat("must less than 10 or bigger than 50", 9, anyOf(lessThan(10), greaterThan(50)));
        assertThat("must less than 10 or bigger than 50", 70, anyOf(lessThan(10), greaterThan(50)));
        // test for not(), if you use 9 as value, then it will fail
        assertThat("the number must bigger than 10", 10, not(lessThan(10)));
        assertThat("the number must bigger than 10", 11, not(lessThan(10)));

    }

    /**
     * 1.equalTo - test object equality using Object.equals()<br/>
     * 2.hasToString - test Object.toString<br/>
     * 3.instanceOf, isCompatibleType - test type<br/>
     * 4.notNullValue, nullValue - test for null<br/>
     * 5.sameInstance - test object identity
     */
    @Test
    public void objectMatcherTest() {
        Biscuit oneBiscuit = new Biscuit("level-1");
        Biscuit twoBiscuit = new Biscuit("level-1");
        // test for equalTo(), will call object's equal() method, so maybe you need to override it in most cases
        assertThat(oneBiscuit, equalTo(twoBiscuit));
        // test for hasToString(), will call object's toString() method, so maybe you need to override it in most cases
        assertThat(oneBiscuit, hasToString("Biscuit{name='level-1', number=null}"));
        // test for instanceOf()
        assertThat(oneBiscuit, instanceOf(Biscuit.class));
        // test  if the object is not null
        assertThat(oneBiscuit, notNullValue());
        // test if the object is  null
        assertThat(null, nullValue());
        // test whether the two objects are same
        assertThat(oneBiscuit, sameInstance(oneBiscuit));
    }

    /**
     * 1.hasProperty - test JavaBeans properties<br/>
     * your must have the getter method for the target property,<br/>
     * and you don't need to assign value to  the property<br/>
     */
    @Test
    public void beansMatcherTest() {
        Biscuit biscuit = new Biscuit();
        // test for hasProperty()
        assertThat("should have name property ", biscuit, hasProperty("name"));
        assertThat("should have number property", biscuit, hasProperty("number"));
    }

    /**
     * 1.array - test an array’s elements against an array of matchers<br/>
     * 2.hasEntry, hasKey, hasValue - test a map contains an entry, key or value<br/>
     * 3.hasItem, hasItems - test a collection contains elements<br/>
     * 4.hasItemInArray - test an array contains an element<br/>
     */
    @Test
    public void collectionsTest() {
        arrayMatcherTest1();
        arrayMatcherTest2();
        mapMatcherTest();
        collectionMatcherTest();
        iteratorMatcherTest();
    }

    public void arrayMatcherTest1() {
        Integer[] strList = new Integer[]{1, 100, 200};
        /*
          1.test for array()
          test the array's element using corresponding matcher by index of array
         */
        assertThat(strList, array(lessThan(10), greaterThan(50), greaterThan(150)));
    }

    public void arrayMatcherTest2() {
        /*
          4.test for hasItemInArray()
          test if array contains a target element
         */
        String[] strList = new String[]{"s1", "s2", "s3"};
        assertThat(strList, hasItemInArray("s3"));
    }

    public void mapMatcherTest() {
        Map<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        // 2.test for hasKey(),hasValue(),hasEntry()
        // 2.test if the map contains the key
        assertThat(map, hasKey("k1"));
        // 2.test if the map contains the value
        assertThat(map, hasValue("v2"));
        // 2.test if the map contains the entry:{key:value}, try to replace "v1" with "v2", and it will fail
        assertThat(map, hasEntry("k1", "v1"));
    }

    public void collectionMatcherTest() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        List<String> strings = Arrays.asList("s1", "s2", "s3", "s4");

        /*
          3.test if hasItem
          test for hasItem()
         */
        assertThat(integers, hasItem(1));
        assertThat(strings, hasItem("s1"));
        assertThat(integers, hasItem(3));
    }

    public void iteratorMatcherTest(){
        // should have the same length and corresponding element should be the same
        assertThat(Arrays.asList("1","2","3"), contains("1","2","3"));
    }

    /**
     * 1.closeTo - test floating point values are close to a given value<br/>
     * 2.greaterThan, greaterThanOrEqualTo, lessThan, lessThanOrEqualTo - test ordering<br/>
     */
    @Test
    public void numberMatcherTest() {
        Double x = 3.5d;
        /**
         * 1. test for closeTo()
         */
        // the specified value can vary from 3.5d-0.5d to 3.5d+0.5d
        assertThat("should range from 3.5-0.5 to 3.5+0.5", x, closeTo(3.5d, 0.5));
        BigDecimal bigDecimal1 = new BigDecimal("100");
        // the specified value can vary from 1000-900 to 1000+900
        assertThat("the value should between 1000-900 and 1000+900", bigDecimal1, closeTo(new BigDecimal(1000), new BigDecimal("900")));
        /**
         * 2. test for greaterThan, greaterThanOrEqualTo, lessThan, lessThanOrEqualTo
         */
        assertThat("greater than 30", 31, greaterThan(30));
        assertThat("greater than or equal 30", 30, greaterThanOrEqualTo(30));
        assertThat("less  than 30", 29, lessThan(30));
        assertThat("less than or equal 30", 30, lessThanOrEqualTo(30));
    }

    /**
     * 1. equalToIgnoringCase - test string equality ignoring case
     * 2. equalToIgnoringWhiteSpace - test string equality ignoring differences in runs of whitespace
     * 3. containsString, endsWith, startsWith - test string matching
     * 4. equalToCompressingWhiteSpace: ignore all leading and trailing white space for both examined str and expected str,
     * and compress the multiple white space to one for space between characters.
     */
    @Test
    public void textMatcherTest() {
        String str = "  I love    YOU  ";
        // 1. test for equalToCompressingWhiteSpace()
        assertThat("I love you", equalToIgnoringCase("i LOVE YOU"));
        // 2. test for equalToIgnoringWhiteSpace()
        assertThat("I love you ", equalToIgnoringWhiteSpace("I love        you    "));
        // 3. test for containsString()
        assertThat("i love you", containsString("love"));
        // 3. test for startsWith()
        assertThat("i love you", startsWith("i"));
        // 3. test for endsWith()
        assertThat("i love you", endsWith("u"));
        // 4. test for equalToCompressingWhiteSpace()
        assertThat(str.toLowerCase(Locale.ROOT), equalToCompressingWhiteSpace("i love you"));
    }

    /**
     * test custom matcher
     */
    @Test
    public void customMatcherTest() {
        // if you use 9 replace 11, then it will fail, try it
        assertThat("test custom matcher", new Biscuit(11), customMatch());
    }
}

