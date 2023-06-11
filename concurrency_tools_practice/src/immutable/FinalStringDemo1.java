package immutable;

/**
 * 描述：     TODO
 */
public class FinalStringDemo1 {

    public static void main(String[] args) {
        String a = "wukong2";
        final String b = "wukong"; // 被当做一个编译时期的常量（宏替换类似）
        String d = "wukong";
        String c = b + 2; // 没有必要再创建一个新的String对象，编译时就知道c的值
        String e = d + 2; // 运行时才能确定e的值，因此会在堆上创建一个新的String对象
        System.out.println((a == c));
        System.out.println((a == e));
    }
}
