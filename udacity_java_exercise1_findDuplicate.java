import java.util.TreeMap;

class Main{  
  public static void main(String args[]){  
    String data = "abcabc";
    String data2 = "abcd";
    System.out.println(findDuplicate(data));
    System.out.println(findDuplicate(data2));
  }
  public static int findDuplicate(String input) {
    int output=-1;
    TreeMap<String, Integer> strings = new TreeMap();
    for (int i = 0; i < input.length(); i++){
      String c = Character.toString(input.charAt(i));     if(strings.containsKey(c)){
        //first duplicate, note the position
        output = i;
        break;
      }else{
        strings.put(c, 1);
      }
    }
    
    return output;
  }
}