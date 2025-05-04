package character;

import battle.AttackerAction;
import battle.DefenderAction;
import main.Blessing;

import java.util.*;

public class Player extends Character {
    private AttackerAction queuedAttack;
    private DefenderAction queuedDefense;

    private final Set<String> blessings = new HashSet<>();
    private int gold = 0;

    private boolean usedRevive = false;
    private boolean revengeActive = false;
    private int turnsSinceLastHeal = 0;
    private int winStreak = 0;
    private boolean freeUpgradeAvailable = false;
    private int momentumCount = 0;

    private boolean hasBalanceBlessing = false;
    private boolean pendingFreeUpgrade = false;
    private int tempDefense = 0;

    private List<Blessing> currentShopBlessings = new ArrayList<>();

    public Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }

    @Override
    public AttackerAction chooseAttackAction() {
        return queuedAttack != null ? queuedAttack : AttackerAction.ATTACK;
    }

    @Override
    public DefenderAction chooseDefendAction() {
        return queuedDefense != null ? queuedDefense : DefenderAction.DEFEND;
    }

    public void setQueuedAttack(AttackerAction action) {
        this.queuedAttack = action;
    }

    public void setQueuedDefense(DefenderAction action) {
        this.queuedDefense = action;
    }

    public void upgrade(String stat) {
        switch (stat.toLowerCase()) {
            case "hp" -> maxHp += 10;
            case "atk" -> attack += 1;
            case "def" -> defense += 1;
            case "spd" -> speed += 1;
        }
    }

    public void addBlessing(String blessingName) {
        blessings.add(blessingName);
    }

    public boolean hasBlessing(String blessingName) {
        return blessings.contains(blessingName);
    }

    public Set<String> getAllBlessings() {
        return blessings;
    }

    public Set<String> getBlessings() {
        return blessings;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public void deductGold(int amount) {
        gold -= amount;
        if (gold < 0) gold = 0;
    }

    public int getGold() {
        return gold;
    }

    public boolean hasUsedRevive() {
        return usedRevive;
    }

    public void setUsedRevive(boolean used) {
        this.usedRevive = used;
    }

    public boolean isRevengeActive() {
        return revengeActive;
    }

    public void setRevengeActive(boolean value) {
        this.revengeActive = value;
    }

    public int getTurnsSinceLastHeal() {
        return turnsSinceLastHeal;
    }

    public void incrementTurn() {
        this.turnsSinceLastHeal++;
    }

    public void restoreHpBy(int amount) {
        hp += amount;
        if (hp > maxHp) hp = maxHp;
        turnsSinceLastHeal = 0;
    }

    public void restoreHp() {
        hp = maxHp;
    }

    public void increaseMaxHp(int amount) {
        maxHp += amount;
    }

    public int incrementWinStreak() {
        return ++winStreak;
    }

    public void resetWinStreak() {
        winStreak = 0;
    }

    public boolean isFreeUpgradeAvailable() {
        return freeUpgradeAvailable;
    }

    public void setFreeUpgradeAvailable(boolean value) {
        this.freeUpgradeAvailable = value;
    }

    public void resetMomentum() {
        momentumCount = 0;
    }

    public void incrementMomentum() {
        momentumCount++;
        if (momentumCount >= 3 && hasBlessing("Momentum")) {
            attack += 5;
            momentumCount = 0;
        }
    }

    public void addAttack(int amount) {
        attack += amount;
    }

    public void reduceAttack(int amount) {
        attack = Math.max(0, attack - amount);
    }

    public void addDefense(int amount) {
        defense += amount;
    }

    public void reduceDefense(int amount) {
        defense = Math.max(0, defense - amount);
    }

    public void addSpeed(int amount) {
        speed += amount;
    }

    public void reduceSpeed(int amount) {
        speed = Math.max(0, speed - amount);
    }

    public void setHp(int value) {
        hp = value;
    }

    public void addTempDefense(int amount) {
        tempDefense += amount;
    }

    public int getTotalDefense() {
        return defense + tempDefense;
    }

    public void resetTempDefense() {
        tempDefense = 0;
    }

    public void setBalanceBlessing(boolean value) {
        hasBalanceBlessing = value;
    }

    public boolean isBalanceBlessingActive() {
        return hasBalanceBlessing;
    }

    public void pendingFreeStatUpgrade() {
        pendingFreeUpgrade = true;
    }

    public boolean hasPendingFreeUpgrade() {
        return pendingFreeUpgrade;
    }

    public void consumeFreeUpgrade() {
        pendingFreeUpgrade = false;
    }

    public void setCurrentShopBlessings(List<Blessing> blessings) {
        this.currentShopBlessings = blessings;
    }

    public List<Blessing> getCurrentShopBlessings() {
        return currentShopBlessings == null ? new ArrayList<>() : currentShopBlessings;
    }
}
