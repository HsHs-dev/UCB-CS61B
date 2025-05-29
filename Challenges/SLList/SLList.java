import com.sun.security.jgss.GSSUtil;

public class SLList {

    public IntNode first;

    public SLList(int x) {
        first = new IntNode(x, null);
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public int getFirst() {
        return first.item;
    }

    public void addLast(int x) {

        IntNode node = this.first.next;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new IntNode(x, null);
    }

    public int size() {
        int size = 1;
        IntNode node = first.next;
        while (node != null) {
            size++;
            node = node.next;
        }
        return size;
    }

    public String print() {
        String list = first.item + " -> ";
        IntNode node = first.next;
        while (node.next != null) {
            list += node.item + " -> ";
            node = node.next;
        }
        list += node.item;
        return list;
    }

    public static void main(String[] args) {
        SLList L = new SLList(20);
        L.addFirst(40);
        L.addFirst(80);
        L.addLast(333);
        System.out.println(L.size());
        System.out.println(L.print());
        L.addFirst(454);
        System.out.println(L.size());
        System.out.println(L.print());
    }
    
}