package lk.imms.management_system;

import java.time.LocalDateTime;
import java.util.HashMap;

public class NewClassForTest {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());

        HashMap<String,String> n = new HashMap<>();
        n.put("CD", "Kumara");
        n.put("KD", "Asanka");
        n.put("BD", "Saman");
        n.put("CD", "Kamal");
        System.out.println(n);
    }
}
