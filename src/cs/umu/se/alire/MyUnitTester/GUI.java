package cs.umu.se.alire.MyUnitTester;

import javax.swing.*;
import java.awt.*;

/**
 * Class GUI builds and renders the program GUI, containing an input textfield,
 * two buttons and a scrollable output textfield.
 */
public class GUI {

    private final JFrame frame;

    public GUI() {
        frame = new JFrame("My Unit Tester");

        JTextArea input = getInputTextArea();
        JTextArea print = getOutputTextArea();

        JScrollPane scroll = getScrollPane(print);

        //Initialize RunTest class
        RunTest tester = new RunTest();

        //Create buttons and listeners for each respective button
        JButton run = new JButton("Run");
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(b->{print.setText("");});
        //When the run button is clicked, run the test based on the given input string
        run.addActionListener(b -> tester.run(input.getText(), print));

        JPanel inputPanel = getInputPanel(input, run);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(clearButton, BorderLayout.SOUTH);
        configureFrame();
    }

    /**
     * Method getInputPanel creates a JPanel containing the input-field and the button
     * to start running tests
     *
     * @param input JTextArea for the input-field
     * @param run JButton to run the input class
     * @return JPanel to be used in GUI
     */
    private JPanel getInputPanel(JTextArea input, JButton run) {
        JPanel inputPanel = new JPanel(new GridLayout(0,2));
        JScrollPane scrollPane = new JScrollPane(input);
        inputPanel.add(scrollPane);
        inputPanel.add(run);
        return inputPanel;
    }

    /**
     * Method getScrollPane creates a JScrollPane containing the output JTextArea with test results
     *
     * @param print JTextArea containing the printed output
     * @return JScrollPane to be used in GUI
     */
    private JScrollPane getScrollPane(JTextArea print){
        JScrollPane scroll=new JScrollPane(print);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    /**
     * Method configureFrame configures the frame size and other operations of the JFrame used for the GUI
     */
    private void configureFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setVisible(true);
    }

    /**
     * Method getOutputTextArea creates a JTextArea for the non-editable output field in the GUI
     * @return JTextArea
     */
    private JTextArea getOutputTextArea() {
        JTextArea text=new JTextArea();
        text.setLineWrap(true);
        text.setEditable(false);
        return text;
    }

    /**
     * Method getInputTextArea creates a JTextArea for the editable input field in the GUI
     * @return JTextArea
     */
    private JTextArea getInputTextArea() {
        JTextArea text=new JTextArea();
        text.setLineWrap(false);
        text.setEditable(true);
        return text;
    }
}
