/**
 * Created by 1 on 21.09.2017.
 */


public class Knapsack {

    public int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public int solution(int W, int[] w, int[] v, int n) {
        int K[][] = new int[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                if (i == 0 || j == 0) {
                    K[i][j] = 0;
                }
               else if (w[i-1] <= j) {
                    K[i][j] = max( v[i-1]+ K[i - 1][j - w[i-1]],K[i - 1][j] );
                } else {
                    K[i][j] = K[i - 1][j];
                }
            }
        }

        return K[n][W];
    }




        //Determines which items were kept in the optimal solution of the knapsack problem





    public static void main(String args[]) {

        int val[] = new int[]{60, 1001, 120,10,10};
        int wt[] = new int[]{10, 201, 30,10,10};
        int  W = 50;
        int n = val.length;
        Knapsack k = new Knapsack();
        System.out.println(k.solution(W, wt, val, n));




    }
}
