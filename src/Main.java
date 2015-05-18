import java.io.File;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptContext;
import javax.swing.JFrame;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.python.antlr.PythonParser.return_stmt_return;
import org.sikuli.script.*;

import Entities.*;

public class Main {

	public static void main(String[] args) throws FindFailed {
		ParserFrame frame = new ParserFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

}
