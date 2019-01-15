package cpecmu.cpe218.sp2019.hw1.submit;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import cpecmu.cpe218.sp2019.hw1.StableWorkforce;
import cpecmu.cpe218.sp2019.hw1.WorkforcePreferences;
import cpecmu.cpe218.sp2019.hw1.WorkforcePreferencesImpl;

public class StableWorkforceImpl implements StableWorkforce {

    /** input file name */
    protected static final String inFile = "hw1tests/stablewf01.in";

    @Override
    public <C, W> Map<C, Set<W>> stableMatching(WorkforcePreferences<C, W> wp) {
        Map<C, SortedMap<Integer, W>> m = new HashMap<>();

        // Initialize Company in m
        for(C company : wp.companies()){     
            m.put(company, new TreeMap<Integer, W>());
        }

        for(W worker : wp.workers()){
            for(int i = 0; i < wp.companies().size(); i++){

                C company = wp.workerPref(worker, i);
                
                // add worker in company
                m.get(company).put(wp.companyRank(company, worker), worker);
                
                // if company has workers too much
                if(wp.capacity(company) < m.get(company).size()){
                    //low priority worker is fired
                    worker = m.get(company).get(m.get(company).lastKey());
                    m.get(company).remove(m.get(company).lastKey());
                    
                    i = wp.workerRank(worker, company);
                    // that worker will find other company
                    continue;
                }
                // Otherwise (no one is fired)
                break;
            }
        }

        Map<C, Set<W>> map = new HashMap<>();
        for(C company : wp.companies()){
            map.put(company, new HashSet<W>((m.get(company).values())));
        }
        return map;
    }

    public static void main(String[] args) {
        try (FileReader fr = new FileReader(inFile);
             Scanner s = new Scanner(fr)) {
            // read capacities and preferences
            int nc = s.nextInt();
            Map<String, Integer> capacity = new LinkedHashMap<>();
            for (int i = 0; i < nc; i++)
                capacity.put(s.next(), s.nextInt());
            int nw = s.nextInt();
            Map<String, List<String>> wPrefs = new LinkedHashMap<>();
            for (int i = 0; i < nw; i++) {
                List<String> pref = new ArrayList<>(nc);
                String w = s.next();
                for (int j = 0; j < nc; j++)
                    pref.add(s.next());
                wPrefs.put(w, pref);
            }
            Map<String, List<String>> cPrefs = new LinkedHashMap<>();
            for (int i = 0; i < nc; i++) {
                List<String> pref = new ArrayList<>(nw);
                String c = s.next();
                for (int j = 0; j < nw; j++)
                    pref.add(s.next());
                cPrefs.put(c, pref);
            }
            // construct problem instance
            WorkforcePreferences<String, String> wp =
                    new WorkforcePreferencesImpl<>(capacity, cPrefs, wPrefs);

            // invoke algorithm
            StableWorkforce sbm = new StableWorkforceImpl();
            Map<String, Set<String>> res = sbm.stableMatching(wp);
            for (Map.Entry<String, Set<String>> e : res.entrySet()) {
                System.out.print(e.getKey() + ":");
                for (String w : e.getValue())
                    System.out.print(" " + w);
                System.out.println();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
