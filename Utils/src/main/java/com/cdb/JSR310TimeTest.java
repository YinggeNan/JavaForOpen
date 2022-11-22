package com.cdb;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * reference1: https://www.cnblogs.com/yourbatman/p/14334554.html
 * reference2: https://mp.weixin.qq.com/s/_Zt4JcUokea-zha0gCFnsA
 *
 * @Author yingge
 * @Date 2022/11/19 18:34
 */
public class JSR310TimeTest {
    public static void main(String[] args) {
//        printDateTime();
        testLocalDateTime();
    }

    public static void printDateTime() {
        System.out.println(LocalDate.now(ZoneId.systemDefault()));
        System.out.println(LocalTime.now(ZoneId.systemDefault()));
        System.out.println(LocalDateTime.now(ZoneId.systemDefault()));
        System.out.println("---------");
        System.out.println(OffsetTime.now(ZoneId.systemDefault()));
        System.out.println(OffsetDateTime.now(ZoneId.systemDefault()));
        System.out.println(ZonedDateTime.now(ZoneId.systemDefault()));
        System.out.println("---------");
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now()));
        System.out.println(DateTimeFormatter.ISO_LOCAL_TIME.format(LocalTime.now()));
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
    }

    public static void testLocalDateTime() {
        LocalDate nowDate = LocalDate.now(ZoneId.systemDefault());
        // 输出从1970-01-01 (ISO)到现在有多少天了
        System.out.println(nowDate.toEpochDay());
        LocalDateTime now = LocalDateTime.now();
        System.out.println("计算前的localDateTime" + now);
        // 加3天
        LocalDateTime after = now.plusDays(3);
        // 减4个小时
        after = after.plusHours(-4); // 效果同now.minusDays(3);
        System.out.println("计算后：" + after);
        Period period = Period.between(now.toLocalDate(), after.toLocalDate());
        System.out.println(period.getDays());
        System.out.println(period.getMonths());
        System.out.println(period.getYears());
        Duration duration = Duration.between(now.toLocalTime(), after.toLocalTime());
        System.out.println("小时diff: "+duration.toHours());
        System.out.println("days diff: "+duration.toDays());
//        System.out.println("小时diff: "+duration.toHours());
    }
}
