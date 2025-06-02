public class SLListLauncher {

    public static void main(String[] args) {

        SLList<String> list = new SLList<>("Hassan");
        list.addLast("Siddig");
        list.addLast("Mahmoud");

        System.out.println(list.print());
    }
}
