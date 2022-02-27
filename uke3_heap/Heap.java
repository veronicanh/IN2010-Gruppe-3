import java.util.ArrayList;

class Heap implements Prioritetsko {
    private ArrayList<Integer> heap;
    private int n;

    public Heap() {
        heap = new ArrayList<Integer>();
        n = 0;
    }



    public void insert(int x) {
        heap.add(n, x);
        bubbleUp();
        n += 1;
    }

    public void bubbleUp() {
        int i = n;
        // Mens foreldernoden er større enn barnenoden
        // (og i ikke blir 0)
        // (Evt. mens barnet har e forelder som er større)
        int barn = heap.get(i);
        int forelder = heap.get((i - 1) / 2);

        while ((0 < i) && (barn < forelder)) {
            // bytt plass på barn- og forelder-node
            heap.set((i - 1)/2, barn);
            heap.set(i, forelder);
            // i settes til å være indeksen til nåværende
            // forelder (slik at vi kan finne deres forelder)
            // (Dvs. "besteforelderen" til barn-noden per nå)
            i = (i - 1)/2

            barn = heap.get(i);
            forelder = heap.get((i - 1) / 2);
        }
    }


    public int removeMin() {
        int fjernet = heap.get(0);
        n -= 1;
        bubbleDown();
        return fjernet;
    }


    private void bubbleDown() {
        heap.set(0, heap.remove(n));
        int i = 0;
        int barnIndeks = ((2 * i) + 2)
        // Mens vi er innenfor array-storrelsen (gyldige indekser)
        while (barnIndeks < (n - 1)) {
            // Finner ut om j skal vaere indeksen til venstre
            // eller hoyre barn ...
            int j;
            int venstreBarn = heap.get((2 * i) + 1);
            int hoyreBarn = heap.get((2 * i) + 2);
            // ... basert paa stoerrelsen deres. Vi vil ha den
            // minste.
            if (venstreBarn <= hoyreBarn) {
                j = (2 * i) + 1;
            } else {
                j = (2 * i) + 2;
            }

            // Bytter evt. plass
            int minsteBarn = heap.get(j);
            int denne = heap.get(i);
            if (minsteBarn <= denne) {
                // Bytter plass
                heap.set(i, minsteBarn);
                heap.set(j, denne);
                // Videre må vi til indeksen til barnet for å
                // finne noden vår (siden de byttet plass)
                i = j;
            } else {
                // Break, ferdig å boble ned
                i = n;
            }
        }

        // Må ha med denne biten til slutt for å sjekke om vi evt
        // må bytte plass med venstre barn.
        // Om noden har h=1 og kun ett barn, så dekkes ikke det
        // av while-løkken over
        int j = (2 * i) + 1;
        if (j < (n - 1)) {
            int venstreBarn = heap.get(j);
            int denne = heap.get(i);
            // Om foreldern er større
            if (venstreBarn.compareTo(denne) <= 0) {
                // Bytter plass
                heap.set(i, venstreBarn);
                heap.set(j, denne);
            }
        }
    }
}


/*
A = array
n = ant elementer
x = elemntet som skal settes inn

A[0] = rota
A[n - 1] = siste elm

Foreldrenoden til A[i] er på plass [(i - 1)// 2]
Venstre barn til A[i] er på [2i + 1]
Høyre barn til A[i] er på [2i + 2]
*/
}