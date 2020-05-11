package Entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static utils.Sound.playEffect;
import static view.GameViewManager.gameInterface;

public class Block {

    private static final String PATH_BLOCK_SPRITE_STONE_2 = "res/block_stone_2.png";
    private static final String PATH_BLOCK_SPRITE_STONE_1 = "res/block_stone_1.png";
    private static final String PATH_BLOCK_SPRITE_GOLD_1 =  "res/block_gold_1.png";
    private static final String PATH_BLOCK_SPRITE_GOLD_2 = "res/block_gold_2.png";
    private static final String PATH_BLOCK_SPRITE_EARTH = "res/block_earth_1.png";
    private static final String PATH_BLOCK_SPRITE_COAL = "res/block_coal_1.png";
    private static final String PATH_BLOCK_SPRITE_STATUE = "res/icon_statue.png";
    private static final String PATH_BLOCK_SPRITE_NONE = "res/block_none.png";
    private static final String PATH_BLOCK_SPRITE_MINECART = "res/block_minecart.png";
    private static final int BLOCK_WIDTH = 60;
    private static final int BLOCK_HEIGHT = 60;

    private int currentX;
    private int currentY;
    private ImageView blockSprite;
    private BlockTypeEnum blockType;
    private int blockEndurance;
    private AnchorPane gamePane;

    public int getCurrentY() {
        return currentY;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public BlockTypeEnum getBlockType() {
        return blockType;
    }

    public ImageView getBlockSprite(){
        return blockSprite;
    }

    /**
     * Block constructor creating block with chosen sprite and parameters
     * @param spawnX spawn point of X
     * @param spawnY spawn point of Y
     * @param blockType Enum of block type
     * @param gamePane gamePane to which Block will be added
     */
    public Block(int spawnX, int spawnY, BlockTypeEnum blockType, AnchorPane gamePane){
        this.gamePane = gamePane;
        this.currentX = spawnX;
        this.currentY = spawnY;
        this.blockSprite = new ImageView();
        setBlockType(blockType);
        blockSprite.setFitHeight(60);
        blockSprite.setFitWidth(60);
        blockSprite.setTranslateX(currentX);
        blockSprite.setTranslateY(currentY);
        this.gamePane.getChildren().add(blockSprite);
        System.out.println("spawning block coords:\nX:" + currentX + "\nY:" + currentY);
    }

    private void setBlockType(BlockTypeEnum blockType) {
        this.blockType = blockType;
        switch (this.blockType){
            case EARTH:
                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_EARTH));
                blockEndurance = 1;
                break;
            case STONE:
                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_STONE_2));
                blockEndurance = 2;
                break;
            case COAL:
                blockEndurance = 1;
                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_COAL));
                break;
            case GOLD:
                blockEndurance = 2;
                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_GOLD_2));
                break;
            case STATUE:
                blockEndurance = 1;
                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_STATUE));
                break;
            case NONE:
                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_NONE));
                break;
            case MINECART:
                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_MINECART));
                break;
        }
    }

    /**
     * method changing state of mined block, always called on Block Object
      */
    public void mineEndurance(){
        switch(blockType){
            case EARTH://base endurance of earth is set to 1
                playEffect(1);
                gameInterface.setPickaxeDurability(gameInterface.getPickaxeDurability() - 1);
                setBlockType(BlockTypeEnum.NONE);
                break;
            case STONE: //base endurance of stone is set to 2
                playEffect(1);
                gameInterface.setPickaxeDurability(gameInterface.getPickaxeDurability() - 1);
                if(blockEndurance > 0){
                    blockEndurance--;
                    if(blockEndurance < 1){
                        setBlockType(BlockTypeEnum.NONE);

                    } else{
                        switch (blockEndurance){
                            case 1:
                                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_STONE_1));
                                break;
                        }
                    }
                }
                break;
            case COAL://base endurance of coal is set to 1
                playEffect(1);
                gameInterface.setPickaxeDurability(gameInterface.getPickaxeDurability() - 1);
                setBlockType(BlockTypeEnum.NONE);
                gameInterface.setGold(gameInterface.getGold() + 1);
                break;
            case GOLD://base endurance of gold is set to 2
                playEffect(1);
                gameInterface.setPickaxeDurability(gameInterface.getPickaxeDurability() - 1);
                if(blockEndurance > 0){
                    blockEndurance--;
                    if(blockEndurance < 1){
                        setBlockType(BlockTypeEnum.NONE);
                        gameInterface.setGold(gameInterface.getGold() + 2);
                    } else{
                        switch (blockEndurance){
                            case 1:
                                blockSprite.setImage(new Image(PATH_BLOCK_SPRITE_GOLD_1));
                                break;
                        }
                    }
                }
                break;
            case STATUE:
                playEffect(1);
                gameInterface.setPickaxeDurability(gameInterface.getPickaxeDurability() - 1);
                setBlockType(BlockTypeEnum.NONE);
                gameInterface.setStatue(gameInterface.getStatue() + 1);
                break;
        }
    }

    public enum BlockTypeEnum {
        NONE,
        EARTH,
        STONE,
        COAL,
        GOLD,
        STATUE,
        MINECART
    }
}
