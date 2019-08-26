import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Main {
  public static void main(String[] args) {
    Integer[] test = {-1, 15, 59, 22, 6, 42, 45, 0};
    Integer[] test2 = {5, 10, 22, 100, 8};
    Integer range = 4;
    Integer range2 = 2;
    System.out.println(topKLarger(test, range));
    System.out.println(topKLarger(test2, range2));
  }

  public static ArrayList<Integer> topKLarger(Integer[] list, Integer range){
    ArrayList<Integer> result = new ArrayList<>();
    Arrays.sort(list);
    for(int i = list.length - range; i< list.length; i++){
      result.add(list[i]);
    }
    // Collections.sort(result);
    return result;
  }
}