public class IntList {
    public int first;
    public IntList rest;        

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    public int size() {

        int size = 1;
        if (this.rest == null) return size;
        return size + rest.size();

        
    }

    public int iterativeSize() {
        int size = 1;
        IntList currentRest = this.rest;
        while (currentRest != null) {
            size++;
            currentRest = currentRest.rest;
        }
        return size;
    }

    public int iterativeGet(int i) {
        int element = this.first;
        int index = i;
        int count = 0;
        IntList currentNode = this;
        while (currentNode != null) {
            if (count == index){
                element = currentNode.first;
                break;
            }
            count++;
            currentNode = currentNode.rest;
        }
        return element;
    }

    public int get(int i) {
        if (i == 0) return first;
        return rest.get(i - 1);
    }

    public static void main(String[] args) {
        IntList L = new IntList(15, null);
        L = new IntList(10, L);
        L = new IntList(5, L);

        System.out.println(L.size());
        System.out.println(L.iterativeSize());
        // System.out.println(L.iterativeGet(2));
        System.out.println(L.get(2));


    }

}
