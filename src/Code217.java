import java.util.Arrays;
import java.util.Scanner;

public class Code217 {

    private char[] temp;
    private int[] g0 = new int[]{1, 0, 1, 0, 1, 0, 1, 1};//171
    private int[] g1 = new int[]{1, 0, 0, 0, 0, 1, 0, 1};//133

    public static void main(String[] args) {
        Code217 code = new Code217();
        String res = Code217.getString();

        System.out.println(Arrays.toString(code.coding(res)));
    }


    /***
     * 输入值
     * @param string
     * @return
     */
    private int str2num(String string) {
        temp = string.toCharArray();
        return string.length();
    }

    /***
     * 获取输入值
     * @return
     */
    public static String getString() {
        Scanner scanner = new Scanner(System.in);
        String str = null;
        System.out.print("请输入任意字符: ");
        str = scanner.nextLine();
        return str;
    }

    /***
     * 卷积计算
     * @param uIn
     * @param length
     * @param g
     * @return
     */
    private int[] conv(int[] uIn, int length, int[] g) {
        int len_max = length + 7;
        int[] v = new int[len_max];
        for (int i = 0; i < 8; i++) {
            for (int j = len_max - i; j > len_max - length - i; j--) {
                v[j - 1] = ((g[7 - i] * uIn[j - 8 + i] + v[j - 1])) % 2;
            }
        }
        return v;
    }

    /***
     * 编码
     * @param string
     * @return
     */
    public int[] coding(String string) {
        int lenu = str2num(string);
        int[] uIn = new int[lenu];
        for (int i = 0; i < lenu; i++) {
            uIn[i] = (int) temp[i] - 0x30;
        }
        int[] v0 = conv(uIn, lenu, g0);
        int[] v1 = conv(uIn, lenu, g1);
        int[] res = new int[2 * (lenu + 7)];
        for (int i = 0; i < lenu + 7; i++) {
            res[2 * i] = v0[i];
            res[2 * i + 1] = v1[i];
        }
        return res;
    }

}
