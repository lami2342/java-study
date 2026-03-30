package study.ch07;

public class main6 {
    public static void main(String[] args) {
       int searchNumber = 6;
       int foundindex = -1;
        for (int i = 0; i < 10000; i++){
            int num = i + 1;
            if (num == searchNumber){
                foundindex = i;
                break;
            }
        }

        System.out.println("검색 번호 : " + searchNumber);
        System.out.println("찾은 Index : " + foundindex);
    }
}
