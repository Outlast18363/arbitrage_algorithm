import java.io.*;
import java.util.*;
//press 'option' + 'x' to run test cases
//press command + ',' to open template settings
//多选替换："ctrl f" + "ctrl r" + "replace all"
//输入中间如果有完全没有东西的空行的话，不需要手动 in.next() 换行，下次读入会直接跳到下一个有东西的行去

public class Main {
    static FastIO in;
    static HashMap<String, Integer> m = new HashMap<>(); //name of the currency
    static double[][] G;
    static int n, c = 0, tt=0;
    static ArrayList<Edge> edges;
    static class Edge{
        int from, to;
        double weight;
        Edge(int f, int t, double w){
            from = f; to = t; weight = w;
        }
    }

    static long end;
    static int rep = 15; //执行重复次数

    static String type = "sparse";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {


        for(int k = 105; k <= 140; k+=5){ //140-150 dense
            System.out.println("K: " + k);

            for(int x = 0; x < 15; x++) { //Bellman-Ford
                in = new FastIO("/Users/jz/Desktop/算法测试数据/"+type+"_" + k + ".txt", "/Users/jz/Desktop/算法测试数据/out" + k + "B.txt");
                n = in.nextInt();
                tt = 0;
                long start = System.currentTimeMillis();
                Solve2(n);
                end = System.currentTimeMillis();
                System.out.print((end - start) + " ");
                System.out.println(tt+" ");
                in.close();
            }

            System.out.println();

            for(int x = 0; x < 15; x++) { //Floyd
                in = new FastIO("/Users/jz/Desktop/算法测试数据/"+type+"_" + k + ".txt", "/Users/jz/Desktop/算法测试数据/out" + k + "F.txt");
                n = in.nextInt();
                long start = System.currentTimeMillis();
                tt=0;
                Solve1(n);
                end = System.currentTimeMillis();
                System.out.print((end - start) + " ");
                System.out.println(tt+" ");
                in.close();
            }

            System.out.println();
        }
    }

    static void Solve1(int n){
        for (int i = 1; i <= n; i++) {
            String currency = in.next();
            m.put(currency, i);
        }

        G = new double[n + 1][n + 1];
        int nn = in.nextInt();
        for (int i = 0; i < nn; i++) { //建图
            int a = m.get(in.next());
            double ex = in.nextDouble(); //exchange rate
            int b = m.get(in.next());
            G[a][b] = ex; //通过对数将乘法转加法，避免精度损失
        }

        String out = "";
        for (int i = 0; i < rep; i++) {
            out = "Case " + c + ": " + (Floyd() ? "Yes" : "No");
        }
        in.println(out);
    }

    static void Solve2(int n){
        for (int i = 1; i <= n; i++) {
            String currency = in.next();
            m.put(currency, i);
        }

        int nn = in.nextInt(); //number of edges
        edges = new ArrayList<>();
        for (int i = 0; i < nn; i++) { //建图
            int a = m.get(in.next());
            double ex = in.nextDouble(); //exchange rate
            int b = m.get(in.next());
            edges.add(new Edge(a, b, ex));
        }



        for(int i = 1; i <= n; i++){
            edges.add(new Edge(0, i, 0));
        }

        boolean arbitrage = false;
        for (int i = 0; i < rep*2; i++) {
            arbitrage = BellmanFord(0);
        }

        String out = "Case " + c + ": " + (arbitrage ? "Yes" : "No");

//        boolean arbitrage = false;
//        for (int i = 1; i <= n; i++) { //每个点都做一个最短路，如果发现某个点到自己的最长路松弛完一圈后比之前还大，则可以套汇
//            arbitrage |= BellmanFord(i);
//        }
//
//        String out = "Case " + c + ": " + (arbitrage ? "Yes" : "No");

        in.println(out);
    }

    static boolean Floyd(){
        for (int k = 1; k <= n; k++) { //可做桥的新点
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    tt++;
                    if(G[i][j] < G[i][k] * G[k][j]) { //找最长路
                        G[i][j] = G[i][k] * G[k][j];
                    }
                }
                //在第三层 loop 外是因为需要等到所有点都被松弛完
                if(G[i][i] > 1) return true; //自环使得回来时大于 1
            }
        }
        return false;
    }

    static boolean BellmanFord(int s){
        double dis[] = new double[n + 1];
        dis[s] = 1;

        for(int i = 0; i <= n; i++) {
            for (Edge e: edges) { //对每个边进行松弛操作
                int from = e.from, to = e.to;
                double w = e.weight;
                tt++;
                if(dis[from] == 0) continue;
                if(dis[from] * w > dis[to]) {
                    dis[to] = dis[from] * w;
                }
            }
        }

        if(dis[s] - 1.0 > 0) return true;
        return false;
    }
}

class FastIO extends PrintWriter {
    private InputStream stream;
    private byte[] buf = new byte[1 << 16];
    private int curChar, numChars;

    // standard input
    public FastIO() { this(System.in, System.out); }

    public FastIO(InputStream i, OutputStream o) {super(o); stream = i; }

    // file input
    public FastIO(String i, String o) throws IOException { //i: in || o: out
        super(new FileWriter(o)); stream = new FileInputStream(i);
    }

    // throws InputMismatchException() if previously detected end of file
    private int nextByte() {
        if (numChars == -1) { throw new InputMismatchException(); }
        if (curChar >= numChars) { curChar = 0; try { numChars = stream.read(buf);
        } catch (IOException e) { throw new InputMismatchException(); }
            if (numChars == -1) return -1;  // end of file
        } return buf[curChar++];
    }

    // to read in entire lines, replace c <= ' '
    // with a function that checks whether c is a line break
    public String next() {
        int c; do { c = nextByte(); } while (c <= ' ');
        StringBuilder res = new StringBuilder();
        do { res.appendCodePoint(c); c = nextByte(); } while (c > ' ');
        return res.toString();
    }

    public int nextInt() {
        int c, sgn = 1, res = 0;
        do { c = nextByte(); } while (c <= ' ');
        if (c == '-') { sgn = -1; c = nextByte(); }
        do { if (c < '0' || c > '9') { throw new InputMismatchException(); }
            res = 10 * res + c - '0'; c = nextByte(); } while (c > ' ');
        return res * sgn;
    }

    public long nextLong() {
        int c, sgn = 1; long res = 0;
        do { c = nextByte(); } while (c <= ' ');
        if (c == '-') { sgn = -1; c = nextByte(); }
        do { if (c < '0' || c > '9') { throw new InputMismatchException(); }
            res = 10 * res + c - '0'; c = nextByte(); } while (c > ' ');
        return 1l * res * sgn;
    }

    public double nextDouble() { return Double.parseDouble(next()); }
}
