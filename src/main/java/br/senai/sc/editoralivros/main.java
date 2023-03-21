package br.senai.sc.editoralivros;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("Senai123"));

//        int[] a = {677, 591, 153, 356, 617, 337, 195, 948, 440, 657, 631, 546, 148, 678};
//
//        boolean f = false;
//        int x = a[0];
//
//        for (int i = 0; i < a.length; i++) {
//            if ((a[i] % 2 == 0) && (f == false)) {
//                x = a[i];
//                f = true;
//            }
//            if ((a[i] < x) && (a[i] % 2 == 0)) {
//                x = a[i];
//            }
//        }
//        if (f == false) {
//            System.out.println(0);
//        } else {
//            System.out.println(x);
//        }

        int r = 2;
        int p = 3;
        int t = 6;

        List<Integer> a = new ArrayList<>();
        a.add(r);

        for (int i = 1; i < t;i++){
            a.add(a.get(i-1) * r);
        }
        for (int i = p-1; i < t; i++){
            if (i+1 == t){
                System.out.println(a.get(i));
            } else {
                System.out.print(a.get(i) + ", ");
            }
        }
    }
}
