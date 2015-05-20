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

public class SikuliParser {

	private  static String catalogPath;
	public static Document openScript(String filename) throws DocumentException {
		File inputFile = new File(filename);
		SAXReader reader = new SAXReader();

		return reader.read(inputFile);
	}

	public static Element getScriptContent(Document document) {
		Element rootElement = document.getRootElement();
		catalogPath = rootElement.attributeValue("CatalogPath");
		List<Element> actions = rootElement.elements();
		Element scriptContent = actions.get(0);

		return scriptContent;
	}

	public static ActionUnit createAction(Element e) {
		String type = getAttributeValue(e, "type");
		String target = catalogPath + "\\" + getAttributeValue(e, "target");
		String targetOffset = getAttributeValue(e, "targetOffset");
		String inputText = getAttributeValue(e, "inputText");
		String targetSimilarity = getAttributeValue(e, "targetSimilarity");
		String timeout = getAttributeValue(e, "timeout");
		Double similarity = null;
		if (targetSimilarity != null)
			similarity = new Double(targetSimilarity);

		return new ActionUnit(type, targetOffset, target, inputText, similarity, timeout);

	}

	public static Loop createLoop(Element e) {
		String type = getAttributeValue(e, "type");
		String countNumber = getAttributeValue(e, "counts");
		String targetCondition = catalogPath + "\\" + getAttributeValue(e, "condition");

		return new Loop(countNumber, type, targetCondition);
	}

	public static If createIf(Element e) {
		String targetCondition = catalogPath + "\\" + getAttributeValue(e, "condition");

		return new If(targetCondition);
	}

	private static String getAttributeValue(Element e, String attributeName) {
		String value = null;
		org.dom4j.Attribute attribute = e.attribute(attributeName);
		if (attribute != null)
			value = attribute.getValue();
		return value;
	}

	public static void executeScript(Region region, Element e)
			throws FindFailed {
		List<Element> childElements = e.elements();
		switch (e.getName()) {
		case "actions":

			for (Element childElement : childElements) {
				executeScript(region, childElement);
			}
		case "action":
			ActionUnit act = createAction(e);
			act.execute(region);
			break;
		case "loop":
			Loop loop = createLoop(e);

			switch (loop.getType()) {
			case "for":
				for (int i = 0; i < loop.getCountNumber(); ++i) {
					for (Element childElement : childElements) {
						executeScript(region, childElement);
					}
				}
				break;
			case "while":
				while(loop.executeCondition(region)) {
					for (Element childElement : childElements)
						executeScript(region, childElement);
				}
				break;
			default:
				break;
			}
			break;
		case "if":
			If ifContainer = createIf(e);
			boolean b = ifContainer.executeCondition(region);
			if (b) {
				for (Element childElement : childElements) {
					if (!"else".equals(childElement.getName()))
						executeScript(region, childElement);
				}
			} else {
				for (Element childElement : childElements) {
					if ("else".equals(childElement.getName()))
						executeScript(region, childElement);
				}
			}
			break;
		case "else":
			for (Element childElement : childElements) {
				executeScript(region, childElement);
			}
			break;
		default:
			break;
		}

	}

	public static void execute(String filename) throws FindFailed {
		
		try {

			Document document = openScript(filename);
			Element scriptContent = getScriptContent(document);
			Region region = new Screen();
			executeScript(region, scriptContent);

		} catch (DocumentException e) {
			e.printStackTrace();
		} 
	}

}
