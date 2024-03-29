package byow.InputDemo;

/**
 * Created by hug.
 */
public class StringInputDevice implements InputSource  {
    private final String input;
    private int index;

    public StringInputDevice(String s) {
        index = 0;
        input = s;
    }

    @Override
    public char getNextKey() {
        char returnChar = input.charAt(index);
        index += 1;
        return returnChar;
    }
    @Override
    public boolean possibleNextInput() {
        return index < input.length();
    }
}
