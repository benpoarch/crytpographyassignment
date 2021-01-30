import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * A class to demonstrate the puzzles working.
 * @author Ben Poarch
 */
public class Merkle {

    /**
     * Main method to demonstrate puzzles working
     */
    public static void main(String args[]) {

        //create a random key
        try{
            PuzzleCreator creator = new PuzzleCreator();
            SecretKey key = CryptoLib.createKey(creator.createRandomKey());
            Puzzle puzzle = new Puzzle(1,key);
            creator.encryptPuzzlesToFile("file");
            PuzzleCracker cracker = new PuzzleCracker("file");

            if (creator.findKey(1) == cracker.crack(1).getKey()) {
                System.out.println("Key matches!");
            }


        }
        catch (Exception e) {}





    }
}
