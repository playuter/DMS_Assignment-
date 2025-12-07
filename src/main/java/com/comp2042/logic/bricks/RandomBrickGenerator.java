package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates random Tetris bricks using a bag system or random selection.
 * Maintains a queue of upcoming bricks for the preview functionality.
 */
public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Creates a new RandomBrickGenerator.
     * Initializes the list of available bricks and pre-fills the queue of next bricks.
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    /**
     * Retrieves the next brick from the queue and replenishes the queue.
     * 
     * @return The next Brick to be played.
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 3) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }

    /**
     * Peeks at the immediate next brick without removing it from the queue.
     * 
     * @return The next Brick.
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }

    /**
     * Gets a list of the next few bricks in the queue.
     * Used for displaying the "Next Piece" preview.
     * 
     * @return A list containing the next 3 bricks.
     */
    @Override
    public List<Brick> getNextBricks() {
        return new ArrayList<>(nextBricks).subList(0, 3);
    }
}
