class Node:
    def __init__(self, data):
        self.data = data
        self.besokt = False
        self.inDeg = 0
        self.naboer = []

    def __str__(self):
        penStr = str(self.data) + ": ["
        if (len(self.naboer) != 0):
            for kant in self.naboer:
                penStr += str(kant.data) + ", "
            penStr = penStr[:len(penStr) - 2]  #fjerner trailing komma
        return penStr + "]"

class Graf:
    def __init__(self, rettet):
        self.graf = {}
        self.rettet = rettet

    def leggTilKant(self, u, v):
        uNode = self.hentNode(u)
        vNode = self.hentNode(v)

        if (self.rettet):
            uNode.naboer.append(vNode)
            vNode.inDeg += 1
        else:
            uNode.naboer.append(vNode)
            vNode.naboer.append(uNode)

    def hentNode(self, data):
        if (data not in self.graf):
            self.graf[data] = Node(data)
        return self.graf[data]

    def __str__(self):
        penStr = ""
        for v in self.graf:
            penStr += str(self.graf[v]) + "\n"
        return penStr

    def settNodeStatus(self, boolean):
        for data in self.graf:
            node = self.graf[data]
            node.besokt = boolean

    # Dybde-først søk
    def DFS(self, data):
        print("DFS: ", end="")
        self.settNodeStatus(False)
        startNode = self.graf[data]
        self.DFS_iter(startNode)
        print("ferdig")

    def DFS_iter(self, startNode):
        print(str(startNode.data) + " -> ", end="")
        startNode.besokt = True
        for nabo in startNode.naboer:
            if nabo.besokt == False:
                self.DFS_iter(nabo)

    # Sørger for alle noder blir besøkt ved DFS dersom vi har flere komponenter
    def DFS_full(self):
        print("DFS_FULL")
        self.settNodeStatus(False)

        for data in self.graf:
            node = self.graf[data]
            if node.besokt == False:
                print("|", end="")
                self.DFS(node.data)

    # Bredde-først søk
    def BFS(self, data):
        print("BFS: ", end="")
        self.settNodeStatus(False)

        queue = []

        startNode = self.graf[data]
        startNode.besokt = True
        queue.append(startNode)

        while (len(queue) != 0):
            node = queue.pop(0)
            print(str(node.data) + " -> ", end="")
            for nabo in node.naboer:
                if (nabo.besokt == False):
                    nabo.besokt = True
                    queue.append(nabo)
        print("ferdig")

    def topologiskSortering(self):
        if (not self.rettet):
            print("ERROR: Kan ikke topologisk sortere en utrettet graf")
            return
        print("Topoogisk sortering: ")

        stack = []
        # Legger alle noder med 0 inngående kanter på stacken
        for data in self.graf:
            node = self.graf[data]
            if (node.inDeg == 0):
                stack.append(node)

        i = 0  # Teller for antall elementer vi "utfører"
        output = []
        while (len(stack) != 0):
            node = stack.pop()
            print(str(node.data) + " -> ", end="")
            output.append(node)
            i += 1

            for nabo in node.naboer:
                nabo.inDeg -= 1        # Minsker inDeg for alle naboene til v,
                if (nabo.inDeg == 0):  # legger dem til på stacken om de får inDeg = 0
                    stack.append(nabo)

        if (i == len(self.graf)):
            print("ferdig")
            return output
        # Om vi ikke har utført like mange elemeter som det er i grafen, så har vi en syklus
        else:
            print("*STOP!* Grafen har en syklus")


def main():
    foilGraf = Graf(False)
    foilGraf.leggTilKant("A", "D")
    foilGraf.leggTilKant("E", "F")
    foilGraf.leggTilKant("D", "E")
    foilGraf.leggTilKant("F", "G")
    foilGraf.leggTilKant("A", "B")
    foilGraf.leggTilKant("A", "C")
    foilGraf.leggTilKant("B", "C")
    foilGraf.leggTilKant("C", "D")
    foilGraf.leggTilKant("C", "F")
    foilGraf.leggTilKant("X", "Y")
    foilGraf.leggTilKant("X", "Z")
    foilGraf.leggTilKant("Y", "Z")
    print(foilGraf)

    foilGraf.DFS("A")
    foilGraf.DFS_full()
    foilGraf.BFS("A")

    morgenRutine = Graf(True)
    morgenRutine.leggTilKant("Dusj", "Undertøy")
    morgenRutine.leggTilKant("Dusj", "Sokker")
    morgenRutine.leggTilKant("Dusj", "Bukse")
    morgenRutine.leggTilKant("Dusj", "T-skjorte")

    morgenRutine.leggTilKant("Undertøy", "Bukse")
    morgenRutine.leggTilKant("Sokker", "Sko")
    morgenRutine.leggTilKant("Bukse", "Jakke")
    morgenRutine.leggTilKant("Bukse", "Sko")
    morgenRutine.leggTilKant("T-skjorte", "Jakke")

    morgenRutine.leggTilKant("Jakke", "Dra")
    morgenRutine.leggTilKant("Bukse", "Dra")
    morgenRutine.leggTilKant("Sko", "Dra")

    morgenRutine.leggTilKant("Frokost", "Pusse tenner")
    morgenRutine.leggTilKant("Frokost", "Dra")
    morgenRutine.leggTilKant("Pusse tenner", "Dra")

    # Skaper sykler
    morgenRutine.leggTilKant("Sko", "Sokker")
    morgenRutine.leggTilKant("Dra", "Dusj")

    print(morgenRutine)
    morgenRutine.topologiskSortering()

main()
