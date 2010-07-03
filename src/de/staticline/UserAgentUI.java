package de.staticline;

import jade.gui.GuiEvent;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;


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
    private JTextPane calculationDislpay;
    private JPanel display_panel;
    private JButton b_enter;
    private JPanel controls_down;
    private JPanel controls_up;
    private JButton b_multiply;
    private JButton b_subtract;
    private JButton b_add;
    
    private UserAgent myagent;
    private String operation = null;
    private double val1 = 0.;
    private double val2 = 0.;
    private boolean commaSet = false;
    private boolean firstKey = true;
    
    private static final String CALC_EVENT = "calculate";
    
    public UserAgentUI(){//for ui builder only
    	super();
    	initGUI();
    }
    
    public UserAgentUI(UserAgent a) {
        super();
        initGUI();
        myagent = a;
        //this.setLocationRelativeTo(null);//center on screen, but JADE UI is in front :(
        display.requestFocus();
    }
    
    private void initGUI() {
        try {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS);
            getContentPane().setLayout(thisLayout);
            this.setResizable(false);
            {
            	display_panel = new JPanel();
            	getContentPane().add(display_panel);
            	{
            		calculationDislpay = new JTextPane();
            		display_panel.add(calculationDislpay);
            		calculationDislpay.setText("");
            		calculationDislpay.setPreferredSize(new java.awt.Dimension(398, 31));
            		calculationDislpay.setAutoscrolls(false);
            		calculationDislpay.setEditable(false);
            	}
            	{
            		display = new JTextPane();
            		display_panel.add(display);
            		BorderLayout displayLayout = new BorderLayout();
            		display.setLayout(displayLayout);
            		display.setSize(400, 40);
            		display.setPreferredSize(new java.awt.Dimension(400, 19));
            		display.setEditable(false);
            		display.addKeyListener(new KeyAdapter() {
            			public void keyPressed(KeyEvent evt) {
            				thisKeyPressed(evt);
            			}
            		});
            		display.setText("0");
            	}
            }

            {
                controls = new JPanel();
                BoxLayout controlsLayout = new BoxLayout(controls, javax.swing.BoxLayout.Y_AXIS);
                controls.setLayout(controlsLayout);
                getContentPane().add(controls);
                controls.setPreferredSize(new java.awt.Dimension(400, 50));
                {
                	controls_up = new JPanel();
                	controls.add(controls_up);
                	controls_up.setPreferredSize(new java.awt.Dimension(400, 31));
                	{
                		b_add = new JButton();
                		controls_up.add(b_add);
                		b_add.setText("+");
                		b_add.setLayout(null);
                		b_add.setSize(30, 30);
                		b_add.setFont(new java.awt.Font("Dialog",1,18));
                		b_add.setPreferredSize(new java.awt.Dimension(92, 29));
                		b_add.addMouseListener(new MouseAdapter() {
                			public void mouseClicked(MouseEvent evt) {
                				operationMouseClicked(evt);
                			}
                		});
                	}
                	{
                		b_subtract = new JButton();
                		controls_up.add(b_subtract);
                		b_subtract.setText("-");
                		b_subtract.setLayout(null);
                		b_subtract.setSize(30, 30);
                		b_subtract.setFont(new java.awt.Font("Dialog",1,18));
                		b_subtract.setPreferredSize(new java.awt.Dimension(92, 29));
                		b_subtract.addMouseListener(new MouseAdapter() {
                			public void mouseClicked(MouseEvent evt) {
                				operationMouseClicked(evt);
                			}
                		});
                	}
                	{
                		b_multiply = new JButton();
                		controls_up.add(b_multiply);
                		b_multiply.setText("x");
                		b_multiply.setLayout(null);
                		b_multiply.setSize(30, 30);
                		b_multiply.setFont(new java.awt.Font("Dialog",1,18));
                		b_multiply.setPreferredSize(new java.awt.Dimension(92, 29));
                		b_multiply.addMouseListener(new MouseAdapter() {
                			public void mouseClicked(MouseEvent evt) {
                				operationMouseClicked(evt);
                			}
                		});
                	}
                	{
                		b_divide = new JButton();
                		controls_up.add(b_divide);
                		b_divide.setText("/");
                		b_divide.setLayout(null);
                		b_divide.setSize(30, 30);
                		b_divide.setFont(new java.awt.Font("Dialog",1,18));
                		b_divide.setPreferredSize(new java.awt.Dimension(92, 29));
                		b_divide.addMouseListener(new MouseAdapter() {
                			public void mouseClicked(MouseEvent evt) {
                				operationMouseClicked(evt);
                			}
                		});
                	}
                }
                {
                	controls_down = new JPanel();
                	controls.add(controls_down);
                	{
                		b_enter = new JButton();
                		controls_down.add(b_enter);
                		b_enter.setText("=");
                		b_enter.setPreferredSize(new java.awt.Dimension(385, 22));
                		b_enter.addMouseListener(new MouseAdapter() {
                			public void mouseClicked(MouseEvent evt) {
                				operationEnterClicked(evt);
                			}
                		});
                	}
                }
            }
            pack();
            this.setSize(400, 169);
        } catch (Exception e) {
            //add your error handling code here
            e.printStackTrace();
        }
    }
    //MOUSE EVENTS
    private void operationMouseClicked(MouseEvent evt) {
        JButton src = (JButton)evt.getSource();
        operation = src.getText();
        val1=Double.valueOf(display.getText());
        display.setText("0");
        calculationDislpay.setText(val1+operation);
        display.requestFocus();
        firstKey = true;
        commaSet = false;
    }
    
    private void operationEnterClicked(MouseEvent evt) {
        val2=Double.parseDouble(display.getText());
        firstKey = true;
        display.setText("0");
        display.requestFocus();
        calculate();
    }
    
    //KEY EVENTS
    private void thisKeyPressed(KeyEvent evt) {
        //System.out.println(evt.toString());
        if(evt.getKeyCode()>47 && evt.getKeyCode()<58){
            //0...9
        	if(firstKey){
        		display.setText("");
        		firstKey = false;
        	}
            display.setText(display.getText()+evt.getKeyChar());
        }else if(evt.getKeyCode()==43){
            //+
            JButton src = (JButton)evt.getSource();
            operation=src.getText();
            val1=Double.parseDouble(display.getText());
            display.setText("0");
            calculationDislpay.setText(val1+operation);
            firstKey = true;
            commaSet = false;
        }else if(evt.getKeyCode()==45){
            //-
            JButton src = (JButton)evt.getSource();
            operation=src.getText();
            val1=Double.parseDouble(display.getText());
            display.setText("0");
            calculationDislpay.setText(val1+operation);
            firstKey = true;
            commaSet = false;
        }else if(evt.getKeyCode()==42 || evt.getKeyCode()==88){
            //* && x
            JButton src = (JButton)evt.getSource();
            operation=src.getText();
            val1=Double.parseDouble(display.getText());
            display.setText("0");
            calculationDislpay.setText(val1+operation);
            firstKey = true;
            commaSet = false;
        }else if(evt.getKeyCode()==47){
            ///
            JButton src = (JButton)evt.getSource();
            operation=src.getText();
            val1=Double.parseDouble(display.getText());
            display.setText("0");
            calculationDislpay.setText(val1+operation);
            firstKey = true;
            commaSet = false;
        }else if(evt.getKeyCode()==8){
            //delete
            String updated = display.getText();
            display.setText(updated.substring(0, updated.length()-1));
            calculationDislpay.setText(val1+operation);
            if(display.getText().matches("[0-9]*")){
                commaSet=false;
            }
        }else if(evt.getKeyCode()==27 || evt.getKeyCode()==67){
            //esc && c
            display.setText("0");
            calculationDislpay.setText(val1+operation);
            operation=null;
            val1=0;
            val2=0;
            commaSet=false;
            firstKey = true;
        }else if(evt.getKeyCode()==10){
            //enter
        	val2=Double.parseDouble(display.getText());
        	calculationDislpay.setText(val1+operation+val2+"=");
            firstKey = true;
            display.setText("0");
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
    	calculationDislpay.setText(this.val1 + operation + this.val2 + " = " + val);
    	//reset
        val1 = 0;
        val2 = 0;
        operation = null;
    }

}
