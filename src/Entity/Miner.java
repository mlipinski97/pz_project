package Entity;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import static view.GameViewManager.*;

public class Miner{

    private static final String PATH_MINER_SPRITE_UP = "res/miner_up.png";
    private static final String PATH_MINER_SPRITE_DOWN = "res/miner_down.png";
    private static final String PATH_MINER_SPRITE_RIGHT = "res/miner_right.png";
    private static final String PATH_MINER_SPRITE_LEFT = "res/miner_left.png";
    private static final String PATH_MINER_ANIMATION_SHEET_LEFT = "res/miner_animation_left.png";
    private static final String PATH_MINER_ANIMATION_SHEET_RIGHT = "res/miner_animation_right.png";
    private static final String PATH_MINER_ANIMATION_SHEET_UP = "res/miner_animation_up.png";
    private static final String PATH_MINER_ANIMATION_SHEET_DOWN = "res/miner_animation_down.png";

    AnchorPane gamePane;
    ImageView minerSprite;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private boolean isAbleToMove;
    private int currentX, currentY;
    MinerDirectionEnum minerDirection;
    private Shape shadowOverlay;


    public enum MinerDirectionEnum {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    /**
     * Miner constructor creating and spawning miner on giver coords and Pane
     * @param pane Oane on which Miner will be spawn
     * @param spawnX spawn X coordinates
     * @param spawnY spawn Y coordinates
     */
    public Miner(AnchorPane pane, int spawnX, int spawnY){
        gamePane = pane;
        currentX = spawnX;
        currentY = spawnY;
        initMiner();

    }

    private void initMiner(){
        minerSprite = new ImageView(PATH_MINER_SPRITE_LEFT);  //loading sprite
        minerSprite.setLayoutX(currentX);
        minerSprite.setLayoutY(currentY);
        gamePane.getChildren().add(minerSprite);
        minerDirection = MinerDirectionEnum.LEFT;
        isAbleToMove = true;
        generateShadow();
    }

    public void setDownKeyPressed(boolean downKeyPressed) {
        isDownKeyPressed = downKeyPressed;
    }

    public void setUpKeyPressed(boolean upKeyPressed) {
        isUpKeyPressed = upKeyPressed;
    }

    public void setLeftKeyPressed(boolean leftKeyPressed) {
        isLeftKeyPressed = leftKeyPressed;
    }

    public void setRightKeyPressed(boolean rightKeyPressed) {
        isRightKeyPressed = rightKeyPressed;
    }

    public Boolean getUpKeyPressed(){
        return isUpKeyPressed;
    }

    public Boolean getDownKeyPressed(){
        return isDownKeyPressed;
    }

    public Boolean getLeftKeyPressed(){
        return isLeftKeyPressed;
    }

    public Boolean getRightKeyPressed(){
        return isRightKeyPressed;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
        minerSprite.setLayoutX(this.currentX);
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
        minerSprite.setLayoutX(this.currentY);
    }

    public Boolean getAbleToMove(){
        return isAbleToMove;
    }

    public void setAbleToMove(boolean newValue){
        isAbleToMove = newValue;
    }

    /**
     * method changing position of the miner by one width - height block
     * @param direction Miner Enum direction
     */
    public void move(MinerDirectionEnum direction){
        if(isAbleToMove){
            switch(direction){
                case LEFT:
                    if(checkForCollision(MinerDirectionEnum.LEFT)) {
                        minerSprite.setLayoutX(currentX - 60);
                        currentX -= 60;
                        updateShadow();
                    }
                    if(minerDirection != MinerDirectionEnum.LEFT) {
                        updateSprite(MinerDirectionEnum.LEFT);
                    }
                    System.out.println(minerDirection);
                    break;
                case RIGHT:
                    if(checkForCollision(MinerDirectionEnum.RIGHT)) {
                        minerSprite.setLayoutX(currentX + 60);
                        currentX += 60;
                        updateShadow();                    }
                    if (minerDirection != MinerDirectionEnum.RIGHT) {
                        updateSprite(MinerDirectionEnum.RIGHT);
                    }
                    System.out.println(minerDirection);
                    break;
                case UP:
                    if(checkForCollision(MinerDirectionEnum.UP)) {
                        minerSprite.setLayoutY(currentY - 60);
                        currentY -= 60;
                        updateShadow();                    }
                    if (minerDirection != MinerDirectionEnum.UP) {
                        updateSprite(MinerDirectionEnum.UP);
                    }
                    System.out.println(minerDirection);
                    break;
                case DOWN:
                    if(checkForCollision(MinerDirectionEnum.DOWN)) {
                        minerSprite.setLayoutY(currentY + 60);
                        currentY += 60;
                        updateShadow();
                    }
                    if(minerDirection != MinerDirectionEnum.DOWN){
                        updateSprite(MinerDirectionEnum.DOWN);
                    }
                    System.out.println(minerDirection);
                    break;
            }
        }
    }

    private boolean checkForCollision(MinerDirectionEnum direction) {
        Block.BlockTypeEnum blockType;
        Block block;
        switch (direction){
            case LEFT:
                block = gameMap.getBlock(currentX - 60, currentY);
                if(block == null){
                    return false;
                }
                blockType = block.getBlockType();
                if(blockType == Block.BlockTypeEnum.NONE || blockType == Block.BlockTypeEnum.MINECART){
                    return true;
                } else{
                    return false;
                }
            case RIGHT:
                block = gameMap.getBlock(currentX + 60, currentY);
                if(block == null){
                    return false;
                }
                blockType = block.getBlockType();
                if(blockType == Block.BlockTypeEnum.NONE || blockType == Block.BlockTypeEnum.MINECART){
                    return true;
                } else{
                    return false;
                }
            case UP:
                block = gameMap.getBlock(currentX, currentY - 60);
                if(block == null){
                    return false;
                }
                blockType = block.getBlockType();
                if(blockType == Block.BlockTypeEnum.NONE || blockType == Block.BlockTypeEnum.MINECART){
                    return true;
                } else{
                    return false;
                }
            case DOWN:
                block = gameMap.getBlock(currentX, currentY + 60);
                if(block == null){
                    return false;
                }
                blockType = block.getBlockType();
                if(blockType == Block.BlockTypeEnum.NONE || blockType == Block.BlockTypeEnum.MINECART){
                    return true;
                } else{
                    return false;
                }
                default:
                    return false;
        }
    }

    private void updateSprite(MinerDirectionEnum newDirection){
        minerDirection = newDirection;
        switch(minerDirection){
            case UP:
                minerSprite.setImage(new Image(PATH_MINER_SPRITE_UP));  //loading sprite
                System.out.println("miner sprite path changed to: " + PATH_MINER_SPRITE_UP);
                break;
            case DOWN:
                minerSprite.setImage(new Image(PATH_MINER_SPRITE_DOWN));  //loading sprite
                System.out.println("miner sprite path changed to: " + PATH_MINER_SPRITE_DOWN);
                break;
            case LEFT:
                minerSprite.setImage(new Image(PATH_MINER_SPRITE_LEFT));  //loading sprite
                System.out.println("miner sprite path changed to: " + PATH_MINER_SPRITE_LEFT);
                break;
            case RIGHT:
                minerSprite.setImage(new Image(PATH_MINER_SPRITE_RIGHT));  //loading sprite
                System.out.println("miner sprite path changed to: " + PATH_MINER_SPRITE_RIGHT);
                break;
        }
    }

    /**
     * method calling for mine animation and mining a block in front of the miner
     */
    public void mine() {
        Block block;
        if(isAbleToMove) {
            minerSprite.setVisible(false);
            ImageView minerAnimation;
            switch (minerDirection) {
                case LEFT:
                    minerAnimation = new ImageView(PATH_MINER_ANIMATION_SHEET_LEFT);
                    block = gameMap.getBlock(currentX - 60, currentY);
                    if(block != null){
                        block.mineEndurance();
                    }
                    break;
                case RIGHT:
                    minerAnimation = new ImageView(PATH_MINER_ANIMATION_SHEET_RIGHT);
                    block = gameMap.getBlock(currentX + 60, currentY);
                    if(block != null){
                        block.mineEndurance();
                    }
                    break;
                case UP:
                    minerAnimation = new ImageView(PATH_MINER_ANIMATION_SHEET_UP);
                    block = gameMap.getBlock(currentX, currentY - 60);
                    if(block != null){
                        block.mineEndurance();
                    }
                    break;
                case DOWN:
                    minerAnimation = new ImageView(PATH_MINER_ANIMATION_SHEET_DOWN);
                    block = gameMap.getBlock(currentX, currentY + 60);
                    if(block != null){
                        block.mineEndurance();
                    }
                    break;
                default:
                    minerAnimation = new ImageView(PATH_MINER_ANIMATION_SHEET_LEFT);
            }
            minerAnimation.setViewport(new Rectangle2D(0, 0, 60, 60)); //preventing from showing whole sprite sheet
            gamePane.getChildren().add(minerAnimation);
            minerAnimation.setLayoutX(currentX);
            minerAnimation.setLayoutY(currentY);
            final Animation miningAnimation = new MineAnimation(
                    minerAnimation,
                    Duration.millis(500),
                    3, 3,
                    0, 0,
                    60, 60
            );
            miningAnimation.setCycleCount(1);
            miningAnimation.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gamePane.getChildren().remove(minerAnimation);
                    minerSprite.setVisible(true);
                    if(gameInterface.getPickaxeDurability() <=0){
                        setAbleToMove(false);
                    }else{
                        setAbleToMove(true);
                    }
                }
            });
            miningAnimation.play();
        }
    }

    private void generateShadow(){
        shadowOverlay = Shape.subtract(new Rectangle(0, 0, 960, 840), new Circle(currentX+30, currentY+30, 180));
        gamePane.getChildren().add(shadowOverlay);
    }

    /**
     * method updating shadow around miner position
     */
    public void updateShadow(){
        gamePane.getChildren().remove(shadowOverlay);
        shadowOverlay = Shape.subtract(new Rectangle(0, 0, 960, 840), new Circle(currentX+30, currentY+30, 180));
        gamePane.getChildren().add(shadowOverlay);
        gameInterface.toFront();
    }
}
