package immutable;

/**
 * 描述：     TODO
 */
public class FinalStringDemo2 {

    public static void main(String[] args) {
        String a = "wukong2";
        final String b = getDashixiong(); // 通过方法获取的，编译器无法直接获取b的值
        String c = b + 2;
        System.out.println(a == c); // false

    }

    private static String getDashixiong() {
        return "wukong";
    }
}
