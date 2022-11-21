package cs.umu.se.alire.MyUnitTester;

public class UselessTests implements TestClass {
    public boolean testSuccessful() {
        return true;
    }

    public boolean testFailed() {
        return false;
    }

    public boolean testScuffed() throws RuntimeException {
        throw new RuntimeException();
    }

}
