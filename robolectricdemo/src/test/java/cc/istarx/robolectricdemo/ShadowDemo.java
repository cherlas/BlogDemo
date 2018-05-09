package cc.istarx.robolectricdemo;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(Demo.class)
public class ShadowDemo {
    public ShadowDemo(){
    }

    @Implementation
    public String getStr() {
        return "Shadow Demo implementation";
    }
}
