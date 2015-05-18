import javax.swing.*;

import org.sikuli.script.FindFailed;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ParserFrame extends JFrame {

	public ParserFrame() {
		super("Sikuli script executer");

		// Location and size of frame

		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		center.x -= DEFAULT_WIDTH / 2;
		center.y -= DEFAULT_HEIGHT / 2;
		setLocation(center);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		
		
		
		execute = new JButton("Execute script");
		terminate = new JButton("Terninate");
		
		initTerminateButtonListener();
		initExecuteButtonListener();

		setLayout(new BorderLayout());

		initPanel();
		initMenu();

	}

	private void initTerminateButtonListener() {
		terminate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sikuliThread != null) {
					sikuliThread.stop();
					JOptionPane.showMessageDialog(null, "Execution terminated");
					
					sikuliThread = null;
				}
				
			}
		});
	}

	private void initExecuteButtonListener() {
		execute.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(folderPath == null)
					return;
				
							
				String fileName = (String) scriptList.getSelectedValue();
				if(fileName == null)
					return;
				
				executeScript(folderPath + "\\" + fileName);
				
			}
		});
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		openScript = new JMenuItem("Open...");
		exit = new JMenuItem("Exit");
		menu.add(openScript);
		menu.add(exit);
		menuBar.add(menu);
		setJMenuBar(menuBar);

		initExitButtonListener();

		initOpenScriptButtonListener();
	}

	private void initOpenScriptButtonListener() {
		openScript.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JFileChooser fileopen = new JFileChooser(
						"E:\\New folder\\Chilli\\WEB\\Resources\\Scripts");
				fileopen.setMultiSelectionEnabled(true);
				int ret = fileopen.showDialog(null, "Open File...");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File files[] = fileopen.getSelectedFiles();
					if (files.length > 0)
						folderPath = files[0].getParent();
					DefaultListModel<String> listModel = new DefaultListModel<String>();
					for (File f : files) {
						listModel.addElement(f.getName());

					}

					scriptList.setModel(listModel);
				}
			}
		});
	}

	private void initExitButtonListener() {
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
	}

	private void initPanel() {
		scriptList = new JList<String>();
		JScrollPane scrollPane = new JScrollPane(scriptList);
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(new JPanel());
		panel.add(scrollPane);
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(execute);
		buttonPanel.add(terminate);
		panel.add(buttonPanel);

		add(panel, BorderLayout.CENTER);
	}

	private void executeScript(String filename) {

		sikuliThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Executing " + filename + " ...");
				try {
					SikuliParser.execute(filename);
				} catch (FindFailed e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "No pattern found");
					e.printStackTrace();
				}
			}
		});
		
		sikuliThread.start();
		
	}

	private Thread sikuliThread;
	
	private String folderPath;

	private JMenu menu;
	private JMenuItem openScript;
	private JMenuItem exit;
	private JPanel panel;
	private JButton execute;
	private JButton terminate;
	private JList scriptList;

	public static final int DEFAULT_HEIGHT = 230;
	public static final int DEFAULT_WIDTH = 600;

}
