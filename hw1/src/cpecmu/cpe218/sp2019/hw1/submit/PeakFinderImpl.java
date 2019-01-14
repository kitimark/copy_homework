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
        return peakPosition(a, 0, a.length - 1);
    }

    public int peakPosition(int[] a, int head, int tail)
    {
        // find middle position form head and tail
        int pos = head + (tail - head)/2;
        // peak finding algorithm
        if(tail == head) return pos;
        else if(a[pos] >= a[pos-1] && a[pos] >= a[pos+1]) return pos;
        else if(a[pos-1] >= a[pos+1]) return peakPosition(a, head, pos-1);
        else /* a[pos-1] < a[pos+1] */ return peakPosition(a, pos+1, tail);
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
