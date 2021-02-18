public class OffByOne implements CharacterComparator{
    @Override
     public boolean equalChars(char x, char y) {
        int i = x;
        int j = y;
        return Math.abs(i - j) == 1;
    }
}
