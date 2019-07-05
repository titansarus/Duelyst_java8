package Duelyst.Controllers;

public enum BattleSpeed {
    NORMAL(1.0), DOUBLE(2.0) , HALF(0.5);

    private double speedFactor;

    BattleSpeed(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public double getSpeedFactor() {
        return speedFactor;
    }
}
