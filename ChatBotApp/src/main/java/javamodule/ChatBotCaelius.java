package javamodule;

import java.io.File;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ChatBotCaelius extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
    private Chat chatSession;
    private JTextArea chatArea;
    private PlaceholderTextField inputField;

    //GUI
    public ChatBotCaelius(Chat chatSession) {
        this.chatSession = chatSession;

        setTitle("ChatBot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 400);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new PlaceholderTextField("Enter your text Here");
        inputField.addActionListener(this);
        add(inputField, BorderLayout.SOUTH);
        
    }
    class PlaceholderTextField extends JTextField {
        private static final long serialVersionUID = 1L;

        private String placeholder;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 30);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (getText().isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.GRAY);
                g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                g2.dispose();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText();
        displayUserInput(input);
        String response = chatSession.multisentenceRespond(input);
        displayBotResponse(response);
        inputField.setText("");
    }

    public void displayUserInput(String input) {
        chatArea.append("User: " + input + "\n");
    }

    public void displayBotResponse(String response) {
        chatArea.append("Bot: " + response + "\n");
    }

	private static final boolean TRACE_MODE = false;
	static String botName = "super";

	public static void main(String[] args) {
	    try {
	        String resourcesPath = getResourcesPath();
	        MagicBooleans.trace_mode = TRACE_MODE;
	        final Bot bot = new Bot(botName, resourcesPath);
	        final Chat chatSession = new Chat(bot);
	        bot.brain.nodeStats();

	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                ChatBotCaelius gui = new ChatBotCaelius(chatSession);
	                gui.setVisible(true);
	            }
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		//System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}

}