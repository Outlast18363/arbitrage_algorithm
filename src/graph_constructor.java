import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) throws IOException{
        for(int k = 1; k <= 31; k+=5) // 1-31 or 105-140
        {
            ArrayList<String[]> currencyList = new ArrayList();
            FastIO in = new FastIO("/Users/jz/Downloads/exchange_rates.csv", "/Users/jz/Desktop/算法测试数据/sparse_"+k+".txt");
            int n = 3; //取第几天的
            int ceil = k; //只取前几个 currency
            //in.println(in.nextLine());

            for(int i = 0; i <= 150 * (n - 1); i++) in.nextLine();
            int c = 1;
            HashMap<Integer, String> name = new HashMap<>();
            for (int i = 150 * (n - 1); i < 150 * n; i++) {
                String ss = in.nextLine();
                if ((i % 150) > (ceil - 1)) continue;
                String s[] = ss.split(",");
                currencyList.add(s);
                int si = currencyList.size() - 1 < 0 ? 0 : currencyList.size() - 1;
                name.put(c++, currencyList.get(si)[2]);
                //in.println(currencyList.get(si)[2] +" "+ currencyList.get(si)[3]);
            }

            in.println(name.size());
            for (String values : name.values()) {
                in.println(values);
            }

            String out = "";
            int cnt = 0;
//            for (int i = 0; i < currencyList.size(); i++) { //dense graph
//                for (int j = i + 1; j < currencyList.size(); j++) {
//                    double exRate = Double.parseDouble(currencyList.get(i)[3]) / Double.parseDouble(currencyList.get(j)[3]); //i 到 j 的汇率
//                    double iExRate = Double.parseDouble(currencyList.get(j)[3]) / Double.parseDouble(currencyList.get(i)[3]);
//                    int rr = (int) (exRate*10000); exRate = 1.0*rr/10000;
//                    rr = (int) (iExRate*10000); iExRate = 1.0*rr/10000;
//                    out += currencyList.get(i)[2] + " " + exRate + " " + currencyList.get(j)[2] + "\n";
//                    out += currencyList.get(j)[2] + " " + iExRate + " " + currencyList.get(i)[2] + "\n";
//                    cnt += 2;
//                }
//            }

            for (int i = 1, startNode = 0; i < currencyList.size(); i++) { //sparse graph
                double exRate = Double.parseDouble(currencyList.get(i)[3]) / Double.parseDouble(currencyList.get(startNode)[3]); //i 到 j 的汇率
                double iExRate = Double.parseDouble(currencyList.get(startNode)[3]) / Double.parseDouble(currencyList.get(i)[3]);
                int rr = (int) (exRate*10000); exRate = 1.0*rr/10000;
                rr = (int) (iExRate*10000); iExRate = 1.0*rr/10000;
                out += currencyList.get(i)[2] + " " + exRate + " " + currencyList.get(startNode)[2] + "\n";
                out += currencyList.get(startNode)[2] + " " + iExRate + " " + currencyList.get(i)[2] + "\n";
                cnt += 2;
            }
            in.println(cnt + "\n" + out);
            in.close();
        }
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
    public String nextLine() {
        int c; do { c = nextByte(); } while (c == '\n');
        StringBuilder res = new StringBuilder();
        do { res.appendCodePoint(c); c = nextByte(); } while (c != '\n');
        return res.toString();
    }

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
