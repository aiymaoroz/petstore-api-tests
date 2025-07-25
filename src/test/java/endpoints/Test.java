package endpoints;

public class Test {
    //write a method to add two int numbers and return the result
    public int add(int a, int b) {
        return a + b;
    }
    //write tests for this method
    public static void main(String[] args) {
        Test test = new Test();
        int result = test.add(2, 3);
        System.out.println("The result of adding 2 and 3 is: " + result); // Expected output: 5
    }
}
