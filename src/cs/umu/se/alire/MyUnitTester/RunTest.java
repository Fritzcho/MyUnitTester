package cs.umu.se.alire.MyUnitTester;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class RunTest retrieves a test class based on a given string containing the classname and
 * runs the test class, meaning running all methods in the class.
 * The results of the tests are then appended to the GUI output JTextArea.
 * The class works through a SwingWorker to not freeze the UI.
 */
public class RunTest {

    //Track number of successful tests
    private int nrOfSuc;
    //Track number of failed tests
    private int nrOfFail;
    //Track number of tests that failed because of an exception
    private int nrOfExc;
    public RunTest() {}

    /**
     * Method run that runs the tests of the given test class in a SwingWorker and then executes the SwingWorker
     * @param input String containing the name of the test class
     * @param text JTextArea that results can be appended to
     */
    public void run(String input, JTextArea text) {
        nrOfSuc = 0;
        nrOfFail = 0;
        nrOfExc = 0;
        SwingWorker<Void, Void> sw1 = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                Class<?> reqClass = requireClass(input, text);
                Object instClass = instantiateClass(reqClass, text);
                invokeMethods(instClass, reqClass, text);
                getResults(text);
                return null;
            }
        };
        sw1.execute();
    }

    /**
     * Method requireClass creates a class object based on the given test-class classname
     * @param input String containing the name of the test-class
     * @param output JTextArea that possible exceptions can be appended to
     * @return Class object
     */
    private Class<?> requireClass(String input, JTextArea output) {
        Class<?> reqClass = null;
        try {
            reqClass = Class.forName(input);
        } catch (ClassNotFoundException e) {
            output.append("Exception: Class not found: "+e.getMessage()+"\n");
        }
        return reqClass;
    }

    /**
     * Method instantiateClass instantiates an Object object from a Class object
     * @param reqClass Class object of the required class
     * @param output JTextArea that possible exceptions can be appended to
     * @return class instance in the form of an Object
     */
    private Object instantiateClass(Class<?> reqClass, JTextArea output) {
        Object classInst = null;
        try {
            classInst = reqClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException |
                 InvocationTargetException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            output.append("Exception: "+e.getMessage()+"\n");
        }
        return classInst;
    }

    /**
     * Method invokeMethods retrieves and invokes all the methods in the test-class, meaning running the actual tests
     * @param classInst Object of the instantiated class
     * @param reqClass Class of the required class
     * @param output JTextArea used to append both test-results and possible exceptions.
     */
    private void invokeMethods(Object classInst, Class<?> reqClass, JTextArea output) {
        //Make sure the testclass implements the TestClass interface before running tests
        if (classInst instanceof TestClass) {
            Method[] methodList = reqClass.getMethods();
            for (Method method : methodList) {
                if (method.getName().startsWith("test") && method.getParameterCount() == 0) {
                    runMethod(reqClass, "setUp", classInst, output);

                    runMethodWPrint(reqClass, method.getName(), classInst, output);

                    runMethod(reqClass, "tearDown", classInst, output);
                }
            }
        } else {
            output.append("Tester does not implement TestClass");
        }
    }

    /**
     * Method getResults retrieves the final results (number of succeeded and failed tests) and appends these
     * to the GUI output.
     * @param output JTextArea used for the output
     */
    private void getResults(JTextArea output) {
        output.append("\n");
        output.append(nrOfSuc+" Tests succeeded\n");
        output.append(nrOfFail+" Tests failed\n");
        output.append(nrOfExc+" Tests failed because of an exception\n\n");
    }

    /**
     * Method runMethodWPrint used to run the test-methods that require some kind of printed results after being
     * run. Appends Successful text if test is succeeds and Failed text if test fails. If any exceptions are thrown
     * they too are appended to the GUI textarea.
     * @param req Class object of the required class
     * @param methodName Name of the method to be run
     * @param classInst Instantiated Object of the required class
     * @param output JTextArea used to append results or exceptions
     */
    private void runMethodWPrint(Class<?> req, String methodName, Object classInst, JTextArea output) {
        try {
            boolean success;
            String suc = "null";
            if (req.getMethod(methodName).getReturnType().equals(Boolean.TYPE)) {
                success = (boolean) req.getMethod(methodName).invoke(classInst);
                suc = (success ? "Successful" : "Failed");
                if (success)
                    this.nrOfSuc++;
                else
                    this.nrOfFail++;
            }
            output.append(methodName+": "+ suc +"\n");
        } catch (NoSuchMethodException e) {
            output.append(methodName+" method does not exist "+e+"\n");
            this.nrOfExc++;
        } catch (InvocationTargetException e) {
            output.append(methodName+": "+"Failed by exception "+e+"\n");
            this.nrOfExc++;
        } catch (IllegalAccessException e) {
            output.append(methodName+": "+"Could not access function "+e+"\n");
            this.nrOfExc++;
        }
    }

    /**
     * Method runMethodPrint used to run the non-test-methods that do not provide any results to be printed.
     * If any exceptions are thrown they are appended to the GUI textarea.
     * @param req Class object of the required class
     * @param methodName Name of the method to be run
     * @param classInst Instantiated Object of the required class
     * @param output JTextArea used to append exceptions
     */
    private void runMethod(Class<?> req, String methodName, Object classInst, JTextArea output) {
        try {
            req.getMethod(methodName).invoke(classInst);
        } catch (NoSuchMethodException e) {
            output.append(methodName+" method does not exist \n");
        } catch (InvocationTargetException e) {
            output.append(methodName+": "+"Failed by exception \n");
        } catch (IllegalAccessException e) {
            output.append(methodName+": "+"Could not access function \n");
        }
    }
}
