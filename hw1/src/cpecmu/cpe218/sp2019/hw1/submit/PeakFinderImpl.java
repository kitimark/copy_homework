package cpecmu.cpe218.sp2019.hw1.submit;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import cpecmu.cpe218.sp2019.hw1.PeakFinder;

public class PeakFinderImpl implements PeakFinder {

    /** input file name */
    protected static final String inFile = "hw1tests/peakfinder01.in";

    @Override
    public int peakPosition(int[] a) {
        int pos = a.length/2;
        int head = 0;
        int tail = a.length-1;
        return k(a, pos, head, tail);
    }

    public int k(int[] x, int pos, int head, int tail)
    {
        if(tail == head) return pos;
        else if(x[pos] >= x[pos-1] && x[pos] >= x[pos+1]) return pos;
        else if(x[pos-1] >= x[pos+1]) return k(x, ((pos-1-head)/2)+head, head, pos-1);
        else if(x[pos-1] <= x[pos+1]) return k(x, ((tail-pos+1)/2)+pos, pos+1, tail);
        return -1;
    }

    public static void main(String[] args) {
        try (FileReader fr = new FileReader(inFile);
             Scanner s = new Scanner(fr)) {
            int n = s.nextInt();
            // read array
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();

            // invoke algorithm
            PeakFinder sbm = new PeakFinderImpl();
            int res = sbm.peakPosition(a);
            System.out.println(res);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
