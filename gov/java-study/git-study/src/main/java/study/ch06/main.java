package study.ch06;

public class main {
    public static void main(String[] args) {
        char c = 'a';
        switch (c) {
            case 'a':
                System.out.println("a를 선택하셨습니다");
                break;
            case 'b':
                System.out.println("b를 선택하셨습니다");
                break;
            case 'c':
                System.out.println("c를 선택하셨습니다");
                break;
            case 'd':
                System.out.println("d를 선택하셨습니다");
                break;


        }
        //for 문 : 횟수가 정해져있는 반복
        for (int i = 1; i <= 5; i++) {
            System.out.println(i +" ");
        }
    }
}
