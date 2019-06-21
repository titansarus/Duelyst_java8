package Duelyst.Model;

public enum AttackKind {
    RANGED, HYBRID, MELEE;


    public static AttackKind getFromString(String string) {
        if (string.equalsIgnoreCase("Ranged")) {
            return RANGED;
        } else if (string.equalsIgnoreCase("Melee")) {
            return MELEE;
        } else if (string.equalsIgnoreCase("Hybrid")) {
            return HYBRID;
        }
        return MELEE;
    }
}
