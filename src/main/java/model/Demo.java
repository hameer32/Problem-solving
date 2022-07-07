package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Demo {
    static {
        main(new String[0]);
    }
    public static void main(String[] args) {
        System.out.println("Class -x");
    }


    public static void main1(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        int T = Integer.parseInt(br.readLine().trim());
        for (int t_i = 0; t_i < T; t_i++) {
            String[] line = br.readLine().split(" ");
            int N = Integer.parseInt(line[0]);
            int M = Integer.parseInt(line[1]);
            int[][] mat = new int[N][M];
            for (int i_mat = 0; i_mat < N; i_mat++) {
                String[] arr_mat = br.readLine().split(" ");
                for (int j_mat = 0; j_mat < arr_mat.length; j_mat++) {
                    mat[i_mat][j_mat] = Integer.parseInt(arr_mat[j_mat]);
                }
            }

            int out_ = solve(N, M, mat);
            System.out.println(out_);

        }

        wr.close();
        br.close();
    }
//    10
//            4 2
//            288 1692
//            1044 2523
//            1581 850
//            110 315
//            2 1
//            2190
//            185
//            3 5
//            620 1260 480 1360 720
//            1080 108 828 2124 2520
//            1768 2444 1482 312 1118
//            5 5
//            99 25 16 71 14
//            270 920 810 570 740
//            1260 1420 1940 1640 120
//            286 935 308 407 66
//            141 90 42 174 75
//            5 5
//            1581 2728 279 2387 2449
//            623 595 28 364 385
//            100 33 61 77 69
//            1480 481 999 3219 3515
//            40 96 71 35 79
//            3 3
//            10 405 435
//            756 1188 1620
//            405 180 369
//            3 2
//            836 110
//            588 1428
//            114 196
//            2 4
//            275 319 792 363
//            180 24 120 426
//            1 1
//            1067
//            4 1
//            364
//            154
//            672
//            1360


//    4
//            2
//            5
//            4
//            3
//            3
//            3
//            3
//            1
//            4

    static int solve(int N, int M, int[][] mat) {
        int result = 0;
        int[] rowGCD=new int[N];
        int[] colGCD=new int[M];
        for(int i=0;i<N;i++){
            rowGCD[i]=gcdArray(mat[i]);
        }
        for( int i=0;i<M;i++){
            int[] temp=new int[N];
            for(int j=0;j<N;j++){
                    temp[j]=mat[j][i];
            }
            colGCD[i]=gcdArray(temp);
        }
        // Got 2 Arrays of respective GCD's


        return result;

    }

    static int gcdArray(int[] arr) {
        int result = gcd(arr[0], arr[1]);
        for (int i = 2; i < arr.length; i++) {
            result = gcd(result, arr[i]);
        }
        return result;
    }

    static int gcd(int a, int b) {
        if (a == 0)
            return b;
        if (b == 0)
            return a;
        if (a == b)
            return a;
        if (a > b)
            return gcd(a - b, b);
        return gcd(a, b - a);
    }

}
