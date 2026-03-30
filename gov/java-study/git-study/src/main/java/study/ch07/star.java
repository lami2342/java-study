package study.ch07;

public class star {
    public static void main(String[] args) {
        int count = 10;
        /**
         * *
         * **
         * ***
         * ****
         * *****
         */
        System.out.println("/**");
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < i + 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        for (int k = 0; k < count; k++) {
            for (int l = 0; l < count - k; l++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for(int y = 0; y < count; y++){
            for (int k = 0; k <y; k++){
                System.out.print(" ");
            }
            for(int u = 0; u < count -y; u++){
                System.out.print("*");
            }
            System.out.println();
        }
        for(int q = 0; q < count; q++){
            for (int w = 0; w <count-1-q; w++){
                System.out.print(" ");
            }
            for(int e = 0; e < q+1; e++){
                System.out.print("*");
            }
            System.out.println();
        }


    }
}
