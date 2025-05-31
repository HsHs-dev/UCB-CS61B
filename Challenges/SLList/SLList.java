public class SLList {

//    private IntNode first;
    /** The first item if it exists, it will be in sentinel.next */
    private IntNode sentinel;
    private int size;

    public SLList() {
        sentinel = new IntNode(7, null);
        size = 0;
    }


    public SLList(int x) {
        sentinel = new IntNode(7, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size++;
    }

    public int getFirst() {
        return sentinel.next.item;
    }

    public void addLast(int x) {
        size++;
        // ugly
//        if (first == null) {
//            first = new IntNode(x, null);
//            return;
//        }
        IntNode node = sentinel;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new IntNode(x, null);
    }
    /*
    @source JHug
    public void addLast(int x) {
      IntNode node = first;
      while (node.next != null) {
        node = node.next;
      }
      node.next = new IntNode(x, null);
    }
    */

    // O(1)
    public int size() {
        return this.size;
    }

    // O(n)
    // public int size() {
    //     int size = 1;
    //     IntNode node = first.next;
    //     while (node != null) {
    //         size++;
    //         node = node.next;
    //     }
    //     return size;
    // }

    /*
    @source JHug
    // Returns the size of list that starts at IntNode node.
    private static int size(IntNode node) {
      if (node.next == null) return 1;
      return 1 + size(node.next);
    }

    public int size() {
      return size(first);
    }
    */

    public String print() {
        String list = sentinel.next.item + " -> ";
        IntNode node = sentinel.next.next;
        while (node.next != null) {
            list += node.item + " -> ";
            node = node.next;
        }
        list += node.item;
        return list;
    }

    public static void main(String[] args) {
        SLList L = new SLList();
        L.addLast(7);
        L.addFirst(66);

        System.out.println(L.print());
        System.out.println(L.size);
    }
    
}
