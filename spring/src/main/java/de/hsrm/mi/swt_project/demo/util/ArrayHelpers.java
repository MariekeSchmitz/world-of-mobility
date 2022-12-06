package de.hsrm.mi.swt_project.demo.util;

import java.util.Arrays;

/**
 * This class contains static methods helping with
 * array manipulation. 
 * 
 * @author Sascha Scheid
 */
public class ArrayHelpers {

    protected static final String ARRAY_NULL_ERROR = "input array is not allowed to be null.";
    protected static final String SRC_DEST_NULL_ERROR = "src and dest arrays are not allowed to be null.";
    protected static final String SRC_DEST_SQUARED_ERROR = "src and dest arrays must both be square shaped.";
    protected static final String SRC_DEST_EVEN_ODD_ERROR = "src and dest must both be of even or both of odd size";
    protected static final String DEST_SMALLER_SRC_ERROR = "dest array size must be smaller than or equal to src array size. src size: %d, dest size: %d";
    protected static final String DEST_BIGGER_SRC_ERROR = "dest array must be bigger than src array. src size: %d, dest size: %d";
    protected static final String OFFSET_SMALLER_EQUAL_ERROR = "offset must be smaller than or equal to %d, was %d";

    /**
     * Private default constructor to avoid instanciation.
     */
    private ArrayHelpers() {
        // do nothing
    }


    /**
     * Copies elements from one array to another array.
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from which elements will be copied into destination array
     * @param dest Destination array where the copied elements will be stored.
     */
    public static <T> void copy(T[] src, T[] dest) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException(SRC_DEST_NULL_ERROR);
        }

        if (dest.length < src.length) {
            throw new IllegalArgumentException(DEST_BIGGER_SRC_ERROR.formatted(src.length, dest.length));
        }

        System.arraycopy(src, 0, dest, 0, src.length);
    }

    /**
     * Copies elements from one 2D array to another 2D array.
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from which elements will be copied into destination array
     * @param dest Destination array where the copied elements will be stored.
     */
    public static <T> void copy2D(T[][] src, T[][] dest) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException(SRC_DEST_NULL_ERROR);
        }

        if (dest.length < src.length) {
            throw new IllegalArgumentException(DEST_BIGGER_SRC_ERROR.formatted(src.length, dest.length));
        }

        for (int i = 0; i < src.length; i++) {
            copy(src[i], dest[i]);
        }
    }


    /**
     * Shrinks array into another array of smaller or equal size.
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from where the elements will be transfered to destination array.
     * @param dest Destination array where the elements will be stored in.
     * @param offset Offset by which elements will be shifted row- and column-wise inside destination array.
     */
    public static <T> void shrink(T[] src, T[] dest, int offset) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException(SRC_DEST_NULL_ERROR);
        }

        if (dest.length > src.length) {
            throw new IllegalArgumentException(DEST_SMALLER_SRC_ERROR.formatted(src.length, dest.length));
        }

        if (src.length - dest.length < offset) {
            throw new IllegalArgumentException(OFFSET_SMALLER_EQUAL_ERROR.formatted(src.length - dest.length, offset));
        }

        for (int i = 0; i < dest.length; i++) {
            dest[i] = src[i + offset];
        }
    }

    /**
     * Shrinks 2D array into another 2D array of smaller or equal size.
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from where the elements will be transfered to destination array.
     * @param dest Destination array where the elements will be stored in.
     * @param offset Offset by which elements will be shifted row- and column-wise inside destination array.
     */
    public static <T> void shrink2D(T[][] src, T[][] dest, int offset) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException(SRC_DEST_NULL_ERROR);
        }

        if (!isSquare(src) || !isSquare(dest)) {
            throw new IllegalArgumentException(SRC_DEST_SQUARED_ERROR);
        }

        if (dest.length > src.length) {
            throw new IllegalArgumentException(DEST_SMALLER_SRC_ERROR.formatted(src.length, dest.length));
        }

        if (src.length - dest.length < offset) {
            throw new IllegalArgumentException(OFFSET_SMALLER_EQUAL_ERROR.formatted(src.length - dest.length, offset));
        }

        for (int i = 0; i < dest.length; i++) {
            shrink(src[i + offset], dest[i], offset);
        }
    }

    /**
     * Expands array into another array of bigger or equal size.
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from where the elements will be transfered to destination array.
     * @param dest Destination array where the elements will be stored in.
     * @param offset Offset by which elements will be shifted column-wise inside destination array.
     * 
     */
    public static <T> void expand(T[] src, T[] dest, int offset) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException(SRC_DEST_NULL_ERROR);
        }

        if (src.length > dest.length) {
            throw new IllegalArgumentException(DEST_BIGGER_SRC_ERROR.formatted(src.length, dest.length));
        }

        if (dest.length - src.length < offset) {
            throw new IllegalArgumentException(OFFSET_SMALLER_EQUAL_ERROR.formatted(dest.length - src.length, offset));
        }

        for (int i = 0; i < src.length; i++) {
            dest[i + offset] = src[i];
        }
    }

    /**
     * Expands a 2D array into another 2D array of bigger or equal size.
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from where the elements will be transfered to destination array.
     * @param dest Destination array where the elements will be stored in.
     * @param offset Offset by which elements will be shifted row- and column-wise inside destination array.
     */
    public static <T> void expand2D(T[][] src, T[][] dest, int offset) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException(SRC_DEST_NULL_ERROR);
        }

        if (!isSquare(src) || !isSquare(dest)) {
            throw new IllegalArgumentException(SRC_DEST_SQUARED_ERROR);
        }

        if (src.length > dest.length) {
            throw new IllegalArgumentException(DEST_BIGGER_SRC_ERROR.formatted(src.length, dest.length));
        }

        if (dest.length - src.length < offset) {
            throw new IllegalArgumentException(OFFSET_SMALLER_EQUAL_ERROR.formatted(dest.length - src.length, offset));
        }

        for (int i = 0; i < src.length; i++) {
            expand(src[i], dest[i + offset], offset);
        }
    }

    /**
     * Transfers elements from one array into another.
     * 
     * Elements will be centered inside the destination array.
     * 
     * Examples:
     * 
     * (1) 2 src -> 4 dest
     * 
     *  --- ---        --- --- --- ---
     * | 1 | 1 |  ->  |   | 1 | 1 |   |
     *  --- ---        --- --- --- ---
     * 
     * (2) 4 src -> 2 dest
     * 
     *  --- --- --- ---        --- ---
     * | 5 | 1 | 1 | 5 |  ->  | 1 | 1 |
     *  --- --- --- ---        --- ---
     * 
     * (3) 2 src -> 2 dest
     * 
     *  --- ---         --- ---
     * | 1 | 1 |  ->   | 1 | 1 |
     *  --- ---         --- ---
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from where the elements will be transfered to destination array.
     * @param dest Destination array where the elements will be stored in.
     */
    public static <T> void transfer(T[] src, T[] dest) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException(SRC_DEST_NULL_ERROR);
        }

        if (src.length % 2 != dest.length % 2) {
            throw new IllegalArgumentException(SRC_DEST_EVEN_ODD_ERROR);
        }

        int offset = (src.length - dest.length) / 2;

        if (offset < 0) {
            expand(src, dest, Math.abs(offset));
        } else if (offset > 0) {
            shrink(src, dest, Math.abs(offset));
        } else {
            copy(src, dest);
        }

    }

    /**
     * Transfers elements from one square 2D array into another square 2D array.
     * 
     * Elements will be centered inside the destination array.
     * 
     * Examples:
     * 
     * (1) 2x2 src -> 4x4 dest
     * 
     *                 --- --- --- ---
     *                |   |   |   |   |
     *  --- ---        --- --- --- ---
     * | 1 | 1 |      |   | 1 | 1 |   |
     *  --- ---   ->   --- --- --- ---
     * | 1 | 1 |      |   | 1 | 1 |   |
     *  --- ---        --- --- --- ---
     *                |   |   |   |   |
     *                 --- --- --- ---
     * 
     * (2) 4x4 src -> 2x2 dest
     * 
     *  --- --- --- ---
     * | 5 | 5 | 5 | 5 |
     *  --- --- --- ---        --- ---
     * | 5 | 1 | 1 | 5 |      | 1 | 1 |
     *  --- --- --- ---   ->   --- ---
     * | 5 | 1 | 1 | 5 |      | 1 | 1 |
     *  --- --- --- ---        --- ---
     * | 5 | 5 | 5 | 5 |
     *  --- --- --- ---
     * 
     * (3) 2x2 src -> 2x2 dest
     * 
     *
     *  --- ---        --- ---
     * | 1 | 1 |      | 1 | 1 |
     *  --- ---   ->   --- ---
     * | 1 | 1 |      | 1 | 1 |
     *  --- ---        --- ---
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from where the elements will be transfered to destination array.
     * @param dest Destination array where the elements will be stored in.
     */
    public static <T> void transfer2D(T[][] src, T[][] dest) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException(SRC_DEST_NULL_ERROR);
        }

        if (!isSquare(src) || !isSquare(dest)) {
            throw new IllegalArgumentException(SRC_DEST_SQUARED_ERROR);
        }

        if (src.length % 2 != dest.length % 2) {
            throw new IllegalArgumentException(SRC_DEST_EVEN_ODD_ERROR);
        }


        int offset = (src.length - dest.length) / 2;
        if (offset < 0) {
            expand2D(src, dest, Math.abs(offset));
        } else if (offset > 0) {
            shrink2D(src, dest, Math.abs(offset));
        } else {
            copy2D(src, dest);
        }
    }

    /**
     * Checks if a 2D array is a square.
     * 
     * @param <T> Type of the array elements.
     * @param toCheck Array that will be checked.
     * @return True if the 2D array is a square, else false.
     */
    public static <T> boolean isSquare(T[][] toCheck) {

        if (toCheck == null) {
            throw new IllegalArgumentException(ARRAY_NULL_ERROR);
        }

        for (T[] row: toCheck) {
            if (row.length != toCheck.length) {
                return false;
            }
        }
        return true;
    }
    
}
