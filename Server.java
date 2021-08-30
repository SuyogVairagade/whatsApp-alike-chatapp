package chat_app;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
public class Server implements ActionListener{
    JPanel p1;
    JTextField t1;
    JButton b1;
    
    static Box vertical = Box.createVerticalBox();
    
    
    static JPanel a1;
    static JFrame f1 = new JFrame();
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    
    Boolean typing;
    Server(){
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,480,60);
        f1.add(p1);
        
        ImageIcon i1  = new ImageIcon(ClassLoader.getSystemResource("chat_app/Icon/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        //Creating Label for 3.png
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5,17,30,30);
        //adding label/image to screen
        p1.add(l1);
       
        
        
        l1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                System.exit(0);
            }
        });
        
        ImageIcon i7  = new ImageIcon(ClassLoader.getSystemResource("chat_app/Icon/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        //Creating Label for 3.png
        JLabel l7 = new JLabel(i9);
        l7.setBounds(260,17,30,30);
        //adding label/image to screen
        p1.add(l7);
        
        ImageIcon i10  = new ImageIcon(ClassLoader.getSystemResource("chat_app/Icon/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        //Creating Label for 3.png
        JLabel l5 = new JLabel(i12);
        l5.setBounds(310,16,30,30);
        //adding label/image to screen
        p1.add(l5);
        
        ImageIcon i13  = new ImageIcon(ClassLoader.getSystemResource("chat_app/Icon/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15, 24, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        //Creating Label for 3.png
        JLabel l6 = new JLabel(i15);
        l6.setBounds(360,16,15,30);
        //adding label/image to screen
        p1.add(l6);
        
       
        
        JLabel l4 = new JLabel("Online");
        l4.setFont(new Font("SAN_SERIF",Font.PLAIN,12));
        l4.setBounds(80, 32, 150, 16);
        l4.setForeground(Color.WHITE);
        p1.add(l4);
        
        javax.swing.Timer t = new javax.swing.Timer(1, (ActionEvent ae) -> {
            if(!typing){
                l4.setText("Active Now");
            }
        });
        
        t.setInitialDelay(1000); // in milli seconds   
        
        a1 = new JPanel();
        a1.setBounds(5,65,390,447);
        a1.setFont(new Font("SAN_SERIF",Font.ITALIC,16));
        a1.setBackground(Color.WHITE);
        
        f1.add(a1);
        
        ImageIcon i4  = new ImageIcon(ClassLoader.getSystemResource("chat_app/Icon/1.png"));
        Image i5 = i4.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        //Creating Label for 3.png
        JLabel l2 = new JLabel(i6);
        l2.setBounds(40,17,30,30);
        //adding label/image to screen
        p1.add(l2);
        
        JLabel l3 = new JLabel("Ganesh Gaitonde");
        l3.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        l3.setBounds(80, 10, 150, 25);
        l3.setForeground(Color.WHITE);
        p1.add(l3);
        
        //a1 = new JTextArea();
        //a1.setBounds(5,75,390,447);
        //a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
       // a1.setEditable(false);
       // a1.setLineWrap(true);
       // a1.setWrapStyleWord(true);
        //a1.setBackground(Color.PINK);
        //add(ta);
        
        t1 = new JTextField();
        t1.setBounds(5,517,275,30);
        t1.setBackground(Color.WHITE);
        t1.setFont(new Font("ROBOTO", Font.PLAIN,14));
        f1.add(t1);
        
        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                l4.setText("typing...");
                t.stop();
                
                typing = true;
            }
            
            public void keyReleased(KeyEvent ke){
                typing = false;
                
                if(!t.isRunning()){
                    t.start();
                }
            }
        });
        /*ImageIcon i16  = new ImageIcon(ClassLoader.getSystemResource("chat_app/icon/OIP.png"));
        Image i17 = i16.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i18 = new ImageIcon(i17);*/
        b1 = new JButton("Send");
        b1.setBounds(280,517,117,30);  
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        b1.addActionListener(this);
        f1.add(b1);
        
        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(400,550);
        f1.setLocation(300,100);
        f1.setUndecorated(true);
        f1.setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent ae){
        try{    
            String out = t1.getText();
            
            JPanel p2 = formatLabel(out);
            
            a1.setLayout(new BorderLayout());
            
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15)); //height between each chat
            
            a1.add(vertical, BorderLayout.PAGE_START);
     
            dout.writeUTF(out);
            t1.setText("");
        } 
        catch(Exception e){
            System.out.println(e);
        }
    }
    
     public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width: 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma",Font.ITALIC,12));
        l1.setBackground(new Color(37,211,102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    public static void main(String[] args){
        new Server().f1.setVisible(true);
        String msginput = "";
        try{
            skt = new ServerSocket(6001);
            while(true){
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
            
                while(true){
                     msginput = din.readUTF();
                     JPanel p2 = formatLabel(msginput);
                     
                     JPanel left  = new JPanel(new BorderLayout());
                     left.add(p2, BorderLayout.LINE_START);
                     vertical.add(left);
                     f1.validate();  
                }
            }
        }catch(Exception e){
        
        }
    }
}