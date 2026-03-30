package study.ch07;

import org.w3c.dom.ls.LSOutput;

public class main {
    public static void main(String[] args) {
        System.out.println("프로그램 실행");
        int input1 = 10;
        int input2 = 5;

        for (int i = 0; i < input1; i++) {
            System.out.println(input1 - i);
            for (int j = 0; j < input2; j++) {
                int num = 1;
                for (int k = 0; k < j + 1; k++) {
                    num *= 10;
                }
                System.out.println((input1 - i)* num);
            }

        }
        System.out.println("프로그램 종료");
    }
}
