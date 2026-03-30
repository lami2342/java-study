package study.ch07;

public class main3 {
    public static void main(String[] args) {
       int count = 120;

        int num = 0;
        while (num < count) {
            int q = 0;
            while (q<count-1-num){
                System.out.print(" ");
                q++;
            }
            q = 0;
            while(q < num+1){
            System.out.print("*");
            q++;}
            System.out.println();
            num++;

        }
        System.out.println();

        int num2 = 0;
        while (num2 < 5){
            int i =0;
            while (i < num2+1) {
                System.out.print("*");
                i++;
            }
            System.out.println();
            num2++;
        }



    }
}
