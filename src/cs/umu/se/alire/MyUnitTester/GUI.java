package cs.umu.se.alire.MyUnitTester;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private final JFrame frame;

    public GUI() {
        frame = new JFrame("Intrusion Detector");

        JTextArea input = getInputTextArea();
        JTextArea print = getOutputTextArea();

        JScrollPane scroll = getScrollPane(print);

        JButton run = new JButton("Run");
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(b->{print.setText("");});
        run.addActionListener(b -> new RunTest(input.getText(), print));

        JPanel inputPanel = getInputPanel(input, run);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(clearButton, BorderLayout.SOUTH);
        configureFrame();
    }

    private JPanel getInputPanel(JTextArea input, JButton run) {
        JPanel inputPanel = new JPanel(new GridLayout(0,2));
        JScrollPane scrollPane = new JScrollPane(input);
        inputPanel.add(scrollPane);
        inputPanel.add(run);
        return inputPanel;
    }

    private JScrollPane getScrollPane(JTextArea print){
        JScrollPane scroll=new JScrollPane(print);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private void configureFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setVisible(true);
    }
    private JTextArea getOutputTextArea() {
        JTextArea text=new JTextArea();
        text.setLineWrap(true);
        text.setEditable(false);
        return text;
    }

    private JTextArea getInputTextArea() {
        JTextArea text=new JTextArea();
        text.setLineWrap(false);
        text.setEditable(true);
        return text;
    }
}
