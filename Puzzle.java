import javax.crypto.SecretKey;

/**
 * A class to store a puzzle.
 * @author Ben Poarch
 */
public class Puzzle{

    String plainText;
    int puzzleNumber;
    SecretKey secretKey;
    //SecretKey puzzleKey;

    /**
     * Class constructor.
     * @param puzzleNumber representing which of the 1-4096 puzzles it is
     * @param secretKey a key
     */
    public Puzzle(int puzzleNumber, SecretKey secretKey){
        this.puzzleNumber=puzzleNumber;
        this.secretKey=secretKey;
    }

    /**
     * Get the puzzle number.
     * @return The puzzle number
     */
    public int getPuzzleNumber() {
        return puzzleNumber;
    }

    /**
     * Get the key of the puzzle.
     * @return The key of the puzzle
     */
    public SecretKey getKey() {
        return secretKey;
    }

    /**
     * Convert each element of the puzzle into bytes
     * and add these to an array of bytes
     * @return The puzzle represented as an array of bytes
     */
    public byte[] getPuzzleAsBytes() {

        //create byte arrays for each element of puzzle
        byte[] plainTextAsBytes = new byte[16];
        byte[] puzzleNumAsBytes = CryptoLib.smallIntToByteArray(puzzleNumber);
        byte[] keyAsBytes = secretKey.getEncoded();

        //combine into one byte array (length = 26)
        byte[] puzzleAsBytes = new byte[26];
        System.arraycopy(plainTextAsBytes,0,puzzleAsBytes,0,16);
        System.arraycopy(puzzleNumAsBytes,0,puzzleAsBytes,16,2);
        System.arraycopy(keyAsBytes,0,puzzleAsBytes,18,8);

        return puzzleAsBytes;
    }
}
