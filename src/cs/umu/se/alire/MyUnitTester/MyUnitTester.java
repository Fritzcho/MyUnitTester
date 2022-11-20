package cs.umu.se.alire.MyUnitTester;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyUnitTester {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
