import java.util.ArrayList;
public class BigInt implements Comparable {

    protected String num;
    protected ArrayList<Character> bigInt;

    public BigInt(String n){
        bigInt = new ArrayList<>();
        this.num = n;
        for (int i=0; i<this.num.length(); i++){
            char c = this.num.charAt(i);
            bigInt.add(c);
        }
        boolean isValid = this.checkValidity();
        if (!isValid){
            throw new IllegalArgumentException("Number must be digits only!");
        }
        if(this.bigInt.get(0)==43){ // first char is +
            this.bigInt.remove(0);
            this.num = this.num.substring(1);
        }
    }

    public String getStringBigInt(){
        return this.num;
    }

    public ArrayList<Character> getBigInt(){
        return this.bigInt;
    }

    public void setBigInt(ArrayList<Character> arr){
        this.bigInt = arr;
    }

    public void setNum(String str){
        this.num = str;
    }


    public BigInt plus(BigInt b1){
        String num1 = this.num;
        String num2 = b1.getStringBigInt();
        String result = "";
        int digit1 = 0;
        int digit2 = 0;
        int num1Len = num1.length() - 1;
        int num2Len = num2.length() - 1;
        int carry = 0;

        if (isMinus(b1) && !isMinus(this)) { // first char of argument is '-' and in this there is no '-'
            BigInt temp = new BigInt(b1.getStringBigInt().substring(1));
            return this.minus(temp);
        }

        if (isMinus(this) && !isMinus(b1)) { // first char of this is '-' and in argument there is no '-'
            BigInt temp = new BigInt(this.num.substring(1));
            return b1.minus(temp);
        }

        if (isMinus(this) && isMinus(b1)) { // first char of this is '-' and first char of argument is '-'
            BigInt temp1 = new BigInt(this.num.substring(1));
            BigInt temp2 = new BigInt((b1.getStringBigInt()).substring(1));
            return addMinus(temp1.plus(temp2));
        }

        for (int i = num1Len, j = num2Len; i >= 0 || j >= 0 || carry != 0; i--, j--) {
            if(i < 0){ // num1 don't have digits anymore.
                digit1 = 0;
            }
            else{ // num1 still have digits
                digit1 = Integer.parseInt(Character.toString(num1.charAt(i)));
            }
            if(j < 0){ // num2 don't have digits anymore.
                digit2 = 0;
            }
            else{ // num2 still have digits
                digit2 = Integer.parseInt(Character.toString(num2.charAt(j)));
            }

            int digit = digit1 + digit2 + carry;
            if (digit > 9) {
                carry = 1;
                digit -= 10;
            } else {
                carry = 0;
            }

            result+=String.valueOf(digit);
        }
        String revRes = reverse(result);
        return new BigInt(revRes);
    }

    public BigInt minus(BigInt b2){
        String[] whosBigger = bigger(this.num, b2.getStringBigInt());
        char[] greater = whosBigger[0].toCharArray();
        char[] smaller = whosBigger[1].toCharArray();

        boolean isNeg = false;  // if the result must be negative
        if (!whosBigger[0].equals(this.num)){
            isNeg = true;
        }

        char[] result = new char[greater.length];
        int index = 1;
        int carry = 0;
        int digit;
        int greatLen = greater.length;
        int smallLen = smaller.length;
        int currDigitGreater = 0;
        int currDigitSmaller = 0;

        if (isMinus(b2) && !isMinus(this)) { // first char of argument is '-' and in this there is no '-'
            BigInt temp = new BigInt(b2.getStringBigInt().substring(1));
            return this.plus(temp);
        }

        if (isMinus(this) && !isMinus(b2)) { // first char of this is '-' and in argument there is no '-'
            BigInt temp = new BigInt(this.num.substring(1));
            return addMinus(b2.plus(temp));
        }

        if (isMinus(this) && isMinus(b2)) { // first char of this is '-' and first char of argument is '-'
            BigInt temp1 = new BigInt((b2.getStringBigInt()).substring(1));
            return temp1.plus(this);
        }

        for ( int i = index; i <= smallLen; i++){
            currDigitGreater =  Character.getNumericValue(greater[greatLen-index]);
            currDigitSmaller = Character.getNumericValue(smaller[smallLen-index]);
            digit = currDigitGreater - currDigitSmaller - carry;
            if(digit<0){
                carry = 1;
            }
            else
                carry = 0;

            if (carry == 1){
                digit = digit+10;
            }
            result[greatLen - index] = Character.forDigit(digit, 10);
            index++;
        }

        for (int i = index; i <= greatLen; i++){
            digit = Character.getNumericValue(greater[greatLen-index]) - carry;
            if(digit<0){
                carry = 1;
            }
            else
                carry = 0;

            if (carry == 1){
                digit = digit+10;
            }
            result[greatLen - index] = Character.forDigit(digit, 10);
            index++;
        }
        String strRes = deleteLeadingZeroes(String.valueOf(result));
        if (isNeg) {
            strRes = "-" + strRes;
        }
        return new BigInt(strRes);
    }

    public BigInt multiply(BigInt b3){
        int carry = 0;
        String unit = ""; // multiply the current sum by 10 in every iteration of the loop
        ArrayList<BigInt> lstToSum = new ArrayList<>();
        int num1Len = this.num.length()-1;
        int num2Len = b3.getStringBigInt().length()-1;

        if (isMinus(this) && isMinus(b3)) { // first char of this is '-' and first char of argument is '-'
            BigInt temp1 = new BigInt((b3.getStringBigInt()).substring(1));
            BigInt temp2 = new BigInt(this.num.substring(1));
            return temp1.multiply(temp2);
        }

        if (isMinus(this) && !isMinus(b3)) { // first char of this is '-' and in argument there is no '-'
            BigInt temp = new BigInt(this.num.substring(1));
            return addMinus(b3.multiply(temp));
        }

        if (!isMinus(this) && isMinus(b3)) { // first char of argument is '-' and in this there is no '-'
            BigInt temp = new BigInt(b3.getStringBigInt().substring(1));
            return addMinus(this.multiply(temp));
        }

        for (int i = num2Len; i>=0; i--){
            char cArg = b3.getStringBigInt().charAt(i);
            int currDig1 = Character.getNumericValue(cArg);
            ArrayList<Integer> currMul = new ArrayList<>();
            for (int j = num1Len; j>=0; j--){
                char cThis = this.num.charAt(j);
                int currDig2 = Character.getNumericValue(cThis);
                int mul = (currDig1*currDig2)+carry;
                if (mul < 10) {
                    currMul.add(mul);
                }
                else{
                    currMul.add(mul%10);
                }
                carry = mul/10;
            }
            currMul.add(carry);
            carry = 0;
            String s = "";
            for(int k = currMul.size()-1; k>=0; k--){  // revers mul
                s +=String.valueOf(currMul.get(k));
            }
            String currRes = addZeros(s,unit);
            unit += "0"; // unit = unit * 10
            String deletedZerosRes = deleteLeadingZeroes(currRes);
            lstToSum.add(new BigInt(deletedZerosRes));
        }
        BigInt res = new BigInt("0");
        for (BigInt b: lstToSum){
            res = res.plus(b);
        }
        return res;
    }

    public BigInt divide(BigInt b4){
        int count = 0;
        BigInt numerator = new BigInt(this.num);
        BigInt denominator = new BigInt(b4.getStringBigInt());
        BigInt sum = new BigInt(denominator.getStringBigInt());
        if (denominator.getStringBigInt().equals("0")){
            throw new ArithmeticException("Cannot divide by zero. ");
        }

        if (isMinus(this) && isMinus(b4)) { // first char of this is '-' and first char of argument is '-'
            BigInt temp1 = new BigInt((b4.getStringBigInt()).substring(1));
            BigInt temp2 = new BigInt(this.num.substring(1));
            return temp2.divide(temp1);
        }

        if (isMinus(this) && !isMinus(b4)) { // first char of this is '-' and in argument there is no '-'
            BigInt temp = new BigInt(this.num.substring(1));
            return addMinus(temp.divide(b4));
        }

        if (!isMinus(this) && isMinus(b4)) { // first char of argument is '-' and in this there is no '-'
            BigInt temp = new BigInt(b4.getStringBigInt().substring(1));
            return addMinus(this.divide(temp));
        }
        while (sum.compareTo(numerator) <= 0){  // sum<=numerator
            count++;
            sum = sum.plus(denominator);  // sum += denominator;
        }
        return new BigInt(String.valueOf(count));
    }

    public boolean equals (Object o){
        boolean res = true;
        String num1 =  this.num;
        BigInt b = (BigInt) o;
        String num2 = b.getStringBigInt();
        int num1Len = num1.length();
        int num2Len = num2.length();

        if (isMinus(this) && !isMinus(b)) { // first char of this is '-' and in argument there is no '-'
            res = false;
        }

        if (!isMinus(this) && isMinus(b)) { // first char of argument is '-' and in this there is no '-'
            res = false;
        }

        if (num1Len > num2Len){
            res = false;
        }
        else if (num2Len > num1Len){
            res = false;
        }
        else {  // num2Len = num1Len , they might be equals
            for (int i = 0; i<num1Len; i++){
                if (num1.charAt(i)!=num2.charAt(i)){
                    res = false;
                }
            }
        }
        return res;
    }

    public String toString() {
        String res = "";

        for (Character i : this.bigInt) {
            res += i;
        }
        return res;
    }

    public int compareTo(Object o) {
        String num1 =  this.num;
        BigInt b = (BigInt) o;
        String num2 = b.getStringBigInt();
        String[] arr = bigger(num1,num2);

        if ((num1.equals(arr[0])) && !(num2.equals(arr[0]))) {  // this is bigger
            return 1;
        }
        else if ((num2.equals(arr[0])) && !(num1.equals(arr[0]))) {  // argument is bigger
            return -1;
        }
        else{ // this = argument
            return 0;
        }
    }


    private boolean isMinus(BigInt b){ // check if the first char is '-'
        if (b.getBigInt().get(0) == 45) {
            return true;
        }
        return false;
    }

    private BigInt addMinus(BigInt b){
        if (!b.getStringBigInt().equals("0")){   // if b is NOT representing the number 0: add '-' to the BigInt b
            b.setNum("-" + b.getStringBigInt());   // add '-' to the string field
            ArrayList<Character> lst = b.getBigInt();
            lst.add(0, '-');   // add '-' to the list
            b.setBigInt(lst);           // add set the list field to the new list with the '-' to b
            return b;
        }
        else {       // if b represents the number 0, don't add '-' to the BigInt b
            return b;
        }
    }
    
    private boolean isDigit(char c){ // //return true if there are only digits chars in the input string.
        boolean res = true;
        if (c < 48 || c > 57){
            res = false;
        }
        return res;
    }

    private boolean checkValidity(){ //Check if a given string is a valid number
        boolean res = true;
        for (int i=0; i<this.num.length(); i++){
            char c = this.num.charAt(i);
            if (i == 0){  // in the beginning of the number
                if (c!=43 && c!=45 ) { // if char is NOT '+' or '-'
                    if (!isDigit(c)) { // if it is not a digit
                        res = false;
                        break;
                    }
                }
            }
            else {// in every index of the number except 0
                if (!isDigit(c)) {
                    res = false;
                }
            }
        }
        return res;
    }

    private String reverse(String n){
        String res = "";
        for (int i=n.length()-1; i>=0; i--){
            res+=n.charAt(i);
        }
        return res;
    }

    private String[] bigger(String num1, String num2){

        BigInt b1 = new BigInt(num1);
        BigInt b2 = new BigInt(num2);
        int num1Len = num1.length();
        int num2Len = num2.length();
        String[] res = {num1,num2};
        boolean b1Minus = isMinus(b1);
        boolean b2Minus = isMinus(b2);
        boolean twoMinuses = b1Minus && b2Minus;

        if (b1Minus && !b2Minus){ //first char of b1 is '-' and in b2 there is no '-'
            res[0] = num2;
            res[1] = num1;
            return res;
        }
        if (b2Minus && !b1Minus){ //first char of b2 is '-' and in b1 there is no '-'
            res[0] = num1;
            res[1] = num2;
            return res;
        }

        if (num1Len > num2Len) {
            if (twoMinuses) { // if both of them have a '-', the smaller length is bigger
                res[0] = num2;
                res[1] = num1;
            }
            else{
                res[0] = num1;
                res[1] = num2;
            }
            return res;
        }

        if (num2Len > num1Len){
            if (twoMinuses) { // if both of them have a '-', the smaller length is bigger
                res[0] = num1;
                res[1] = num2;
            }
            else{
                res[0] = num2;
                res[1] = num1;
            }
            return res;
        }

        int index;
        if (twoMinuses) {  // if two of them has '-', start check from index 1
            index = 1;
        }
        else{              // none of them has '-'
            index = 0;
        }

        for (int i = index; i<=num1Len-1;i++){
            if (num1.charAt(i) > num2.charAt(i)){
                if (twoMinuses){
                    res[0] = num2;
                    res[1] = num1;
                }
                else{
                    res[0] = num1;
                    res[1] = num2;
                }
                return res;
            }
            else if (num1.charAt(i) < num2.charAt(i)){
                if (twoMinuses){
                    res[0] = num1;
                    res[1] = num2;
                }
                else{
                    res[0] = num2;
                    res[1] = num1;
                }
                return res;
            }
            // the curr digit in num1 and num2 are equals , keep checking in the next digit
        }
        return res;
    }

    private String addZeros(String toAdd, String mulUnit){ //add zeros to the end of the string if necessary
        return toAdd + mulUnit;
    }

    private String deleteLeadingZeroes(String s) { // remove leading zeros from a number in a string
        if (s.length() == 1)
            return s;
        else {
           return  s.replaceFirst("^0+(?!$)", "");
        }
    }

}
