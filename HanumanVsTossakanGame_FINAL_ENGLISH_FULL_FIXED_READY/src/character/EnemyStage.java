package character;

import java.util.List;
import java.util.ArrayList;

public class EnemyStage {
    public static List<Boss> getEnemies() {
        List<Boss> list = new ArrayList<>();

        list.add(new Boss("Tossakan", 100, 30, 15, 10));
        list.add(new Boss("Indrajit", 90, 28, 14, 12));
        list.add(new Boss("Kumbhakan", 110, 25, 18, 8));
        list.add(new Boss("Vibhishana", 80, 20, 10, 15));
        list.add(new Boss("Maiyarap", 95, 26, 13, 14));

        return list;
    }
}
