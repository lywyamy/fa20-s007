public class OffByN implements CharacterComparator {
    private int n;
    public OffByN(int n) {
        this.n = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int i = x;
        int j = y;
        return Math.abs(i - j) == n;
    }
}
