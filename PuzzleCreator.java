import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


/**
 * A class to create a Puzzle.
 * @author Ben Poarch
 */
public class PuzzleCreator {

    public ArrayList<Puzzle> puzzleArr = new ArrayList<Puzzle>();
    public SecretKey newKey;
    public SecretKey DESkey;

    /**
     * Class constructor
     */
    public PuzzleCreator() {
        //TODO: anything needed here????
    }

    /**
     * Create 4096 puzzle objects and add them to an ArrayList
     * @return An ArrayList of the 4096 puzzles
     */
    public ArrayList<Puzzle> createPuzzles() {

        int count = 1;
        int limit = 4097;

        //creates 4096 new Puzzles and adds them to the ArrayList
        while (count<limit) {
            try {
                newKey = CryptoLib.createKey(createRandomKey());
            }
            catch (Exception e) {}

            puzzleArr.add(new Puzzle(count,newKey));
            count++;
        }
        return puzzleArr;
    }

    /**
     * Create a random key, where the last 48 bits are all zeros.
     * @return The random key that is generated
     */
    public byte[] createRandomKey() {
        byte[] randomKey = new byte[8];

        //creates new array of 2 random bytes
        byte[] randomBytes = new byte[2];
        SecureRandom random = new SecureRandom();
        random.nextBytes(randomBytes);

        //adds array to start of key array, leaving remaining 48 bits as 0
        System.arraycopy(randomBytes,0,randomKey,0,2);

        return randomKey;
    }

    /**
     * Encrypt a puzzle.
     * @param key represented by array of bytes
     * @param puzzle object
     * @return An array of bytes representing the encrypted puzzle.
     */
    public byte[] encryptPuzzle(byte[] key, Puzzle puzzle) {
        try {
            DESkey = CryptoLib.createKey(key);
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.ENCRYPT_MODE, DESkey);
            byte[] encryptedPuzzleAsBytes = desCipher.doFinal(puzzle.getPuzzleAsBytes());
            return encryptedPuzzleAsBytes;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Encrypt a puzzle and store it in a file.
     * @param fileName as a string to tell method where to store puzzle
     */
    public void encryptPuzzlesToFile(String fileName) {
        try (FileOutputStream stream = new FileOutputStream(fileName)) {
            for (int x=1; x<4097; x++) {
                try {
                    stream.write(encryptPuzzle(puzzleArr.get(x).getKey().getEncoded(), puzzleArr.get(x)));
                }
                catch(Exception e) {}
            }
        }
        catch(Exception z) {}
    }

    /**
     * Find the key of a specific puzzle, using the puzzle number.
     * @param puzzleNumber of the specific puzzle
     * @return The key of the specific puzzle
     */
    public SecretKey findKey(int puzzleNumber) {

        for (int i=0; i<4096; i++) {
            if (puzzleArr.get(i).getPuzzleNumber()==puzzleNumber) {
                return puzzleArr.get(i).getKey();
            }
        }
        return null;
    }

    {}
}
