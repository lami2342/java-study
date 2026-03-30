package study.ch07;

import java.util.Scanner;

public class main8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("프로그램을 종료하시려면 close를 입력해주세요(계속하시려면 아무키나 입력후 엔터) :");
            if(sc.nextLine().equals("close")) {
                break;
            }
            System.out.println("프로그램 실행 중...");
        }

    }
}
