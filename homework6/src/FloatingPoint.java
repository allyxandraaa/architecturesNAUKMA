import java.util.HashMap;

public class FloatingPoint {
    public static void main(String[] args){
        String hex = convertToFloatingPoint(-1.3);
        System.out.println(hex);
        double num = convertToDecimal(hex);
        System.out.println(num);
    }
    public static String convertToFloatingPoint(double decimal) {
        String sign = "0";
        if (decimal < 0) {
            sign = "1";
            decimal *= -1;
        }

        if (decimal == 0) return times0(8);

        double fractional_decimal = decimal % 1;
        int whole_decimal = (int) decimal;

        if (whole_decimal == 0)
            return convertOnlyFractional(fractional_decimal, sign);
        else
            return convertWholeAndFractional(whole_decimal, fractional_decimal, sign);
    }
    private static String convertOnlyFractional(double fractional_decimal, String sign) {
        String fractional_binary = "";
        int i = 0, exponent_decimal = 0;
        boolean one_found = false;
        while (fractional_decimal != 0 && i < 23) {
            fractional_decimal *= 2;
            int remainder = (int) fractional_decimal;
            if (remainder == 1) one_found = true;
            if (!one_found) {
                exponent_decimal += 1;
            }
            fractional_binary += remainder;
            fractional_decimal %= 1;
            i++;
        }

        String exponent_binary = convertToBinary(-exponent_decimal + 126);

//        Omit first "1" from mantissa
        String mantissa = fractional_binary.substring(exponent_decimal + 1);

//        Write the result in single-precision.
        String result_binary = sign + times0(8 - exponent_binary.length()) + exponent_binary + mantissa +  times0(23 - mantissa.length());

//        Convert to hexadecimal and return result of the function
        return fromBinaryToHexadecimal(result_binary);
    }
    private static String convertWholeAndFractional(int whole_d, double fractional_decimal, String sign) {
        String whole_binary = convertToBinary(whole_d);
        String fractional_binary = "";

        while (fractional_decimal != 0 && whole_binary.length() + fractional_binary.length() < 24) {
            fractional_decimal *= 2;
            int remainder = (int) fractional_decimal;
            fractional_binary += remainder;
            fractional_decimal %= 1;
        }

//        Calculate the exponent value
        int exponent_decimal = whole_binary.length() + 126;

        String exponent_binary = convertToBinary(exponent_decimal);

//        Omit first "1" from mantissa
        String mantissa = whole_binary.substring(1) + fractional_binary;

//        Write the result in single-precision.
        String result_binary = sign + times0(8 - exponent_binary.length()) + exponent_binary + mantissa +  times0(23 - mantissa.length());

//        Convert to hexadecimal and return result of the function
        return fromBinaryToHexadecimal(result_binary);
    }
    private static String times0(int times){
        String result = "";
        if (times == 0) return result;
        for (int i = 0; i < times; i++){
            result += "0";
        }
        return result;
    }
    private static String convertToBinary(int num){
        String result = "";
        while(num != 0) {
            result = num % 2 + result;
            num /= 2;
        }
        return result;
    }
    private static String fromBinaryToHexadecimal(String binary){
        HashMap<String, String> blockToHex = new HashMap<>();
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        for (int i = 0; i < 16; i++) {
            String binary_block = "";
            int k = i;
            for (int j = 0; j < 4; j++) {
                binary_block = k % 2 + binary_block;
                k /= 2;
            }
            blockToHex.put(binary_block, values[i]);
        }

        String result = "";
        String token = "";
        int i = 0;
        while (i < binary.length()) {
            token += binary.charAt(i++);
            if (token.length() >= 4) {
                result += blockToHex.get(token);
                token = "";
            }
        }
        return result;
    }
    private static String fromHexadecimalToBinary(String hex){
        HashMap<Character, String> hexToBlock = new HashMap<>();
        char[] keys = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < 16; i++) {
            String binary_block = "";
            int k = i;
            for (int j = 0; j < 4; j++) {
                binary_block = k % 2 + binary_block;
                k /= 2;
            }
            hexToBlock.put(keys[i], binary_block);
        }

        String result = "";
        for (int i = 0; i < hex.length(); i++) {
            result += hexToBlock.get(hex.charAt(i));
        }
        return result;
    }
    public static double convertToDecimal(String hex){
        if (hex.equals("00000000"))
            return 0;
        String binary = fromHexadecimalToBinary(hex);

        char sign = binary.charAt(0);
        String exponent_binary = binary.substring(1,9);
        String mantissa = binary.substring(9);
        int exponent_decimal = fromBinaryToDecimal(exponent_binary) - 127;

        String whole_binary;
        String fractional_binary;
        if (exponent_decimal >= 0) {
            whole_binary = "1" + mantissa.substring(0, exponent_decimal);
            fractional_binary = mantissa.substring(exponent_decimal);
        }
        else {
            whole_binary = "0";
            fractional_binary = times0(-exponent_decimal - 1) + "1" + mantissa;
        }

        double result = fromBinaryToDecimal(whole_binary);
        for (int i = 0; i < fractional_binary.length(); i++) {
            if (fractional_binary.charAt(i) == '1')
                result += Math.pow(2, -1 - i);
        }

        if (sign == '1')
            return -result;
        return result;
    }
    private static int fromBinaryToDecimal(String binary) {
        int result = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(binary.length() - i - 1) == '1')
                result += (int) Math.pow(2, i);
        }
        return result;
    }
}
