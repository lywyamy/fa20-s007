public class Dog {
    private int size;

    public Dog(int s) {
        size = s;
    }
    
    public String noise() {
        if (size < 10) {
            return "yip";
        } 
        return "bark";
    }
}
