/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.awt.*;
import javax.swing.JOptionPane;

/**
 *
 * @author totzhe
 */
public class FormBoard extends javax.swing.JFrame
{

    private Board board;
    
    private boolean moving;
    
    private static int border = 50;  
    
    /**
     * Creates new form FormBoard
     */
    public FormBoard()
    {
        try
        {
            board = new Board(400);
        } catch (SquareIsNotEmptyException e)
        {
            System.exit(-1);
        }
        initComponents();
        this.invalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Checkers");
        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                onMouseClick(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                onMouseDown(evt);
            }
        });

        jButton1.setText("jButton1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 557, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 515, Short.MAX_VALUE))
        );

        jButton1.getAccessibleContext().setAccessibleName("jButtonAction");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onMouseClick(java.awt.event.MouseEvent evt)//GEN-FIRST:event_onMouseClick
    {//GEN-HEADEREND:event_onMouseClick
        //JOptionPane.showMessageDialog(null, evt.getX());
        //invalidate();
        board.OnClick(evt.getX() - border, evt.getY() - border);
        repaint();
    }//GEN-LAST:event_onMouseClick

    private void onMouseDown(java.awt.event.MouseEvent evt)//GEN-FIRST:event_onMouseDown
    {//GEN-HEADEREND:event_onMouseDown
        // TODO add your handling code here:
    }//GEN-LAST:event_onMouseDown

    @Override
    public void paint(Graphics g)
    {
        Graphics gr = g.create(border, border, board.getBoardWidth(), board.getBoardWidth());
        /*g.setColor(Color.yellow);
         g.fillRect(10, 5, 100, 100);*/
        board.Draw(gr);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(FormBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(FormBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(FormBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(FormBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new FormBoard().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
