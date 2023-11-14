package agents.DanAndNan;

import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;


/**
 * @author Daniel Onyema and Nandita Kumar, based on Sergey Karakovskiy's agent.
 */
public class Agent implements MarioAgent {

    private boolean[] actions = null;
    @Override
    public void initialize(MarioForwardModel model, MarioTimer timer) {
        actions = new boolean[MarioActions.numberOfActions()];
        actions[MarioActions.RIGHT.getValue()] = false;
        actions[MarioActions.SPEED.getValue()] = false;
    }

    @Override

    /*
    Runs every frame, action values are SPEED, LEFT, RIGHT, AND JUMP.
    In Mario's observation arrays, he is located at [8][8].
     */

    public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {

        int[][] sceneArray = model.getScreenCompleteObservation();
        int[][] enemyArray = model.getScreenEnemiesObservation();
        int[][] marioSceneArray = model.getMarioSceneObservation();
        int[][] marioEnemyArray = model.getMarioEnemiesObservation();

        if(isGoomba(marioEnemyArray) || isPipe(marioSceneArray) || inAir(marioSceneArray)){
            actions[MarioActions.JUMP.getValue()] = true;
        }
        else{
            actions[MarioActions.JUMP.getValue()] = false;
        }

        actions[MarioActions.RIGHT.getValue()] = true;
        printArray(marioSceneArray);
        return actions;
    }

    @Override
    public String getAgentName() {
        return "[DanAndNan]";

    }

    public void printArray(int[][] array){
        for(int y = 0; y < 16; y++){
            for(int x = 0; x < 16; x++){
                if(array[x][y] > 9){
                    System.out.print(array[x][y] + " ");
                }
                else {
                    System.out.print(array[x][y] + "  ");
                }
            }
            System.out.println("");
        }
        System.out.println("------------------------------------------------------");

    }

    /*
    USE MARIO'S ENEMY ARRAY.
     */
    public boolean isGoomba(int[][] observationArray){
        for(int x = 8; x < 13; x++) {
            // Goomba val is 2.
            if (observationArray[x][8] == 2) {
                return true;
            }
        }
        return false;
    }

    /*
    USE MARIO'S SCENE ARRAY. If a pipe is more than 2 tiles tall, RIP.
     */
    public boolean isPipe(int[][] observationArray){
        for(int x = 8; x < 10; x++) {
            // Pipe val is 34.
            if (observationArray[x][8] == 34) {
                return true;
            }
        }
        return false;
    }

//    /*
//    USE MARIO'S SCENE ARRAY. If a pipe is more than 2 tiles tall, RIP.
//     */
//    public boolean isObstacle(int[][] observationArray){
//        for(int x = 8; x < 10; x++) {
//            // Pipe val is 34.
//            if (observationArray[x][8] == 34) {
//                return true;
//            }
//        }
//        return false;
//    }

    /*
    USE MARIO'S SCENE ARRAY.
     */
    public boolean inAir(int[][] observationArray){
        return (observationArray[8][9] == 0);
    }

    public boolean inAir2(int[][] observationArray){
        boolean inAir = (observationArray[8][9] == 0);
        boolean obsInFront = (observationArray[9][8] != 0); //can add actual obs values
        return (inAir && obsInFront);
    }
}
