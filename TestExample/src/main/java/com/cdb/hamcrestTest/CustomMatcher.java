package com.cdb.hamcrestTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * @Author yingge
 * @Date 2022/12/2 20:28
 * 1.define yourself custom matcher to reduce code reduplication、
 * 2.should guarantee the matcher is stateless,
 * so other person can enhance the performance by using singleton pattern
 * 应该保证自定义的matcher是没有状态的,所以当别人使用单例模式来
 */
public class CustomMatcher implements Matcher<Biscuit>{
    @Override
    public void describeTo(Description description) {
        description.appendText("this is buscuit matcher, that nubmer must bigger than 10");
    }

    @Override
    public boolean matches(Object actual) {
        if (((Biscuit) actual).getNumber() > 10) {
            return true;
        }
        return false;
    }

    @Override
    public void describeMismatch(Object actual, Description mismatchDescription) {
        mismatchDescription.appendText("not match, the actual value is " + ((Biscuit) actual).getNumber());
    }

    @Override
    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

    }
    // factory method to return the custom matcher
    public static CustomMatcher customMatch(){
        return new CustomMatcher();
    }
}
