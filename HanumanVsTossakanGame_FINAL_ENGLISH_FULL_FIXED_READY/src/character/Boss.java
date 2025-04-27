package character;

import battle.AttackerAction;
import battle.DefenderAction;

import java.util.Random;

public class Boss extends Character {
    private Random random = new Random();

    public Boss(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }

    @Override
    public AttackerAction chooseAttackAction() {
        return AttackerAction.values()[random.nextInt(AttackerAction.values().length)];
    }

    @Override
    public DefenderAction chooseDefendAction() {
        return DefenderAction.values()[random.nextInt(DefenderAction.values().length)];
    }
}
