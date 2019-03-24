import bo.PhoneReader;
import model.PhoneInfo;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        long lStartTime = new Date().getTime();
        long[] oA = new long[50000000];
        int index = 0;
        try (PhoneReader phoneReader = new PhoneReader("M:\\phone.txt")) {
            for (PhoneInfo phoneInfo : phoneReader){
                if (phoneInfo != null) {
                    oA[index++] = phoneInfo.phoneNumber;
                    if (index % 1000000 == 0) {
                        System.out.println(index);
                    }
                }
            }
        }
        long lEndInputTime = new Date().getTime();
        System.out.println(lEndInputTime - lStartTime);
        Arrays.sort(oA);

        for (int i = 0; i < 50*1000*1000; i++) {
            //PhoneInfo x = oA[i];
            if (i < 50)
                System.out.println(oA[50000000- i - 1]);
        }
        long lEndSortTime = new Date().getTime();
        System.out.println(lEndSortTime - lEndInputTime);
        /*

        Random oR = new Random();


        for (int i = 0; i < 40*1000*1000; i++) {
            oA[i] = new PhoneInfo(oR.nextLong(), oR.nextInt(), oR.nextInt());
            if (i % 1000000 == 0) {
                System.out.println(i);
            }
        }
        long lEndInputTime = new Date().getTime();
        System.out.println(lEndInputTime - lStartTime);

        Comparator<PhoneInfo> phoneComparator = new Comparator<PhoneInfo>() {
            @Override
            public int compare(PhoneInfo e1, PhoneInfo e2) {
                if (e1 == null && e2 == null) {
                    return 0;
                }
                if (e1 != null && e2 == null) {
                    return -1;
                }
                if (e1 == null && e2 != null) {
                    return 1;
                }
                return Long.compare(e1.phoneNumber, e2.phoneNumber);
            }
        };

        //Collections.sort(oA);
        //quickSort(oA, 0, oA.length - 1);
        Arrays.sort(oA, phoneComparator);
        long lEndSortTime = new Date().getTime();
        System.out.println(lEndSortTime - lEndInputTime);
        for (int i = 0; i < 50*1000*1000; i++) {
            PhoneInfo x = oA[i];
            if (i < 50)
                System.out.println(oA[i]);
        }
        System.out.println(new Date().getTime() - lEndSortTime);*/
    }

    public static int partition(int a[], int beg, int end)
    {

        int left, right, temp, loc, flag;
        loc = left = beg;
        right = end;
        flag = 0;
        while(flag != 1)
        {
            while((a[loc] <= a[right]) && (loc!=right))
                right--;
            if(loc==right)
                flag =1;
            else if(a[loc]>a[right])
            {
                temp = a[loc];
                a[loc] = a[right];
                a[right] = temp;
                loc = right;
            }
            if(flag!=1)
            {
                while((a[loc] >= a[left]) && (loc!=left))
                    left++;
                if(loc==left)
                    flag =1;
                else if(a[loc] <a[left])
                {
                    temp = a[loc];
                    a[loc] = a[left];
                    a[left] = temp;
                    loc = left;
                }
            }
        }
        return loc;
    }
    static void quickSort(int a[], int beg, int end)
    {

        int loc;
        if(beg<end)
        {
            loc = partition(a, beg, end);
            quickSort(a, beg, loc-1);
            quickSort(a, loc+1, end);
        }
    }
}
