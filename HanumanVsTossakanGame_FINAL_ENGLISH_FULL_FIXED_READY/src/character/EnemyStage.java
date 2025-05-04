package character;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class EnemyStage {
    public static List<Boss> getEnemies() {
        List<Boss> list = new ArrayList<>();
        Random random = new Random();

        String[] names = {
            "Tossakan", "Indrajit", "Kumbhakan", "Vibhishana", "Maiyarap",
            "Asurapad", "Virunchambang", "Maliwan", "Sahasadecha", "Speed Reaper"
        };

        int[][] baseStats = {
            {130, 32, 16, 10},  // Tossakan - Main boss, tanky
            {95, 30, 14, 14},   // Indrajit - agile fighter
            {110, 27, 18, 8},   // Kumbhakan - high defense
            {85, 21, 10, 17},   // Vibhishana - high speed
            {100, 28, 13, 15},  // Maiyarap - balanced
            {105, 29, 14, 13},  // Asurapad - average
            {90, 31, 12, 18},   // Virunchambang - high speed
            {115, 26, 17, 11},  // Maliwan - high defense
            {108, 33, 15, 9},   // Sahasadecha - strong attacker
            {95, 25, 11, 21}    // Speed Reaper - ultra fast enemy
        };

        for (int i = 0; i < 30; i++) {
            int bossIndex = i % names.length;
            String name = names[bossIndex] + " Lv." + (i + 1);

            int[] stats = baseStats[bossIndex];
            int hp = stats[0] + (int)(i * 7.5); // scaled for late-game toughness
            int atk = stats[1] + (i / 5);
            int def = stats[2] + (i / 6);
            int spd = stats[3] + (i % 3 == 0 ? 1 : 0); // occasional speed bump

            list.add(new Boss(name, hp, atk, def, spd));
        }

        return list;
    }
}
