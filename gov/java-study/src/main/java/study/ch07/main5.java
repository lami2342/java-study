package study.ch07;

public class main5 {
    public static void main(String[] args) {
        int c = 10;
        int i = 0;
        while (i < c) {
            int ii = i++;
            if (ii % 2 != 0){
                continue;
            }


            System.out.println("i : "+ ii);



        }
    }
}
