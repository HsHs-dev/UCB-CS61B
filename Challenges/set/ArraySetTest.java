public class ArraySetTest {

    public static void main(String[] args) {

        ArraySet<Integer> set = new ArraySet<>();

        set.add(2);
        set.add(2);
        set.add(3);
        set.add(null);
        set.add(5);

        set.print();

        System.out.println(set.contains(2));
        System.out.println(set.size());
        
        
    }
    
}
