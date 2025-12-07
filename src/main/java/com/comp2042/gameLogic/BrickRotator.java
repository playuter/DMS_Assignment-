package com.comp2042.gameLogic;

import com.comp2042.data.NextShapeInfo;
import com.comp2042.logic.bricks.Brick;

/**
 * Manages the rotation of a Tetris brick.
 * Handles cycling through the different rotation states (orientations) of a brick.
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Gets information about the next rotation state without changing the current state.
     * Used for collision detection before applying a rotation.
     * 
     * @return NextShapeInfo containing the matrix and index of the next rotation.
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Gets the matrix of the current rotation state.
     * 
     * @return The 2D integer array representing the current brick shape.
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current rotation index.
     * 
     * @param currentShape The new rotation index.
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets the brick to control.
     * Resets rotation to the default state (0).
     * 
     * @param brick The new Brick object.
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Gets the index of the current rotation state.
     * 
     * @return The rotation index (0-3).
     */
    public int getCurrentShapeIndex() {
        return currentShape;
    }

    /**
     * Gets the underlying Brick object.
     * 
     * @return The Brick object.
     */
    public Brick getBrick() {
        return brick;
    }

}
