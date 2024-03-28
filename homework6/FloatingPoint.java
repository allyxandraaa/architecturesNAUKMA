import java.util.HashMap;

public class FloatingPoint {
    public static void main(String[] args){
        System.out.println(convert(1789.146));
    }
    public static String convert(double decimal) {

        if (decimal == 0) return times0(32);

        String fractional = "";
        String whole = "";
        String decimalString = Double.toString(decimal);

        //Split the number by whole and fractional part
        String[] split = decimalString.split("\\."); // use regular expression to split by dot
        split[1] = "0." + split[1];

        //Convert integer part to binary
        whole = convertToBinary(Math.abs(Integer.parseInt(split[0])));

        //Convert fractional part to binary
        double el = Double.parseDouble(split[1]);
        while (el % 1 != 0 && whole.length() + fractional.length() < 24) {
            el *= 2;
            if (Math.floor(el) == 1) {
                fractional += "1";
                el -= 1;
            } else {
                fractional += "0";
            }
        }

        //Calculate the exponent value

        int exponent = whole.length() + 126;

        String exponentBinary = convertToBinary(exponent);

        // Omit first "1" from mantissa

        String mantissa = whole.substring(1) + fractional;

        // Set the value of the sign bit
        String sign = "0";
        if (decimal < 0){
           sign = "1";
        }

        //Write the result in single-precision.
        String resultBinary = sign + times0(8 - exponentBinary.length()) + exponentBinary + mantissa +  times0(23 - mantissa.length());

        System.out.println(resultBinary);


        //convert to hexadecimal and return result of the function
        return convertToHexadecimal(convertToDecimal(resultBinary));

    }
    public static String times0(int times){
        String result = "";
        if (times == 0) return result;
        for (int i = 0; i < times; i++){
            result += "0";
        }
        return result;
    }
    public static String convertToBinary(int num){
        String result = "";
        while(num != 0) {
            result = Integer.toString(num % 2) + result;
            num /= 2;
        }
        return result;
    }
    public static long convertToDecimal(String binary){
        long decimal = 0;
        for(int i = 0; i < binary.length(); i++){
            decimal += (long) (Integer.parseInt(String.valueOf(binary.charAt(binary.length() - 1 - i))) * Math.pow(2,i));
        }
        return decimal;
    }
    public static String convertToHexadecimal(long decimal){
        HashMap<Long, String> hexadecimal = new HashMap<>();
        hexadecimal.put(10L,"A");
        hexadecimal.put(11L,"B");
        hexadecimal.put(12L,"C");
        hexadecimal.put(13L,"D");
        hexadecimal.put(14L,"E");
        hexadecimal.put(15L,"F");

        String result = "";
        long remain = 0;
        while(decimal != 0 ){
            remain = decimal % 16;
            if (remain < 10){
                result = Long.toString(remain) + result;
            }
            else {
                result = hexadecimal.get(remain) + result;
            }
            decimal /= 16;
        }
        return result;
    }
}
