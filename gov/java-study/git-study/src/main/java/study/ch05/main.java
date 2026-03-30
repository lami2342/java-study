package study.ch05;

public class main {
    public static void main(String[] args) {
        int num = 33;
        boolean result = num % 2 ==0;

//        if (result) System.out.println(num * 10);
//        if (!result) System.out.println(num * 20);
        if (result) {
             System.out.println(num * 10);
            System.out.println("2의 배수 입니다.");
        } else{
            System.out.println(num * 20);
            System.out.println("2의 배수가 아닙니다.");
        }
        //num의 값이 짝수면 num에 10을 곱하고 짝수가 아니면 20을 곱하여 결과를 출력해라.

        int num2 = 12;
        if (num2 % 8 == 0) {
            System.out.println("8의 배수입니다");
        }else if (num2% 7 == 0) {
            System.out.println("7의 배수입니다");
        }else if (num2 % 6 == 0) {
            System.out.println("6의 배수입니다");
        }else {
            System.out.println("6,7,8 의 배수가 아닙니다.");
        }
        int 키 = 140;
        int 나이 = 7;

        if (나이 >= 7 && 키 >= 140){
            System.out.println("탑승 가능");
        } else {
            if (나이< 7) {
                System.out.println("나이는 7세 이상이어야 합니다.");
            }else {
                System.out.println("키가 140 이상이어야 합니다.");
                }
            System.out.println("탑승 불가능.");
            }

        //swtch 조건문
        //break를 통해 정지 가능함

        }
    }
