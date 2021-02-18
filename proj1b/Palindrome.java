public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    /* Helper function for isPalindrome. **/
    private String DequeToString(Deque d) {
        String string = "";
        while (d.size() > 0) {
            string += d.removeFirst();
        }
        return string;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        Deque<Character> d = wordToDeque(word);
        if (d.size() == 0 || d.size() == 1) {
            return true;
        } else if (d.removeFirst() == d.removeLast()) {
            return isPalindrome(DequeToString(d));
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        } else if (cc == null) {
            return isPalindrome(word);
        } else {
            Deque<Character> d = wordToDeque(word);
            if (d.size() == 0 || d.size() == 1) {
                return true;
            } else if (cc.equalChars(d.removeFirst(), d.removeLast())) {
                return isPalindrome(DequeToString(d), cc);
            } else return false;
        }
    }
}
