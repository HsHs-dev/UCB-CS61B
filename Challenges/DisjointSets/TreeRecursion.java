public class TreeRecursion {

  public static int f3(int n) {

    System.out.println("n is " + n);
    if (n <= 1) return 1;

    return f3(n-1) + f3(n-1);
  }

  public static void main(String[] args) {

    System.out.println(f3(5));
  }

  // 3 -> 7
  // 4 -> 15
  // 5 -> 31

}
