package de.staticline;

import jade.core.Agent;
import jade.gui.GuiEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class UserAgentUI extends javax.swing.JFrame {
    private JTextPane display;
    private JPanel controls;
    private JButton b_divide;
    private JButton b_multiply;
    private JButton b_subtract;
    private JButton b_add;
    
    private UserAgent myagent;
    private String operation = null;
    private double val1 = 0.;
    private double val2 = 0.;
    private boolean commaSet = false;
    
    private static final String CALC_EVENT = "calculate";
    
    
    public UserAgentUI(UserAgent a) {
        super();
        initGUI();
        myagent = a;
    }
    
    private void initGUI() {
        try {
            BorderLayout thisLayout = new BorderLayout();
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            thisLayout.setHgap(2);
            getContentPane().setLayout(thisLayout);
            this.setResizable(false);
            
            {
                display = new JTextPane();
                BorderLayout displayLayout = new BorderLayout();
                display.setLayout(displayLayout);
                getContentPane().add(display, BorderLayout.NORTH);
                display.setSize(400, 40);
                display.setPreferredSize(new java.awt.Dimension(400, 36));
                display.setEditable(false);
                display.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent evt) {
                        thisKeyPressed(evt);
                    }
                });
                display.setText("0");
            }
            {
                controls = new JPanel();
                FlowLayout controlsLayout = new FlowLayout();
                controls.setLayout(controlsLayout);
                getContentPane().add(controls, BorderLayout.CENTER);
                controls.setPreferredSize(new java.awt.Dimension(400, 50));
                {
                    b_add = new JButton();
                    controls.add(b_add);
                    b_add.setText("+");
                    b_add.setLayout(null);
                    b_add.setSize(30, 30);
                    b_add.setFont(new java.awt.Font("Dialog",1,18));
                    b_add.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                            operationMouseClicked(evt);
                        }
                    });
                }
                {
                    b_subtract = new JButton();
                    controls.add(b_subtract);
                    b_subtract.setText("-");
                    b_subtract.setLayout(null);
                    b_subtract.setSize(30, 30);
                    b_subtract.setFont(new java.awt.Font("Dialog",1,18));
                    b_subtract.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                            operationMouseClicked(evt);
                        }
                    });
                }
                {
                    b_multiply = new JButton();
                    controls.add(b_multiply);
                    b_multiply.setText("x");
                    b_multiply.setLayout(null);
                    b_multiply.setSize(30, 30);
                    b_multiply.setFont(new java.awt.Font("Dialog",1,18));
                    b_multiply.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                            operationMouseClicked(evt);
                        }
                    });
                }
                {
                    b_divide = new JButton();
                    controls.add(b_divide);
                    b_divide.setText("/");
                    b_divide.setLayout(null);
                    b_divide.setSize(30, 30);
                    b_divide.setFont(new java.awt.Font("Dialog",1,18));
                    b_divide.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                            operationMouseClicked(evt);
                        }
                    });
                }
            }
            pack();
            this.setSize(400, 104);
        } catch (Exception e) {
            //add your error handling code here
            e.printStackTrace();
        }
    }
    
    private void operationMouseClicked(MouseEvent evt) {
        JButton src = (JButton)evt.getSource();
        operation = src.getText();
        //val1=Integer.getInteger(display.getText());//FIXME: error here!
        val1=Double.valueOf(display.getText());
        display.setText("0");
        display.requestFocus();
    }
    
    private void thisKeyPressed(KeyEvent evt) {
        //System.out.println(evt.toString());
        if(evt.getKeyCode()>47 && evt.getKeyCode()<58){
            //0...9
            display.setText(display.getText()+evt.getKeyChar());
        }else if(evt.getKeyCode()==43){
            //+
            JButton src = (JButton)evt.getSource();
            operation=src.getText();
            val1=Integer.getInteger(display.getText());
            display.setText("0");
        }else if(evt.getKeyCode()==45){
            //-
            JButton src = (JButton)evt.getSource();
            operation=src.getText();
            val1=Integer.getInteger(display.getText());
            display.setText("0");
        }else if(evt.getKeyCode()==42 || evt.getKeyCode()==88){
            //* && x
            JButton src = (JButton)evt.getSource();
            operation=src.getText();
            val1=Integer.getInteger(display.getText());
            display.setText("0");
        }else if(evt.getKeyCode()==47){
            ///
            JButton src = (JButton)evt.getSource();
            operation=src.getText();
            val1=Integer.getInteger(display.getText());
            display.setText("0");
        }else if(evt.getKeyCode()==8){
            //delete
            String updated = display.getText();
            display.setText(updated.substring(0, updated.length()-1));
            if(display.getText().matches("[0-9]*")){
                System.out.println("comma out");
                commaSet=false;
            }
        }else if(evt.getKeyCode()==27 || evt.getKeyCode()==67){
            //esc && c
            display.setText("");
            operation=null;
            val1=0;
            val2=0;
            commaSet=false;
        }else if(evt.getKeyCode()==10){
            //enter
            val2=Integer.getInteger(display.getText());
            calculate();
        }else if(evt.getKeyCode()==44 || evt.getKeyCode()==46){
            //, && .
            if(commaSet == false){
                commaSet = true;
                display.setText(display.getText()+evt.getKeyChar());
            }
        }
    }
    
    private void calculate(){
        //myagent.calculate(val1, val2, operation);
        GuiEvent ev = new GuiEvent((Object) this,UserAgent.CALC_EVENT);
        ev.addParameter(val1);
        ev.addParameter(val2);
        ev.addParameter(operation);
        myagent.postGuiEvent(ev);
    }
    
    public void setValue(String val){
        //quick 'n dirty...
        display.setText(val);
    }

}
