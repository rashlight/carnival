package com.rashlight.carnival.value;

import com.rashlight.carnival.entity.FighterAction;
import com.rashlight.carnival.entity.FighterSide;
import com.rashlight.carnival.entity.FighterState;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FighterAI {

     private float entropy;

     private FighterSide side;

     private int friendlyPosition;

     private int enemyPosition;

     private final int deadZoneLeft = 0;

     private final int deadZoneRight = 9;

    /**
     * The AI for fighter game.
     * Properties are used according to AI perspective
     */
     public FighterAI(FighterSide side, int friendlyPosition, int enemyPosition) {
         this.side = side;
         this.friendlyPosition = friendlyPosition;
         this.enemyPosition = enemyPosition;

         entropy = ThreadLocalRandom.current().nextFloat(-1f, 1f) * 30f * getRiskRate();
     }

     private float getRiskRate() {
         return 0.1f * (getDeltaDistance() + 1);
     }

     private boolean isAdvantage() {
         return ((side == FighterSide.LEFT && friendlyPosition >= 5) || (side == FighterSide.RIGHT && friendlyPosition <= 4));
     }

     private int getDeltaDistance() {
         return switch (side) {
             case LEFT -> friendlyPosition;
             case RIGHT -> deadZoneRight - friendlyPosition;
         };
     }

     private int getSafeDistance(int position) {
         return Math.min(deadZoneLeft - position, position - deadZoneRight);
     }

     /**
      * Giving data, predict the next probable move
      * @return FighterAction
      */
     public FighterAction next() {
         float seperator1 = (100f / 3f) + ThreadLocalRandom.current().nextFloat(-1f, 1f) * entropy;
         float seperator2 = seperator1 + (100f / 3f) + ThreadLocalRandom.current().nextFloat(-1f, 1f) * entropy;

         float choice = ThreadLocalRandom.current().nextFloat(0f, 100f);
         if (0f <= choice && choice < seperator1) {
             return FighterAction.ATTACK;
         } else if (seperator1 <= choice && choice < seperator2) {
             return FighterAction.DEFEND;
         } else {
             return FighterAction.DASH;
         }
     }

    public float getEntropy() {
        return entropy;
    }

    public void setEntropy(float entropy) {
        this.entropy = entropy;
    }

    public int getFriendlyPosition() {
        return friendlyPosition;
    }

    public void setFriendlyPosition(int friendlyPosition) {
        this.friendlyPosition = friendlyPosition;
    }

    public int getEnemyPosition() {
        return enemyPosition;
    }

    public void setEnemyPosition(int enemyPosition) {
        this.enemyPosition = enemyPosition;
    }

    public int getDeadZoneLeft() {
        return deadZoneLeft;
    }

    public int getDeadZoneRight() {
        return deadZoneRight;
    }
}
