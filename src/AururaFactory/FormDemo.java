/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AururaFactory;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author HT
 */
public class FormDemo extends JFrame{
//    private JFrame form;
    JButton nut;
    
    public FormDemo(){
        TaoForm();
    }
    public void TaoForm(){
        nut = new JButton("Péo");
//        this = new JFrame ("Hải Ngu");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);// form show chính giữa màn hình
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(nut);
        this.setLayout(new FlowLayout());
//        this.pack();
        setVisible(true);
    }
    public static void main(String[] args){
        new FormDemo();
    }
}

