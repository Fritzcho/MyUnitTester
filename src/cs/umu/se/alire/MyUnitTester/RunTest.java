package cs.umu.se.alire.MyUnitTester;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTest {
    public RunTest(String input, JTextArea text) {
        SwingWorker sw1 = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Class<?> reqClass = requireClass(input, text);
                Object instClass = instantiateClass(reqClass, text);
                invokeMethods(instClass, reqClass, text);
                return null;
            }
        };
    }

    public Class<?> requireClass(String input, JTextArea output) {
        Class<?> reqClass = null;
        try {
            reqClass = Class.forName(input);
        } catch (ClassNotFoundException e) {
            output.append("Exception: Class not found: "+e.getMessage()+"\n");
        }
        return reqClass;
    }

    public Object instantiateClass(Class<?> reqClass, JTextArea output) {
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

    public void invokeMethods(Object classInst, Class<?> reqClass, JTextArea output) {
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
            throw new RuntimeException("Tester does not implement TestClass");
        }
    }

    public void runMethodWPrint(Class<?> req, String methodName, Object classInst, JTextArea output) {
        try {
            boolean success;
            String suc = "null";
            if (req.getMethod(methodName).invoke(classInst) instanceof Boolean) {
                success = (boolean) req.getMethod(methodName).invoke(classInst);
                suc = (success ? "Successful" : "Failed");
            }
            output.append(methodName+": "+ suc +"\n");
        } catch (NoSuchMethodException e) {
            //System.err.println(methodName+" method does not exist "+e);
            output.append(methodName+" method does not exist "+e+"\n");
        } catch (InvocationTargetException e) {
            //System.err.println("Failed by exception "+e);
        } catch (IllegalAccessException e) {
            //System.err.println(methodName+": "+"Could not access function "+e);
            output.append(methodName+": "+"Could not access function "+e+"\n");
        }
    }

    public void runMethod(Class<?> req, String methodName, Object classInst, JTextArea output) {
        try {
            req.getMethod(methodName).invoke(classInst);
        } catch (NoSuchMethodException e) {
            //System.err.println(methodName+" method does not exist "+e);
            output.append(methodName+" method does not exist "+e+"\n");
        } catch (InvocationTargetException e) {
            //System.err.println("Failed by exception "+e);
        } catch (IllegalAccessException e) {
            //System.err.println(methodName+": "+"Could not access function "+e);
            output.append(methodName+": "+"Could not access function "+e+"\n");
        }
    }
}
