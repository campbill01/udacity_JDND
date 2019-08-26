import java.util.Stack;

class Main {
  public static void main(String[] args) {
    String string1 = "Hello World!";
    System.out.println(reverseString(string1));
    System.out.println(reverseString("abcde"));
  }
  public static String reverseString(String s) {
    String result = "";
    //char character;
    Stack<Character> reverser = new Stack();
    //iterate over s, pushing chars as strings on to lifo stack
    for(int i = 0; i< s.length(); i++){
      reverser.push((Character)s.charAt(i));
    }
    //iterate over lifo building return string
    for(int j = 0; j < s.length(); j++){
        result = result + reverser.pop();
    }
    return result;
  }
}