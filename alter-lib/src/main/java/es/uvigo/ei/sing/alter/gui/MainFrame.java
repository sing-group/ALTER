/*
 *  This file is part of ALTER.
 *
 *  ALTER is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ALTER is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ALTER.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.uvigo.ei.sing.alter.gui;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;

import es.uvigo.ei.sing.alter.converter.*;
import es.uvigo.ei.sing.alter.parser.ParseException;
import es.uvigo.ei.sing.alter.util.AlterProperties;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the main form to execute ALTER as a GUI application.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */
public class MainFrame extends javax.swing.JFrame
{
    /**
     * Logger to register information messages.
     */
    private Logger logger;

    /**
     * Input file.
     */
    private File inputFile = null;

    /**
     * Output file.
     */
    private File outputFile = null;

    /**
     * Current input folder.
     */
    private File inputCurDir = null;

    /**
     * Current output folder.
     */
    private File outputCurDir = null;

    /**
     * Java Action to display the application information.
     */
    private AboutAction aboutAction;

    /**
     * Java Action to display the application help.
     */
    private HelpAction helpAction;

    /**
     * Java Action to load a MSA from a file.
     */
    private LoadMsaAction loadMsaAction;

    /**
     * Java Action to save the input MSA in the selected input file.
     */
    private SaveInputAction saveInputAction;

    /**
     * Java Action to save the input MSA in a file after selecting it.
     */
    private SaveInputAsAction saveInputAsAction;

    /**
     * Java Action to save the output MSA in the selected output file.
     */
    private SaveOutputAction saveOutputAction;

    /**
     * Java Action to save the output MSA in a file after selecting it.
     */
    private SaveOutputAsAction saveOutputAsAction;

    /**
     * Java Action to save both MSAs in their respective files.
     */
    private SaveAllAction saveAllAction;

    /**
     * Java Action to convert the input MSA.
     */
    private ConvertAction convertAction;

    /**
     * Java Action to exit the application asking for confirmation.
     */
    private ExitAction exitAction;

    /**
     * Panel with the conversion options.
     */
    ConvertPanel convertPanel;

    /**
     * Class constructor.
     */
    public MainFrame()
    {
        //Try changing Look-And-Feel to Nimbus (do nothing in case is not installed)
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException e)
        {
            // handle exception
        }
        catch (ClassNotFoundException e)
        {
            // handle exception
        }
        catch (InstantiationException e)
        {
            // handle exception
        }
        catch (IllegalAccessException e)
        {
            // handle exception
        }

        //Initialize the convert panel
        convertPanel = new ConvertPanel();
        //Initialize Java Actions
        aboutAction = new AboutAction("About", null, "Information about ALTER", KeyEvent.VK_A);
        helpAction = new HelpAction("Help Contents", null, "", KeyEvent.VK_C);
        loadMsaAction = new LoadMsaAction("Load...", null, "Load a new input MSA", KeyEvent.VK_L);
        saveInputAction = new SaveInputAction("Save", null, "Save input MSA", KeyEvent.VK_S);
        saveInputAsAction = new SaveInputAsAction("Save As...", null, "Save input MSA as...", KeyEvent.VK_I);
        saveOutputAction = new SaveOutputAction("Save", null, "Save output MSA", KeyEvent.VK_V);
        saveOutputAsAction = new SaveOutputAsAction("Save As...", null, "Save output MSA as...", KeyEvent.VK_O);
        saveAllAction = new SaveAllAction("Save All", null, "Save both input and output MSAs", KeyEvent.VK_A);
        convertAction = new ConvertAction("Convert...", null, "Convert MSA", KeyEvent.VK_C);
        exitAction = new ExitAction("Exit", null, "Exit ALTER", KeyEvent.VK_E);

        //Initialize components
        initComponents();

        //Assing Java Actions to components
        about.setAction(aboutAction);

        helpContents.setAction(helpAction);

        loadMsaButton.setAction(loadMsaAction);
        loadMsaMenu.setAction(loadMsaAction);
        loadMsaMenu.setText("Load Input MSA...");
        loadMsaMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));

        saveInputMenu.setAction(saveInputAction);
        saveInputMenu.setText("Save Input MSA");
        saveInputMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        saveInputButton.setAction(saveInputAction);

        saveInputAsMenu.setAction(saveInputAsAction);
        saveInputAsMenu.setText("Save Input MSA As...");
        saveInputAsButton.setAction(saveInputAsAction);

        saveOutputMenu.setAction(saveOutputAction);
        saveOutputMenu.setText("Save Output MSA");
        saveOutputMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        saveOutputButton.setAction(saveOutputAction);

        saveOutputAsMenu.setAction(saveOutputAsAction);
        saveOutputAsMenu.setText("Save Output MSA As...");
        saveOutputAsButton.setAction(saveOutputAsAction);

        saveAllMenu.setAction(saveAllAction);
        saveAllMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

        convertMenu.setAction(convertAction);
        convertMenu.setText("Convert MSA...");
        convertMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        convertButton.setAction(convertAction);

        exitMenu.setAction(exitAction);

        //Maximize window
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds ());
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        //Initialize logger
        logger = Logger.getLogger("alter" + System.currentTimeMillis());
        //Add logger handler
        logger.addHandler(new GUILogHandler(log));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputPanel = new javax.swing.JPanel();
        inputScroll = new javax.swing.JScrollPane();
        input = new javax.swing.JTextArea();
        loadMsaButton = new javax.swing.JButton();
        saveInputButton = new javax.swing.JButton();
        convertButton = new javax.swing.JButton();
        inputPosition = new javax.swing.JLabel();
        saveInputAsButton = new javax.swing.JButton();
        outputPanel = new javax.swing.JPanel();
        saveOutputButton = new javax.swing.JButton();
        outputScroll = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        outputPosition = new javax.swing.JLabel();
        saveOutputAsButton = new javax.swing.JButton();
        logPanel = new javax.swing.JPanel();
        logScroll = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        menu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        loadMsaMenu = new javax.swing.JMenuItem();
        separator1 = new javax.swing.JSeparator();
        convertMenu = new javax.swing.JMenuItem();
        separator2 = new javax.swing.JSeparator();
        saveInputMenu = new javax.swing.JMenuItem();
        saveInputAsMenu = new javax.swing.JMenuItem();
        saveOutputMenu = new javax.swing.JMenuItem();
        saveOutputAsMenu = new javax.swing.JMenuItem();
        saveAllMenu = new javax.swing.JMenuItem();
        separator3 = new javax.swing.JSeparator();
        exitMenu = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpContents = new javax.swing.JMenuItem();
        about = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ALTER (ALignment Transformation EnviRonment) v"+ AlterProperties.getInstance().getProperty
				("project.version"));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setName("mainFrame"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        inputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));

        input.setColumns(2147483647);
        input.setFont(new java.awt.Font("Lucida Console", 0, 11));
        input.setRows(5);
        input.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputCaretUpdate(evt);
            }
        });
        inputScroll.setViewportView(input);

        loadMsaButton.setText("Load...");

        saveInputButton.setText("Save");

        convertButton.setText("Convert...");

        inputPosition.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        inputPosition.setText("Ln: 1 Col: 1");

        saveInputAsButton.setText("Save As...");

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(loadMsaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveInputButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveInputAsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(convertButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputPosition, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
                .addComponent(inputScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadMsaButton)
                    .addComponent(saveInputButton)
                    .addComponent(inputPosition)
                    .addComponent(saveInputAsButton)
                    .addComponent(convertButton)))
        );

        outputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

        saveOutputButton.setText("Save");

        output.setColumns(2147483647);
        output.setFont(new java.awt.Font("Lucida Console", 0, 11));
        output.setRows(5);
        output.setEnabled(false);
        output.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                outputCaretUpdate(evt);
            }
        });
        outputScroll.setViewportView(output);

        outputPosition.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        outputPosition.setText("Ln: 0 Col:0");
        outputPosition.setEnabled(false);

        saveOutputAsButton.setText("Save As...");

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout.setHorizontalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addComponent(saveOutputButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveOutputAsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputPosition, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                    .addComponent(outputScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
                .addContainerGap())
        );
        outputPanelLayout.setVerticalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outputPanelLayout.createSequentialGroup()
                .addComponent(outputScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveOutputButton)
                        .addComponent(saveOutputAsButton))
                    .addComponent(outputPosition)))
        );

        logPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Log"));

        log.setColumns(2147483647);
        log.setEditable(false);
        log.setFont(new java.awt.Font("Lucida Console", 0, 11));
        log.setRows(5);
        logScroll.setViewportView(log);

        javax.swing.GroupLayout logPanelLayout = new javax.swing.GroupLayout(logPanel);
        logPanel.setLayout(logPanelLayout);
        logPanelLayout.setHorizontalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
        );
        logPanelLayout.setVerticalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );

        fileMenu.setText("File");

        loadMsaMenu.setText("Load Input MSA...");
        fileMenu.add(loadMsaMenu);
        fileMenu.add(separator1);

        convertMenu.setText("Convert");
        fileMenu.add(convertMenu);
        fileMenu.add(separator2);

        saveInputMenu.setText("Save Input MSA");
        fileMenu.add(saveInputMenu);

        saveInputAsMenu.setText("Save Input MSA As...");
        fileMenu.add(saveInputAsMenu);

        saveOutputMenu.setText("Save Output MSA");
        fileMenu.add(saveOutputMenu);

        saveOutputAsMenu.setText("Save Output MSA As...");
        fileMenu.add(saveOutputAsMenu);

        saveAllMenu.setText("Save Both");
        fileMenu.add(saveAllMenu);
        fileMenu.add(separator3);

        exitMenu.setText("Exit");
        fileMenu.add(exitMenu);

        menu.add(fileMenu);

        helpMenu.setText("Help");

        helpContents.setText("Help Contents");
        helpMenu.add(helpContents);

        about.setAction(new AboutAction("About...", null, "Information about Corpus Administrator", null));
        about.setText("About");
        helpMenu.add(about);

        menu.add(helpMenu);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(logPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Calls exitAction.actionPerformed(null) to ask for confirmation before exiting the application.
     * @param evt Event generated when closing the window.
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        exitAction.actionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    /**
     * Updates line and column numbers when the input caret position changes.
     * @param evt Event generated when the caret position changes.
     */
    private void inputCaretUpdate(javax.swing.event.CaretEvent evt)//GEN-FIRST:event_inputCaretUpdate
    {//GEN-HEADEREND:event_inputCaretUpdate
        try
        {
            int caretPos = input.getCaretPosition();
            int line = input.getLineOfOffset(caretPos);
            int column = caretPos - input.getLineStartOffset(line);
            line += 1;
            column += 1;
            inputPosition.setText("Ln: " + line + " Col: " + column);
        }
        catch (BadLocationException ex)
        {}
    }//GEN-LAST:event_inputCaretUpdate

    /**
     * Updates line and column numbers when the output caret position changes.
     * @param evt Event generated when the caret position changes.
     */
    private void outputCaretUpdate(javax.swing.event.CaretEvent evt)//GEN-FIRST:event_outputCaretUpdate
    {//GEN-HEADEREND:event_outputCaretUpdate
        try
        {
            int caretPos = output.getCaretPosition();
            int line = output.getLineOfOffset(caretPos);
            int column = caretPos - output.getLineStartOffset(line);
            line += 1;
            column += 1;
            outputPosition.setText("Ln: " + line + " Col: " + column);
        }
        catch (BadLocationException ex)
        {}
    }//GEN-LAST:event_outputCaretUpdate

    /**
     * Main method that creates and shows the form
     * @param args Command line arguments
     */
    public static void main(String args[])
    {

        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about;
    private javax.swing.JButton convertButton;
    private javax.swing.JMenuItem convertMenu;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem helpContents;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JTextArea input;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JLabel inputPosition;
    private javax.swing.JScrollPane inputScroll;
    private javax.swing.JButton loadMsaButton;
    private javax.swing.JMenuItem loadMsaMenu;
    private javax.swing.JTextArea log;
    private javax.swing.JPanel logPanel;
    private javax.swing.JScrollPane logScroll;
    private javax.swing.JMenuBar menu;
    private javax.swing.JTextArea output;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JLabel outputPosition;
    private javax.swing.JScrollPane outputScroll;
    private javax.swing.JMenuItem saveAllMenu;
    private javax.swing.JButton saveInputAsButton;
    private javax.swing.JMenuItem saveInputAsMenu;
    private javax.swing.JButton saveInputButton;
    private javax.swing.JMenuItem saveInputMenu;
    private javax.swing.JButton saveOutputAsButton;
    private javax.swing.JMenuItem saveOutputAsMenu;
    private javax.swing.JButton saveOutputButton;
    private javax.swing.JMenuItem saveOutputMenu;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator2;
    private javax.swing.JSeparator separator3;
    // End of variables declaration//GEN-END:variables

    /**
     * Java Action to show the application information.
     */
    private class AboutAction extends AbstractAction
    {

        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public AboutAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**Implements action.
         *@param e Event that triggers the action.
         */
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(new JFrame(),
                    "ALTER (ALlignment Transformation EnviRonment)\n" +
                    "Developed by Daniel Gomez Blanco\n\n" +
                    "Email: nanodgb@gmail.com\n",
                    "About ALTER",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Java Action to show the application help.
     */
    private class HelpAction extends AbstractAction
    {

        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public HelpAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**Implements action.
         *@param e Event that triggers the action.
         */
        public void actionPerformed(ActionEvent e)
        {
            JFrame help = new JFrame("ALTER Help");
            help.setLocationByPlatform(true);
            HelpPanel helpPanel = new HelpPanel();
            help.add(helpPanel);
            help.pack();
            help.setVisible(true);
            helpPanel.scrollUp();
        }
    }

    /**
     * Java Action to load a MSA from a file after selecting it.
     */
    private class LoadMsaAction extends AbstractAction
    {

        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public LoadMsaAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**Implements action.
         *@param e Event that triggers the action.
         */
        public void actionPerformed(ActionEvent e)
        {
            //Create file chooser
            JFileChooser chooser = new JFileChooser();

            //Only files are to be chosen
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogTitle("Load Input MSA");
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            if (inputCurDir != null)
                chooser.setCurrentDirectory(inputCurDir);
            //Display chooser
            int returnVal = chooser.showOpenDialog(new javax.swing.JFrame());

            //If the user accepts
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                //Get input file
                inputFile = chooser.getSelectedFile();
                //Get input folder
                inputCurDir = inputFile.getParentFile();
                //Copy file's contents to a string
                StringBuffer text = new StringBuffer();
                try
                {
                    BufferedReader br = new BufferedReader(new FileReader(inputFile));
                    String s;
                    while ((s = br.readLine()) != null)
                        text.append(s + "\r\n");
                    br.close();
                }
                catch (FileNotFoundException ex)
                {
                    logger.log(Level.SEVERE,"Input MSA file not found.");
                    return;
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE, "Failure reading input MSA file:\n" + ex.getMessage());
                    return;
                }
                
                //Update interface
                input.setText(text.toString());
                
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        ((TitledBorder) inputPanel.getBorder()).setTitle("Input - " + inputFile.getName());
                        inputPanel.repaint();
                        inputScroll.getVerticalScrollBar().setValue(0);
                        inputScroll.getHorizontalScrollBar().setValue(0);
                    }
                });
                logger.log(Level.INFO, "MSA loaded from " + inputFile.getAbsolutePath() + ".");
            }
        }
    }

    /**
     * Java Action to save the input MSA into the selected file.
     */
    private class SaveInputAction extends AbstractAction
    {
        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public SaveInputAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**Implements action.
         *@param e Event that triggers the action.
         */
        public void actionPerformed(ActionEvent e)
        {
            //If no file had been selected call saveInputAsAction
            if (inputFile == null)
                saveInputAsAction.actionPerformed(e);
            else
            {
                //Save text to file
                try
                {
                    FileWriter fw = new FileWriter(inputFile);
                    fw.write(input.getText());
                    fw.close();
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE, "Failure saving input MSA file:\n" + ex.getMessage());
                    return;
                }
                logger.log(Level.INFO, "Input MSA saved to " + inputFile.getAbsolutePath());
            }
        }
    }

    /**
     * Java Action to save input MSA in a different file.
     */
    private class SaveInputAsAction extends AbstractAction
    {

        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public SaveInputAsAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**Implements action.
         *@param e Event that triggers the action.
         */
        public void actionPerformed(ActionEvent e)
        {        
            //Show file chooser
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogTitle("Save Input MSA As...");
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            int returnVal = chooser.showOpenDialog(new javax.swing.JFrame());

            //If user accepts
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                //Save file
                inputFile = chooser.getSelectedFile();
                if (!inputFile.exists())
                    inputFile = new File(inputFile.getPath());
                inputCurDir = inputFile.getParentFile();

                try
                {
                    FileWriter fw = new FileWriter(inputFile);
                    fw.write(input.getText());
                    fw.close();
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE, "Failure saving input MSA file:\n" + ex.getMessage());
                    return;
                }

                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        ((TitledBorder) inputPanel.getBorder()).setTitle("Input - " + inputFile.getName());
                        inputPanel.repaint();
                    }
                });
                logger.log(Level.INFO, "Input MSA saved to " + inputFile.getAbsolutePath());
            }
        }
    }

    /**
     * Java Action to save the output MSA to the selected file.
     */
    private class SaveOutputAction extends AbstractAction
    {

        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public SaveOutputAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Incializar la accion
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            this.setEnabled(false);
        }

        /**Implements action
         *@param e Event that triggers the action
         */
        public void actionPerformed(ActionEvent e)
        {
            //If no file had been selected call saveOutputAsAction
            if (outputFile == null)
                saveOutputAsAction.actionPerformed(e);
            else
            {
                //Save file
                try
                {
                    FileWriter fw = new FileWriter(outputFile);
                    fw.write(output.getText());
                    fw.close();
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE, "Failure saving output MSA file:\n" + ex.getMessage());
                    return;
                }
                logger.log(Level.INFO, "Output MSA saved to " + outputFile.getAbsolutePath());
            }
        }
    }

    /**
     * Java Action to save the output MSA to a different file.
     */
    private class SaveOutputAsAction extends AbstractAction
    {

        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public SaveOutputAsAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            this.setEnabled(false);
        }

        /**Implements action.
         *@param e Event that triggers the action.
         */
        public void actionPerformed(ActionEvent e)
        {
            //Show file chooser
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogTitle("Save Output MSA As...");
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            if (outputCurDir != null)
                chooser.setCurrentDirectory(outputCurDir);
            int returnVal = chooser.showOpenDialog(new javax.swing.JFrame());

            //If user accepts
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                //Save file
                outputFile = chooser.getSelectedFile();
                if (!outputFile.exists())
                    outputFile = new File(outputFile.getPath());
                outputCurDir = outputFile.getParentFile();

                try
                {
                    FileWriter fw = new FileWriter(outputFile);
                    fw.write(output.getText());
                    fw.close();
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE, "Failure saving output MSA file:\n" + ex.getMessage());
                    return;
                }
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        ((TitledBorder) outputPanel.getBorder()).setTitle("Output - " + outputFile.getName());
                        outputPanel.repaint();
                    }
                });
                logger.log(Level.INFO,"Output MSA saved to " + outputFile.getAbsolutePath());
            }
        }
    }

    /**
     * Java Action to save both input and output MSA to their respective files.
     */
    private class SaveAllAction extends AbstractAction
    {
        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public SaveAllAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            this.setEnabled(false);
        }

        /**Implements action
         *@param e Event that triggers the action
         */
        public void actionPerformed(ActionEvent e)
        {
            saveInputAction.actionPerformed(e);
            saveOutputAction.actionPerformed(e);
        }
    }

    /**
     * Java Action to convert the input MSA.
     */
    private class ConvertAction extends AbstractAction
    {

        /**Class constructor.
         *@param text Text to display.
         *@param icon Action's icon.
         *@param desc Brief description.
         *@param mnemonic Keyboard shortcut.
         */
        public ConvertAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**Implements action
         *@param e Event that triggers the action
         */
        public void actionPerformed(ActionEvent e)
        {
            //Show options panel
            int n = JOptionPane.showConfirmDialog(new JFrame(),
                    convertPanel,
                    "Convert MSA",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            //If user accepts
            if (n == JOptionPane.OK_OPTION)
            {
                //Get options
                String in = input.getText();
                String inO = convertPanel.getInputOS();
                String inP = convertPanel.getInputProgram();
                String inF = convertPanel.getInputFormat();
                boolean autodetect = convertPanel.getInputAutodetect();
                boolean collapse = convertPanel.getCollapse();
                boolean gaps = convertPanel.getCollapseGaps();
                boolean missing = convertPanel.getCollapseMissing();
                int limit = convertPanel.getCollapseLimit();
                String out = "";
                String outO = convertPanel.getOutputOS();
                String outP = convertPanel.getOutputProgram();
                String outF = convertPanel.getOutputFormat();
                boolean lower = convertPanel.getOutputLowerCase();
                boolean numbers = convertPanel.getOutputResidueNumbers();
                boolean sequential = convertPanel.getOutputSequential();
                boolean match = convertPanel.getOutputMatch();

                //Get converter and convert MSA
                Factory factory = new DefaultFactory();
                Converter converter;

                try
                {
                    converter = factory.getConverter(inO, inP, inF, autodetect,
                            collapse, gaps, missing, limit,
                            outO, outP, outF, lower, numbers, sequential, match, logger.getName());
                    out = converter.convert(in);
                }
                catch (UnsupportedOperationException ex)
                {
                    logger.log(Level.SEVERE, ex.getMessage());
                    return;
                }
                catch (ParseException ex)
                {
                    logger.log(Level.SEVERE, "Failure parsing input file: \n" + ex.getMessage());
                    return;
                }
                
                //Update interface
                output.setText(out);
                SwingUtilities.invokeLater(new Runnable()
                {

                    public void run()
                    {
                        output.setEnabled(true);
                        outputPosition.setEnabled(true);
                        outputScroll.getVerticalScrollBar().setValue(0);
                        outputScroll.getHorizontalScrollBar().setValue(0);
                        saveOutputAsAction.setEnabled(true);
                        saveOutputAction.setEnabled(true);
                        saveAllAction.setEnabled(true);
                    }
                });
            }
        }
    }

    /**
     * Java Action to ask for confirmation before exiting the application.
     */
    private class ExitAction extends AbstractAction
    {

        /**Class constructor
         *@param text Text to display
         *@param icon Action's icon
         *@param desc Brief description
         *@param mnemonic Keyboard shortcut
         */
        public ExitAction(String text, ImageIcon icon, String desc, Integer mnemonic)
        {
            //Initialize action
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**Implements action
         *@param e Event that triggers the action
         */
        public void actionPerformed(ActionEvent e)
        {
            //Show confirm dialog
            int n = JOptionPane.showConfirmDialog(
                    new JFrame(),
                    "Are you sure you want to exit ALTER?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            //If user confirms exit
            if (n == JOptionPane.YES_OPTION)
                System.exit(0);
        }
    }
}
