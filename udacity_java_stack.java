import java.util.Stack;

class Main {
  public static void main(String[] args) {
    String equation = new String();
    String equation2 = new String();
    equation = "1+2";
    equation2 = "1+2*5";
    System.out.println(calculate(equation));
    System.out.println(calculate(equation2));
  }

  public static int calculate(String equation) {
    Stack<String> equationParts = new Stack<>();
    //System.out.println("equation is " + equation.length() + " chars long.");
    String part = new String();
    for(int i = 0; i < equation.length(); i++){
      part = String.valueOf(equation.charAt(i));
      equationParts.push(part);
    }
    //System.out.println("The equationParts stack is now " + equationParts );
    return 4; 
  }
}