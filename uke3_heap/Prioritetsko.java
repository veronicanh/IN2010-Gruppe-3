/* Abstakt datatype
    Bryr ikke om implemenatsjon
    Bryr oss kun om funkjsonalitet!
*/

interface Prioritetsko {
    // Plasser element i koen
    public void insert(int x);
    
    // Fjerner og returnerer minste element i koen
    public int remove();
}



/*
Heap
 - Type: min vs max
 Min:
 - minste element ligger i rota
 - hver node v er større enn foreldre-noden
 - komplett 
 - peker til siste node


*/

[1, 2, 3, 4, 5]