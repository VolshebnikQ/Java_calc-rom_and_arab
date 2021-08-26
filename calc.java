import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class calc {

    public static void main(String[] args) {

        Map<String, Integer> roman_num = new HashMap<String, Integer>();
        roman_num.put("C", 100);
        roman_num.put("XC", 90);
        roman_num.put("L", 50);
        roman_num.put("XL", 40);
        roman_num.put("X", 10);
        roman_num.put("IX", 9);
        roman_num.put("V", 5);
        roman_num.put("IV", 4);
        roman_num.put("I", 1);

        Scanner in = new Scanner(System.in);
        System.out.printf("Введите ваше выражение: ");
        String str = in.nextLine();
        in.close();

        str = str.trim();
        str = str.toUpperCase();
        String[] elements = str.split(" ");
        if (elements.length == 3) { //Ошибка колличества элементов в строке
            if (check_nums(elements) != 0) { //Ошибка правила чисел до 10ти и сочетания арабских и римских чисел
                if (check_sign(elements)) { //Ошибка правильности написания математического знака
                    if (check_nums(elements) == 1) {
                        System.out.println(calc_rome_num(elements,roman_num));
                    }else {
                        System.out.println(calc_arab_num(elements));
                    }
                }else {
                    System.out.println("ОШИБКА");
                    System.out.println("Проверте правильность написания математического знака");
                }
            }else {
                System.out.println("ОШИБКА");
                System.out.println("Проверте правильность ввода чисел");
                System.out.println("- Ввод дробных чисел невозможен");
                System.out.println("- Ввод чисел более 10 или X невозможен");
                System.out.println("- Арабские и Римские числа взаимодействуют раздельно");
            }
        }else{
            System.out.println("ОШИБКА");
            System.out.println("Введенная строка не соответсвует условиям");
            System.out.println("- Два числа и один математический знак");
        }
    }
    static int check_nums(String[] elements){//Метод проверки чисел до 10ти
        String r_el = "IIIVIIIX";
        String n_el = "12345678910";
        
        if (r_el.indexOf(elements[0])>=0 && r_el.indexOf(elements[2])>=0) {
            return 1;
        }else if (n_el.indexOf(elements[0])>=0 && n_el.indexOf(elements[2])>=0) {
            return 2;
        }else {
            return 0;
        }
    }
    static Boolean check_sign(String[] elements){//Метод проверки знаков
        String r_el = "+-*/";
        
        if (r_el.indexOf(elements[1])>=0) {
            return true;
        }else {
            return false;
        }
    }
    static int roman_to_arabic(Map<String, Integer> roman_num, String el){//Метод превращения римских чисел в арабские (ограничение только здесь roman_num)
        String[] roman_el = el.split("");                             
        int arab_num = 0;
        int i = 0; 
        do {
            if(1 == roman_el.length){
                arab_num += roman_num.get(roman_el[i]);
                break;
            }else{
                int x1 = roman_num.get(roman_el[i]);
                int x2 = roman_num.get(roman_el[i+1]);
                if (x1 >= x2) {
                    arab_num += roman_num.get(roman_el[i]);
                    if (i == roman_el.length-2) {arab_num += roman_num.get(roman_el[i+1]);break;}
                }else {
                    arab_num += roman_num.get(roman_el[i+1]) - roman_num.get(roman_el[i]);
                    break;
                }
            }i++;
        } while (i < roman_el.length-1);
        return arab_num;
    }
    static String arabic_to_roman(int num){//Метод превращения арабских чисел в римские (до 100)
        return "I".repeat(num)
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C");
    }
    static int calc_arab_num(String[] elements){
        int result = 0;
        int x1 = Integer.parseInt(elements[0]);
        int x2 = Integer.parseInt(elements[2]);
        switch(elements[1]){
            case "+": 
                result = x1 + x2;
                break;
            case "-": 
                result = x1 - x2;
                break;
            case "*": 
                result = x1 * x2;
                break;
            case "/":
                result = (int)Math.round(x1 / x2);
                break;
        }
        return result;
    }
    static String calc_rome_num(String[] elements,Map<String, Integer> roman_num){
        int result = 0;
        int x1 = roman_to_arabic(roman_num, elements[0]);
        int x2 = roman_to_arabic(roman_num, elements[2]);
        switch(elements[1]){
            case "+": 
                result = x1 + x2;
                break;
            case "-": 
                if(x1 > x2){
                    result = x1 - x2;
                }else {
                    System.out.println("ОШИБКА");
                    System.out.println("Риское число не может быть отрицательным");
                }
                break;
            case "*": 
                result = x1 * x2;
                break;
            case "/":
                result = (int)Math.round(x1 / x2);
                break;
        }
        return arabic_to_roman(result);
    }
}