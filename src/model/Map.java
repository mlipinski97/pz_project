package model;

import Entity.Block;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private int windowHeight = 840;
    private int windowWidth = 960;
    private int blockWidth = 60;
    private int blockHeight = 60;
    private int rowNumber;
    private int columnNumber;
    public ArrayList<Block> blocksArray;
    private AnchorPane gamePane;

    /**
     * Map constructor creating random map for given window and block width and height
     * @param windowHeight
     * @param windowWidth
     * @param blockHeight
     * @param blockWidth
     * @param gamePane
     */
    public Map(int windowHeight, int windowWidth, int blockHeight, int blockWidth, AnchorPane gamePane){
        this.gamePane = gamePane;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;
        rowNumber = this.windowHeight/this.blockHeight;
        columnNumber = this.windowWidth/this.blockWidth;
        generateMap(this.gamePane);
    }
    /**
     * Map constructor creating random map on set map and block width and height
     */
    public Map(AnchorPane gamePane){
        this.gamePane = gamePane;
        rowNumber = this.windowHeight/this.blockHeight;
        columnNumber = this.windowWidth/this.blockWidth;
        generateMap(this.gamePane);
    }

    /**
     * merthod clearing currently generated map
     */
    public void clearMap(){
        for(Block b : blocksArray)
        gamePane.getChildren().remove(b.getBlockSprite());
    }

    private void generateMap(AnchorPane gamePane){
        blocksArray = new ArrayList<>();
        Random rand = new Random();
        int randomSeed;
        for(int row = 0; row < rowNumber; row++){
            for(int column = 0; column < columnNumber; column++){
                if(row == 0){

                } else if(row == 1){
                    if(column == 0){
                        blocksArray.add(new Block(column*blockWidth, row*blockHeight, Block.BlockTypeEnum.MINECART, gamePane));
                    }
                    blocksArray.add(new Block(column*blockWidth, row*blockHeight, Block.BlockTypeEnum.NONE, gamePane));
                } else if(row == 2){
                    blocksArray.add(new Block(column*blockWidth, row*blockHeight, Block.BlockTypeEnum.EARTH, gamePane));
                } else if(row > 2){
                    randomSeed = rand.nextInt(100)+1;
                    if(randomSeed > 95){
                        blocksArray.add(new Block(column*blockWidth, row*blockHeight, Block.BlockTypeEnum.STATUE, gamePane));
                    } else if(randomSeed <= 95 && randomSeed > 75){
                        blocksArray.add(new Block(column*blockWidth, row*blockHeight, Block.BlockTypeEnum.GOLD, gamePane));

                    } else if(randomSeed <= 75 && randomSeed > 55){
                        blocksArray.add(new Block(column*blockWidth, row*blockHeight, Block.BlockTypeEnum.COAL, gamePane));
                    } else{
                        blocksArray.add(new Block(column*blockWidth, row*blockHeight, Block.BlockTypeEnum.STONE, gamePane));
                    }
                }
            }
        }
    }

    public Block getBlock(int coordX, int coordY){
        for(Block b : blocksArray){
            if(b.getCurrentX() == coordX && b.getCurrentY() == coordY){
                return b;
            }
        }
        return null;
    }
}
