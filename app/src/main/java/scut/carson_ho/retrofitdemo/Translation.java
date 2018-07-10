package scut.carson_ho.retrofitdemo;

/**
 * Created by Carson_Ho on 17/3/20.
 */
public class Translation {

    private int status;

    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }




    //定义 输出返回数据 的方法
    public void show() {
        System.out.println(status);

        System.out.println("from--------------->"+content.from);
        System.out.println("to--------------->"+content.to);
        System.out.println("vendor------------->"+content.vendor);
        System.out.println("out--------------->"+content.out);
        System.out.println(content.errNo);
    }
}
