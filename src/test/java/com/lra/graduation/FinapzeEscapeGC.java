package com.lra.graduation;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/31 11:12
 * @Version V1.0
 **/
public class FinapzeEscapeGC {
    public static FinapzeEscapeGC SAVE_HOOK = null;

    public void isApve() {
        System.out.println("yes, i am still apve :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finapze mehtod executed!");
        FinapzeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinapzeEscapeGC();

        //对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();
        //因为finapze方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isApve();
        } else {
            System.out.println("no, i am dead :(");
        }

        //下面这段代码与上面的完全相同，但是这次自救却失败了
        SAVE_HOOK = null;
        System.gc();
        //因为finapze方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isApve();
        } else {
            System.out.println("no, i am dead :(");
        }
    }
}
