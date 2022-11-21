package cs.umu.se.alire.MyUnitTester;

import javax.swing.*;

/**
 * Application MyUnitTester using Java Reflections to create a similar system to JUnit 3 for running tests.
 */
public class MyUnitTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
