import java.util.ArrayList;
import java.util.Arrays;
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
    String part = new String();
    for(int i = 0; i < equation.length(); i++){
      part = String.valueOf(equation.charAt(i));
      equationParts.push(part);
    }
    int result=0;
    String temp=null;
    String num1=null;
    String num2=null;
    String operator=null;
    while(!equationParts.empty()){
      temp = equationParts.pop();
      if(isNumber(temp) && !(String.valueOf(result).equals(num1))){
        num1 = temp;
       // System.out.println("num1 and temp are " + num1 + " " + temp);
        if(result == 0){
          result = Integer.parseInt(num1);
        }
      }else if(isNumber(temp)){
        //System.out.println("Setting num2");
        num2 = temp;
      }else{
        //System.out.println("Setting operator to: " + temp);
        operator = temp;
      }
      if(num2 != null){
        //System.out.println("Setting result");
        result = doMath(num1,num2, operator);
        num2 = null;
        num1 = String.valueOf(result);
      }
    }
    return result; 
  }
  public static boolean isNumber(String temp) {
    String[] stringNumbers = {"0","1","2","3","4","5","6","7","8","9"};
    if(Arrays.asList(stringNumbers).contains(temp)){
      return true;
    }
    return false;
  }
  public static int doMath(String num1, String num2,  String operator) {
    //System.out.println("I am in do Math...operator is: " + operator);
    int result;
    if(operator.equals("+")){
      //System.out.println(operator);
      result = Integer.parseInt(num1) + Integer.parseInt(num2);
      //System.out.println("Adding ..." + result); 
      return result;
    } else if( operator.equals("-")) {
      result = Integer.parseInt(num1) - Integer.parseInt(num2);
      // System.out.println("Subtracting ..." + result); 
      return result;
    }else{
      result = Integer.parseInt(num1) * Integer.parseInt(num2);
       //System.out.println("Multiplying ..." + result); 
      return result;
    }
  }
}