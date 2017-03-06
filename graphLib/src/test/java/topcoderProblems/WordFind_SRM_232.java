package topcoderProblems;

import com.sun.tools.javac.util.ArrayUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import topcoderProblems.utilities.Trie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfox on 3/5/17.
 */
public class WordFind_SRM_232 {

    //Method signature:	String[] findWords(String[] grid, String[] wordList)

    @DataProvider(name = "findWordsProvider")
    public Object[][] findWordsProvider() {
        return new Object[][] {
                {
                        new String[] {
                                "TEST",
                                "GOAT",
                                "BOAT"
                        },
                        new String[] {"GOAT", "BOAT", "TEST"},
                        new Boolean[] { true, true, true }
                },
                {
                        new String[] {
                                "SXXX",
                                "XQXM",
                                "XXLA",
                                "XXXR"
                        },
                        new String[] {"SQL", "RAM"},
                        new Boolean[] { true, false }
                },
                {
                        new String[] {
                                "EASYTOFINDEAGSRVHOTCJYG",
                                "FLVENKDHCESOXXXXFAGJKEO",
                                "YHEDYNAIRQGIZECGXQLKDBI",
                                "DEIJFKABAQSIHSNDLOMYJIN",
                                "CKXINIMMNGRNSNRGIWQLWOG",
                                "VOFQDROQGCWDKOUYRAFUCDO",
                                "PFLXWTYKOITSURQJGEGSPGG"
                        },
                        new String[] {"EASYTOFIND", "DIAG", "GOING", "THISISTOOLONGTOFITINTHISPUZZLE"},
                        new Boolean[] { true, true, true, false }
                },
        };
    }

    @Test(dataProvider = "findWordsProvider")
    public void findWordsTest(String[] grid, String[] wordList, Boolean[] expectedResults) {

        Boolean[] results = findWords(grid, wordList);
        Assert.assertEquals(results, expectedResults);
    }


    /**
     * Insert grid into trie as trie node and insert the word list into another trie. For each
     * word, convert it to a list of character and check if it matches the grid trie.
     * @param grid
     * @param wordList
     * @return
     */
    public Boolean[] findWords(String[] grid, String[] wordList) {
        Trie<Character> wordListTrie = new Trie<>();
        Character[][] convertedGrid = new Character[grid.length][grid[0].length()];

        for (int i = 0; i < grid.length; i++) {
            String row = grid[i];
            char[] chars = row.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char character = chars[j];
                convertedGrid[i][j] = new Character(character);
            }
        }

        // insert grid into trie
        for (int i = 0; i < convertedGrid.length; i++) {
            for (int j = 0; j < convertedGrid[i].length; j++) {
                // for each character, insert the word in all directions into the  trie

                // 1. horizontal
                List<Character> horizontal = new ArrayList<>();
                for (int k = j; k < convertedGrid[i].length; k++) {
                    horizontal.add(new Character(convertedGrid[i][k]));
                }

                // 2. vertical
                List<Character> vertical = new ArrayList<>();
                for (int k = i; k < convertedGrid.length; k++) {
                    vertical.add(new Character(convertedGrid[k][j]));
                }

                // 3. diagonal
                List<Character> diagonal = new ArrayList<>();
                int m = i;
                int n = j;
                while (m < convertedGrid.length && n < convertedGrid[m].length) {
                    diagonal.add(new Character(convertedGrid[m][n]));
                    m++;
                    n++;
                }
                wordListTrie.add(horizontal);
                wordListTrie.add(vertical);
                wordListTrie.add(diagonal);
            }
        }

        Boolean[] results = new Boolean[wordList.length];

        int i = 0;
        for (String word: wordList) {
            results[i] = wordListTrie.countPrefixes(convertWordToList(word)) > 0;
            i++;
        }

        return results;
    }

    public List<Character> convertWordToList(String word) {
        char[] chars = word.toCharArray();
        List<Character> characterList = new ArrayList<>();

        for (char character: chars) {
            characterList.add(new Character(character));
        }

        return characterList;
    }

}
