package character;

import battle.AttackerAction;
import battle.DefenderAction;

import java.util.Random;

public class Boss extends Character {
    private int stage; // เพิ่ม field สำหรับเก็บหมายเลขด่าน
    private Random random = new Random();

    // แก้ constructor ให้รับ stage ด้วย
    public Boss(String name, int maxHp, int attack, int defense, int speed, int stage) {
        super(name, maxHp, attack, defense, speed);
        this.stage = stage;
    }

    public int getStage() {
        return stage;
    }

    @Override
    public AttackerAction chooseAttackAction() {
        return AttackerAction.values()[random.nextInt(AttackerAction.values().length)];
    }

    @Override
    public DefenderAction chooseDefendAction() {
        DefenderAction[] choices = {DefenderAction.DEFEND, DefenderAction.COUNTER, DefenderAction.SPECIAL_DEFEND};
        return choices[random.nextInt(choices.length)];
    }
}
