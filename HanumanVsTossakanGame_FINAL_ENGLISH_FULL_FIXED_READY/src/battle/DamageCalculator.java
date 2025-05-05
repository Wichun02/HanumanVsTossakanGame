package battle;

import character.Character;
import character.Player;

public class DamageCalculator {
    public static boolean dodged = false;
    public static boolean countered = false;

    public static int calculate(Character attacker, Character defender,
                                AttackerAction attackAction, DefenderAction defendAction) {
        dodged = false;
        countered = false;

        // Dodge logic
        double baseDodge = 0.1 + 0.4 * ((double) defender.getSpeed() / (attacker.getSpeed() + defender.getSpeed()));
        if (defender instanceof Player p && p.hasBlessing("Evasion")) {
            baseDodge += 0.2;
        }

        if (Math.random() < baseDodge) {
            dodged = true;
            return 0;
        }

        // Base damage
        int attackerATK = (attacker instanceof Player p) ? p.getTotalAttack() : attacker.getAttack();
        int baseDamage = switch (attackAction) {
            case ATTACK -> attackerATK + 5;
            case FINISHER -> attackerATK + 25;
            case SPECIAL_ATTACK -> attackerATK + 15;
        };

        // Heavy Hands
        if (attacker instanceof Player p && p.hasBlessing("Heavy Hands")) {
            baseDamage += 8;
        }

        // Finisher Boost
        if (attacker instanceof Player p && attackAction == AttackerAction.FINISHER && p.hasBlessing("Finisher Boost")) {
            baseDamage += 10;
        }

        // Executioner
        if (attacker instanceof Player p && BlessingPassiveEngine.checkExecutioner(p, defender, attackAction, baseDamage)) {
            baseDamage = (int) (baseDamage * 1.5);
        }

        // Revenge
        if (attacker instanceof Player p && p.isRevengeActive()) {
            baseDamage = (int) (baseDamage * 1.5);
            p.setRevengeActive(false);
        }

        // Critical Eye (+ Will of Hanuman)
        if (attacker instanceof Player p && p.hasBlessing("Critical Eye")) {
            double critChance = 0.2;
            if (BlessingPassiveEngine.checkWillOfHanuman(p)) {
                critChance += 0.3;
            }
            if (Math.random() < critChance) {
                baseDamage *= 1.5;
            }
        }

        // Blessing of Balance
        if (attacker instanceof Player p && p.hasBlessing("Blessing of Balance")) {
            baseDamage *= 0.9;
        }
        if (defender instanceof Player p && p.hasBlessing("Blessing of Balance")) {
            baseDamage *= 0.9;
        }

        // ใช้ totalDefense ที่รวม tempDefense
        int effectiveDefense = defender.getDefense();
        if (defender instanceof Player p) {
            effectiveDefense = p.getTotalDefense();
        }

        // Final damage calculation based on defense interaction
        int finalDamage = switch (defendAction) {
            case DEFEND -> (attackAction == AttackerAction.ATTACK)
                    ? Math.max(0, (int) ((baseDamage - effectiveDefense) / 2.0))
                    : baseDamage;
            case SPECIAL_DEFEND -> (attackAction == AttackerAction.SPECIAL_ATTACK)
                    ? Math.max(0, baseDamage - effectiveDefense - 10)
                    : baseDamage;
            case COUNTER -> {
                if (attackAction == AttackerAction.FINISHER) {
                    countered = true;
                    yield -Math.max(0, attacker.getAttack() / 2); // reflect damage to attacker
                } else {
                    yield baseDamage;
                }
            }
            case SURRENDER -> defender.getHp();
        };

        // Reset temp defense after using
        if (defender instanceof Player p) {
            p.resetTempDefense();
        }

        return finalDamage;
    }
}