import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testOne() {
        final int numberOfTests = 8;
        String methodCalls;
        String message = "";

        StudentArrayDeque<Integer> student1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution1 = new ArrayDequeSolution<>();

        for (int i = 0; i < numberOfTests; i ++) {
            int randomInt = StdRandom.uniform(0, 10);
            if (randomInt < 5) {
                student1.addFirst(randomInt);
                solution1.addFirst(randomInt);
                methodCalls = "addFirst(" + randomInt + ")" + "\n";
                message += methodCalls;
            } else {
                student1.addLast(randomInt);
                solution1.addLast(randomInt);
                methodCalls = "addLast(" + randomInt + ")" + "\n";
                message += methodCalls;
            }
        }

        while(!student1.isEmpty()) {
            double randomDouble = StdRandom.uniform();
            if (randomDouble < 0.5) {
                methodCalls = "removeFirst()" + "\n";
                message += methodCalls;
                assertEquals(message, student1.removeFirst(), solution1.removeFirst());
            } else {
                methodCalls = "removeLast()" + "\n";
                message += methodCalls;
                assertEquals(message, student1.removeLast(), solution1.removeLast());
            }
        }
    }
}
