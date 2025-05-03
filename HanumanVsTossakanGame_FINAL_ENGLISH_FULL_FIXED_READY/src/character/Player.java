package character;

import battle.AttackerAction;
import battle.DefenderAction;

public class Player extends Character {
    private AttackerAction queuedAttack;
    private DefenderAction queuedDefense;

    public Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }

    public void setQueuedAttack(AttackerAction action) {
        this.queuedAttack = action;
    }

    public void setQueuedDefense(DefenderAction action) {
        this.queuedDefense = action;
    }

    @Override
    public AttackerAction chooseAttackAction() {
        return queuedAttack != null ? queuedAttack : AttackerAction.ATTACK;
    }

    @Override
    public DefenderAction chooseDefendAction() {
        return queuedDefense != null ? queuedDefense : DefenderAction.DEFEND;
    }

    public void upgrade(String stat) {
        switch (stat.toLowerCase()) {
            case "hp" -> maxHp += 10;
            case "atk" -> attack += 1;
            case "def" -> defense += 1;
            case "spd" -> speed += 1;
        }
    }

    public int getMaxHp() {
        return maxHp;
    }
}
