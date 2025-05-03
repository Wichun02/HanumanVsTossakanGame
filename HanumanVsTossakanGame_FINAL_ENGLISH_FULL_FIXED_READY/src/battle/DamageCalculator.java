package battle;

import character.Character;

public class DamageCalculator {
    public static int calculate(Character attacker, Character defender,
                                AttackerAction attackAction, DefenderAction defendAction) {

        if (defendAction == DefenderAction.SURRENDER) {
            return defender.getHp();
        }

        double dodgeChance = 0.1 + 0.4 * ((double) defender.getSpeed() / (attacker.getSpeed() + defender.getSpeed()));
        if (Math.random() < dodgeChance) {
            return 0;
        }

        int baseDamage = switch (attackAction) {
            case ATTACK -> attacker.getAttack() + 5;
            case FINISHER -> attacker.getAttack() + 25;
            case SPECIAL_ATTACK -> attacker.getAttack() + 15;
        };

        return switch (defendAction) {
            case DEFEND -> (attackAction == AttackerAction.ATTACK)
                    ? Math.max(0, (baseDamage - defender.getDefense()) / 2)
                    : baseDamage;
            case SPECIAL_DEFEND -> (attackAction == AttackerAction.SPECIAL_ATTACK)
                    ? Math.max(0, (baseDamage - defender.getDefense() - 10))
                    : baseDamage;
            case COUNTER -> (attackAction == AttackerAction.FINISHER)
                    ? Math.max(0, attacker.getAttack() / 2)
                    : baseDamage;
            default -> baseDamage;
        };
    }
}
