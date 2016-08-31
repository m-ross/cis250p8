/*	Program name:	Lab 08 Number Base Converter
	Programmer:		Marcus Ross
	Date Due:		23 April, 2014
	Description:	This program is lab 5 altered to utilize recursion instead of a stack.	*/

package lab08;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		Frame frame = new Frame("Number Base Converter");

		LoadFrame(frame);
	}

	public static void LoadFrame(Frame frame) {
		// initialize components
		SpringLayout layout = new SpringLayout();
		Panel panel = new Panel(layout);
		Label numberL = new Label("Decimal number");
		Label baseL = new Label("Convert to base");
		Label resultL = new Label("Result");
		TextField numberF = new TextField("from 0 to 2147483647");
		TextField baseF = new TextField("from 2 to 32");
		TextField resultF = new TextField();

		// component methods
		resultF.setFocusable(false);
		// to make TextField resultF update
		numberF.addTextListener(new TextListen(numberF, baseF, resultF));
		baseF.addTextListener(new TextListen(numberF, baseF, resultF));
		// to make initial text disappear
		numberF.addFocusListener(new FocusListen(numberF));
		baseF.addFocusListener(new FocusListen(baseF));

		// constraints
		layout.putConstraint("VerticalCenter", baseL, 0, "VerticalCenter", panel);
		layout.putConstraint("VerticalCenter", baseF, 0, "VerticalCenter", panel);
		layout.putConstraint("West", baseL, 10, "West", panel);
		layout.putConstraint("West", baseF, 10, "East", baseL);
		layout.putConstraint("East", baseF, -10, "East", panel);
		layout.putConstraint("VerticalCenter", numberL, -35, "VerticalCenter", panel);
		layout.putConstraint("VerticalCenter", numberF, -35, "VerticalCenter", panel);
		layout.putConstraint("West", numberL, 10, "West", panel);
		layout.putConstraint("West", numberF, 10, "East", numberL);
		layout.putConstraint("East", numberF, -10, "East", panel);
		layout.putConstraint("VerticalCenter", resultL, 35, "VerticalCenter", panel);
		layout.putConstraint("VerticalCenter", resultF, 35, "VerticalCenter", panel);
		layout.putConstraint("West", resultL, 10, "West", panel);
		layout.putConstraint("West", resultF, 10, "East", resultL);
		layout.putConstraint("East", resultF, -10, "East", panel);

		// add components
		panel.add(numberL);	panel.add(numberF);		panel.add(baseL);
		panel.add(baseF);	panel.add(resultL);		panel.add(resultF);
		frame.add(panel);

		// frame methods
		frame.setResizable(false);
		frame.addWindowListener(new WinListen(frame));
		frame.setSize(315, 150);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void UpdateResult(TextField numberF, TextField baseF, TextField resultF) {
		String numberS, baseS;
		int number, base;
		String result;

		numberS = numberF.getText(); // get Strings from TextFields
		baseS = baseF.getText();

		if(numberS.isEmpty() || baseS.isEmpty()) {
			resultF.setText(""); // blank result if nothing typed in
			return;
		}

		try {
			number = Integer.parseInt(numberF.getText());
			base = Integer.parseInt(baseF.getText());

			if(base < 2 || base > 32 || number < 0) {
				resultF.setText(""); // blank result if values not within range
				return;
			}

			result = ConvertBase(number, base); // set result string to value returned by function
			resultF.setText(result); // update TextField using result string
		} catch(NumberFormatException e) {
			resultF.setText(""); // blank result if values not valid integers
		}
	}

	public static String ConvertBase(int number, int base) {
		char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'}; // digits up to V for base 32 system
		StringBuffer sBuffer = new StringBuffer(); // save some memory compared to a String?
		String result;

		result = ConvertBaseRecur(number, base, digit, sBuffer);

		return result;
	}

	public static String ConvertBaseRecur(int number, int base, char[] digit, StringBuffer result) {
		int remainder;

		if(number != 0) {
			remainder = number % base;
			number /= base;
			ConvertBaseRecur(number, base, digit, result);
			result.append(digit[remainder]);
		}

		return result.toString();
	}
}

class FocusListen implements FocusListener {
	private TextField field;
	private boolean first;
	public FocusListen(TextField field) {
		first = true;
		this.field = field;
	}
	public void focusGained(FocusEvent e) {
		if(first) {
			field.setText("");
			first = false; // blank the TextField only on first focus gain
		}
	}
	public void focusLost(FocusEvent e) { }
}

class TextListen implements TextListener {
	private TextField numberF, baseF, resultF;

	public TextListen(TextField numberF, TextField baseF, TextField resultF) {
		this.numberF = numberF;
		this.baseF = baseF;
		this.resultF = resultF;
	}

	public void textValueChanged(TextEvent e) {
		Main.UpdateResult(numberF, baseF, resultF); // update resultF each time text changes
	}
}

class WinListen implements WindowListener {
	private Frame frame;

	public WinListen(Frame frame) {
		this.frame = frame;
	}

	public void windowClosing(WindowEvent e) {
		frame.setVisible(false);
		frame.dispose();
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
}
