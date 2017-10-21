import java.util.Arrays;
import java.util.Scanner;

public class Code {
    private char[] temp;
    private int[] g0 = new int[]{1, 0, 1, 1};
    private int[] g1 = new int[]{1, 1, 1, 1};

    public static void main(String[] args) {


        Code code = new Code();
        String res = Code.getString();

        System.out.println(Arrays.toString(code.decode(res)));
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
     * 输入值
     * @param string
     * @return
     */
    private int str2num(String string) {
        temp = string.toCharArray();
        return string.length();
    }

    /***
     * 卷积计算
     * @param uIn
     * @param length
     * @param g
     * @return
     */
    private int[] conv(int[] uIn, int length, int[] g) {
        int len_max = length + 3;
        int[] v = new int[len_max];
        for (int i = 0; i < 4; i++) {
            for (int j = len_max - i; j > len_max - length - i; j--) {
                v[j - 1] = ((g[3 - i] * uIn[j - 4 + i] + v[j - 1])) % 2;
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
        int[] res = new int[2 * (lenu + 3)];
        for (int i = 0; i < lenu + 3; i++) {
            res[2 * i] = v0[i];
            res[2 * i + 1] = v1[i];
        }
        return res;
    }

    public int[] decode(String string) {
        int length = str2num(string);
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) temp[i] - 0x30;
        }

        int[] d_o = new int[8];//保存到达当前状态的汉明距离
        int[] d_n = new int[8];//保存更新前的当前状态的汉明距离
        int[][] s_n = new int[8][length];//保存新的路径
        int[][] s_o = new int[8][length];//保存更新前的路径

        d_o[0] = (arr[0] ^ 0) + (arr[1] ^ 0) + (arr[2] ^ 0) + (arr[3] ^ 0) + (arr[4] ^ 0) + (arr[5] ^ 0);
        s_o[0][0] = 0;
        s_o[0][1] = 0;
        s_o[0][2] = 0;

        d_o[1] = (arr[0] ^ 0) + (arr[1] ^ 0) + (arr[2] ^ 0) + (arr[3] ^ 0) + (arr[4] ^ 1) + (arr[5] ^ 1);
        s_o[1][0] = 0;
        s_o[1][1] = 0;
        s_o[1][2] = 1;

        d_o[2] = (arr[0] ^ 0) + (arr[1] ^ 0) + (arr[2] ^ 1) + (arr[3] ^ 1) + (arr[4] ^ 0) + (arr[5] ^ 1);
        s_o[2][0] = 0;
        s_o[2][1] = 1;
        s_o[2][2] = 0;

        d_o[3] = (arr[0] ^ 0) + (arr[1] ^ 0) + (arr[2] ^ 1) + (arr[3] ^ 1) + (arr[4] ^ 1) + (arr[5] ^ 0);
        s_o[3][0] = 0;
        s_o[3][1] = 1;
        s_o[3][2] = 1;

        d_o[4] = (arr[0] ^ 1) + (arr[1] ^ 1) + (arr[2] ^ 0) + (arr[3] ^ 1) + (arr[4] ^ 1) + (arr[5] ^ 1);
        s_o[4][0] = 1;
        s_o[4][1] = 0;
        s_o[4][2] = 0;

        d_o[5] = (arr[0] ^ 1) + (arr[1] ^ 1) + (arr[2] ^ 0) + (arr[3] ^ 1) + (arr[4] ^ 0) + (arr[5] ^ 0);
        s_o[5][0] = 1;
        s_o[5][1] = 0;
        s_o[5][2] = 1;

        d_o[6] = (arr[0] ^ 1) + (arr[1] ^ 1) + (arr[2] ^ 1) + (arr[3] ^ 0) + (arr[4] ^ 1) + (arr[5] ^ 0);
        s_o[6][0] = 1;
        s_o[6][1] = 1;
        s_o[6][2] = 0;

        d_o[7] = (arr[0] ^ 1) + (arr[1] ^ 1) + (arr[2] ^ 1) + (arr[3] ^ 0) + (arr[4] ^ 0) + (arr[5] ^ 1);
        s_o[7][0] = 1;
        s_o[7][1] = 1;
        s_o[7][2] = 1;

        for (int i = 6; i < length; i = i + 2) {

            //更新状态0
            if (d_o[0] + (arr[i] ^ 0) + (arr[i + 1] ^ 0) <= d_o[4] + (arr[i] ^ 1) + (arr[i + 1] ^ 1)) {
                d_n[0] = d_o[0] + (arr[i] ^ 0) + (arr[i + 1] ^ 0);
                for (int j = 0; j < i / 2; j++) {
                    s_n[0][j] = s_o[0][j];
                }
                s_n[0][i / 2] = 0;
            } else {
                d_n[0] = d_o[4] + (arr[i] ^ 1) + (arr[i + 1] ^ 1);
                for (int j = 0; j < i / 2; j++) {
                    s_n[0][j] = s_o[4][j];
                }
                s_n[0][i / 2] = 0;
            }

            //更新状态1
            if (d_o[0] + (arr[i] ^ 1) + (arr[i + 1] ^ 1) <= d_o[4] + (arr[i] ^ 0) + (arr[i + 1] ^ 0)) {
                d_n[1] = d_o[0] + (arr[i] ^ 1) + (arr[i + 1] ^ 1);
                for (int j = 0; j < i / 2; j++) {
                    s_n[1][j] = s_o[4][j];
                }
                s_n[1][i / 2] = 1;
            } else {
                d_n[1] = d_o[4] + (arr[i] ^ 0) + (arr[i + 1] ^ 0);
                for (int j = 0; j < i / 2; j++) {
                    s_n[1][j] = s_o[4][j];
                }
                s_n[1][i / 2] = 1;
            }

            //更新状态2
            if (d_o[1] + (arr[i] ^ 0) + (arr[i + 1] ^ 1) <= d_o[4] + (arr[i] ^ 1) + (arr[i + 1] ^ 0)) {
                d_n[2] = d_o[1] + (arr[i] ^ 0) + (arr[i + 1] ^ 1);
                for (int j = 0; j < i / 2; j++) {
                    s_n[2][j] = s_o[1][j];
                }
                s_n[2][i / 2] = 0;
            } else {
                d_n[2] = d_o[5] + (arr[i] ^ 1) + (arr[i + 1] ^ 0);
                for (int j = 0; j < i / 2; j++) {
                    s_n[2][j] = s_o[5][j];
                }
                s_n[2][i / 2] = 0;
            }

            //更新状态3
            if (d_o[1] + (arr[i] ^ 1) + (arr[i + 1] ^ 0) <= d_o[5] + (arr[i] ^ 0) + (arr[i + 1] ^ 1)) {
                d_n[3] = d_o[1] + (arr[i] ^ 1) + (arr[i + 1] ^ 0);
                for (int j = 0; j < i / 2; j++) {
                    s_n[3][j] = s_o[1][j];
                }
                s_n[3][i / 2] = 1;
            } else {
                d_n[3] = d_o[5] + (arr[i] ^ 0) + (arr[i + 1] ^ 1);
                for (int j = 0; j < i / 2; j++) {
                    s_n[3][j] = s_o[5][j];
                }
                s_n[3][i / 2] = 1;
            }

            //更新状态4
            if (d_o[2] + (arr[i] ^ 1) + (arr[i + 1] ^ 1) <= d_o[6] + (arr[i] ^ 0) + (arr[i + 1] ^ 0)) {
                d_n[4] = d_o[2] + (arr[i] ^ 1) + (arr[i + 1] ^ 1);
                for (int j = 0; j < i / 2; j++) {
                    s_n[4][j] = s_o[2][j];
                }
                s_n[4][i / 2] = 0;
            } else {
                d_n[4] = d_o[6] + (arr[i] ^ 0) + (arr[i + 1] ^ 0);
                for (int j = 0; j < i / 2; j++) {
                    s_n[4][j] = s_o[6][j];
                }
                s_n[4][i / 2] = 0;
            }

            //更新状态5
            if (d_o[2] + (arr[i] ^ 0) + (arr[i + 1] ^ 0) <= d_o[5] + (arr[i] ^ 1) + (arr[i + 1] ^ 1)) {
                d_n[5] = d_o[2] + (arr[i] ^ 0) + (arr[i + 1] ^ 0);
                for (int j = 0; j < i / 2; j++) {
                    s_n[5][j] = s_o[2][j];
                }
                s_n[5][i / 2] = 1;
            } else {
                d_n[5] = d_o[6] + (arr[i] ^ 1) + (arr[i + 1] ^ 1);
                for (int j = 0; j < i / 2; j++) {
                    s_n[5][j] = s_o[6][j];
                }
                s_n[5][i / 2] = 1;
            }

            //更新状态6
            if (d_o[3] + (arr[i] ^ 1) + (arr[i + 1] ^ 0) <= d_o[7] + (arr[i] ^ 0) + (arr[i + 1] ^ 1)) {
                d_n[6] = d_o[3] + (arr[i] ^ 1) + (arr[i + 1] ^ 0);
                for (int j = 0; j < i / 2; j++) {
                    s_n[6][j] = s_o[3][j];
                }
                s_n[6][i / 2] = 0;
            } else {
                d_n[6] = d_o[7] + (arr[i] ^ 0) + (arr[i + 1] ^ 1);
                for (int j = 0; j < i / 2; j++) {
                    s_n[6][j] = s_o[7][j];
                }
                s_n[6][i / 2] = 0;
            }

            //更新状态7
            if (d_o[3] + (arr[i] ^ 0) + (arr[i + 1] ^ 1) <= d_o[7] + (arr[i] ^ 1) + (arr[i + 1] ^ 0)) {
                d_n[7] = d_o[3] + (arr[i] ^ 0) + (arr[i + 1] ^ 1);
                for (int j = 0; j < i / 2; j++) {
                    s_n[7][j] = s_o[3][j];
                }
                s_n[7][i / 2] = 1;
            } else {
                d_n[7] = d_o[7] + (arr[i] ^ 1) + (arr[i + 1] ^ 0);
                for (int j = 0; j < i / 2; j++) {
                    s_n[7][j] = s_o[7][j];
                }
                s_n[7][i / 2] = 1;
            }

            for (int j = 0; j < 8; j++) {
                d_o[j] = d_n[j];
                for (int k = 0; k < length; k++) {
                    s_o[j][k] = s_n[j][k];
                }
            }
        }
        int[] codeGet = new int[length / 2 - 3];
        System.arraycopy(s_n[0], 0, codeGet, 0, length / 2 - 3);
        return codeGet;
    }

}
