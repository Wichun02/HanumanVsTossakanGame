package battle;

import character.Character;

public class DamageCalculator {
    public static boolean dodged = false;
    public static boolean countered = false;

    public static int calculate(Character attacker, Character defender,
                                AttackerAction attackAction, DefenderAction defendAction) {

        dodged = false;
        countered = false;

        if (defendAction == DefenderAction.SURRENDER) {
            return defender.getHp(); // instant defeat
        }

        double dodgeChance = 0.1 + 0.4 * ((double) defender.getSpeed() / (attacker.getSpeed() + defender.getSpeed()));
        if (Math.random() < dodgeChance) {
            dodged = true;
            return 0;
        }

        int baseDamage = switch (attackAction) {
            case ATTACK -> attacker.getAttack() + 5;
            case FINISHER -> attacker.getAttack() + 25;
            case SPECIAL_ATTACK -> attacker.getAttack() + 15;
        };

        // counter logic: no damage taken, attacker receives reflected damage
        if (attackAction == AttackerAction.FINISHER && defendAction == DefenderAction.COUNTER) {
            countered = true;
            return -attacker.getAttack(); // negative = reflect damage to attacker
        }

        return switch (defendAction) {
            case DEFEND -> (attackAction == AttackerAction.ATTACK)
                    ? Math.max(0, (baseDamage - defender.getDefense()) / 2)
                    : baseDamage;

            case SPECIAL_DEFEND -> (attackAction == AttackerAction.SPECIAL_ATTACK)
                    ? Math.max(0, (baseDamage - defender.getDefense() - 10))
                    : baseDamage;

            default -> baseDamage;
        };
    }
}
