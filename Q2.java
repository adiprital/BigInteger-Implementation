import javax.swing.*;
import java.util.ArrayList;

public class Q2 {
    public static void main(String args[]) {

        String number1 = null;
        BigInt b1 = null;
        String number2 = null;
        BigInt b2 = null;

        while (number1 == null) {
            try { // checks code for exceptions
                number1 = JOptionPane.showInputDialog("Enter first number: ");
                if (number1 == null){
                    break;
                }
                else{
                    b1 = new BigInt(number1);
                }
            }
            catch (IllegalArgumentException e) { // if an exception appears prints message below
                System.err.println("Please enter a number! " + e.getMessage());
                number1 = null;
            }
        }

        while ((number2 == null)&&(number1 != null)) {
            try { // checks code for exceptions
                number2 = JOptionPane.showInputDialog("Enter second number: ");
                if (number2 == null){
                    break;
                }
                else{
                    b2 = new BigInt(number2);
                }
            }
            catch (IllegalArgumentException e) { // if an exception appears prints message below
                System.err.println("Please enter a number! " + e.getMessage());
                number2 = null;
            }
        }

        if ((number1 != null)&&(number2 != null)) {

            System.out.println(b1.toString() + " + " + b2.toString() + " = " + b1.plus(b2).toString());
            System.out.println(b2.toString() + " + " + b1.toString() + " = " + b2.plus(b1).toString());

            System.out.println(b1.toString() + " - " + b2.toString() + " = " + b1.minus(b2).toString());
            System.out.println(b2.toString() + " - " + b1.toString() + " = " + b2.minus(b1).toString());

            System.out.println(b1.toString() + " * " + b2.toString() + " = " + b1.multiply(b2).toString());
            System.out.println(b2.toString() + " * " + b1.toString() + " = " + b2.multiply(b1).toString());

            System.out.println(b1.toString() + " / " + b2.toString() + " = " + b1.divide(b2).toString());
            System.out.println(b2.toString() + " / " + b1.toString() + " = " + b2.divide(b1).toString());

            int c = b1.compareTo(b2);
            if (c > 0) {
                System.out.println("the result of compereTo is: " + c + ", it means that " + b1.toString() + " is bigger then " + b2.toString());
            }
            if (c < 0) {
                System.out.println("the result of compereTo is: " + c + ", it means that " + b2.toString() + " is bigger then " + b1.toString());
            }
            if (c == 0) {
                System.out.println("the result of compereTo is: " + c + ", it means that " + b2.toString() + " and " + b1.toString() + " are equals. ");
            }

            boolean isEqual = b1.equals(b2);
            if (isEqual) {
                System.out.println(b2.toString() + " and " + b1.toString() + " are equals. ");
            } else {
                System.out.println(b2.toString() + " and " + b1.toString() + " are NOT equals. ");
            }

        }

    }

}
