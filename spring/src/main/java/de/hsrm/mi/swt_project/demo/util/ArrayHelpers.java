package de.hsrm.mi.swt_project.demo.util;

/**
 * This class contains static methods helping with
 * array manipulation. 
 * 
 * @author Sascha Scheid
 */
public class ArrayHelpers {

    /**
     * Copies elements from one array to another array.
     * 
     * @param <T> Type of the array elements.
     * @param src Source array from which elements will be copied into destination array
     * @param dest Destination array where the copied elements will be stored.
     */
    public static <T> void copy(T[] src, T[] dest) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException("src and dest arrays are not allowed to be null.");
        }

        if (dest.length < src.length) {
            throw new IllegalArgumentException("dest array must be bigger than src array. src size: %d, dest size: %d".formatted(src.length, dest.length));
        }

        for (int i = 0; i < src.length; i++) {
            dest[i] = src[i];
        }
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
            throw new IllegalArgumentException("src and dest arrays are not allowed to be null.");
        }

        if (dest.length < src.length) {
            throw new IllegalArgumentException("dest array must be bigger than src array. src size: %d, dest size: %d".formatted(src.length, dest.length));
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
            throw new IllegalArgumentException("src and dest arrays are not allowed to be null.");
        }

        if (dest.length > src.length) {
            throw new IllegalArgumentException("dest array size must be smaller than or equal to src array size.");
        }

        if (src.length - dest.length < offset) {
            throw new IllegalArgumentException("offset must be smaller than or equal to %d, was %d".formatted(src.length - dest.length, offset));
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
            throw new IllegalArgumentException("src and dest arrays are not allowed to be null.");
        }

        if (!isSquare(src) || !isSquare(dest)) {
            throw new IllegalArgumentException("src and dest arrays must both be square shaped.");
        }

        if (dest.length > src.length) {
            throw new IllegalArgumentException("dest array size must be smaller than or equal to src array size.");
        }

        if (src.length - dest.length < offset) {
            throw new IllegalArgumentException("offset must be smaller than or equal to %d, was %d".formatted(src.length - dest.length, offset));
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
            throw new IllegalArgumentException("src and dest arrays are not allowed to be null.");
        }

        if (src.length > dest.length) {
            throw new IllegalArgumentException("dest array size must be bigger than or equal to src array size.");
        }

        if (dest.length - src.length < offset) {
            throw new IllegalArgumentException("offset must be smaller than or equal to %d, was %d".formatted(dest.length - src.length, offset));
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
            throw new IllegalArgumentException("src and dest arrays are not allowed to be null.");
        }

        if (!isSquare(src) || !isSquare(dest)) {
            throw new IllegalArgumentException("src and dest arrays must both be square shaped.");
        }

        if (src.length > dest.length) {
            throw new IllegalArgumentException("dest array size must be bigger than or equal to src array size.");
        }

        if (dest.length - src.length < offset) {
            throw new IllegalArgumentException("offset must be smaller than or equal to %d, was %d".formatted(dest.length - src.length, offset));
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
            throw new IllegalArgumentException("src and dest arrays are not allowed to be null.");
        }

        if (src.length % 2 != dest.length % 2) {
            throw new IllegalArgumentException("Transfer is only supported if src and dest are both of even or both of odd size.");
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
            throw new IllegalArgumentException("src and dest arrays are not allowed to be null.");
        }

        if (!isSquare(src) || !isSquare(dest)) {
            throw new IllegalArgumentException("src and dest arrays must both be square shaped.");
        }

        if (src.length % 2 != dest.length % 2) {
            throw new IllegalArgumentException("Transfer2D is only supported if src and dest are both of even or both of odd size.");
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
            throw new IllegalArgumentException("toCheck array is not allowed to be null");
        }

        for (T[] row: toCheck) {
            if (row.length != toCheck.length) {
                return false;
            }
        }
        return true;
    }
    
}
