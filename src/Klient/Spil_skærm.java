/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author chris
 */
public class Spil_skærm extends javax.swing.JPanel {

    public static int WIDTH = 60;

    public static int BOX_WIDTH = 450;
    public static int BOX_HEIGHT = 80;
    private int antal_terninger = 0;
    private int antal_terninger_i_alt = 0;
    private KlientFunk klient;
    public int[] terninger = new int[6];
    public static String text_til_tekstboks = "";

    public void tilføjText_til_tekstboks(String tekst) {
        if(text_til_tekstboks.isEmpty()){
            text_til_tekstboks = text_til_tekstboks.concat(tekst);
        }else{
            text_til_tekstboks = text_til_tekstboks.concat("\n"+tekst);
        }
        
        System.out.println("Teksten til boksen: "+tekst);
        jTextArea1.setText(text_til_tekstboks);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }

    /**
     * Creates new form Spil_skærm
     */
    public Spil_skærm() {
        initComponents();jLabel_antalterningerialt.setText(""+antal_terninger_i_alt);
        sætAntalTerningerIAlt(antal_terninger_i_alt);
        jTextArea1.setEditable(false);
        //jTextArea1.setText("Test1 \nTest2 \n");
    }

    public void tegnTerninger(int antal) {
        antal_terninger = antal;
        terninger = klient.getTerninger();
        repaint();
    }
    
    public void sætAntalTerningerIAlt(int antal) {
        antal_terninger_i_alt = antal;
        jLabel_antalterningerialt.setText(""+antal_terninger_i_alt); 
    }    

    public void paintComponent(Graphics g) {
        // Herunder referer g til et Graphics-objekt man kan tegne med
        super.paintComponent(g);                // tegn først baggrunden på panelet
        
        int hjørne = 0;
        int xpos = 50;
        int ypos = 60;
        int xmargin = 10;
        int ymargin = 10;
        // Box der skalaere
        //ymargin+(WIDTH*(antal_terninger+1)
        g.drawRoundRect(xpos, ypos, 430, BOX_HEIGHT, 5, 5);
        for (int i = 0; i < antal_terninger; i++) {
            g.drawRect(xpos + xmargin, ypos + ymargin, WIDTH, WIDTH);
            int tal = terninger[i];
            switch (tal) {
                case 6:
                    // Lav 6'ere
                    // Venstre prikker
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 10, WIDTH / 5, WIDTH / 5, 0, 360);
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 20 + 5, WIDTH / 5, WIDTH / 5, 0, 360);
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 35 + 5, WIDTH / 5, WIDTH / 5, 0, 360);

                    // Højre
                    g.fillArc(xpos + xmargin + 37, ypos + ymargin + 10, WIDTH / 5, WIDTH / 5, 0, 360);
                    g.fillArc(xpos + xmargin + 37, ypos + ymargin + 20 + 5, WIDTH / 5, WIDTH / 5, 0, 360);
                    g.fillArc(xpos + xmargin + 37, ypos + ymargin + 35 + 5, WIDTH / 5, WIDTH / 5, 0, 360);
                    break;
                case 5:
                    // 5'er
                    // Venstre prikker
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 10, WIDTH / 5, WIDTH / 5, 0, 360);
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 35 + 5, WIDTH / 5, WIDTH / 5, 0, 360);

                    // Midterste prik
                    g.fillArc(xpos + xmargin + 24, ypos + ymargin + 25, WIDTH / 5, WIDTH / 5, 0, 360);

                    // Højre prikker
                    g.fillArc(xpos + xmargin + 38, ypos + ymargin + 10, WIDTH / 5, WIDTH / 5, 0, 360);
                    g.fillArc(xpos + xmargin + 38, ypos + ymargin + 35 + 5, WIDTH / 5, WIDTH / 5, 0, 360);
                    break;
                case 4:
                    // 4'er
                    // Venstre prikker
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 10, WIDTH / 5, WIDTH / 5, 0, 360);
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 35 + 5, WIDTH / 5, WIDTH / 5, 0, 360);

                    // Højre prikker
                    g.fillArc(xpos + xmargin + 38, ypos + ymargin + 10, WIDTH / 5, WIDTH / 5, 0, 360);
                    g.fillArc(xpos + xmargin + 38, ypos + ymargin + 35 + 5, WIDTH / 5, WIDTH / 5, 0, 360);
                    break;
                case 3:
                    // 3'er
                    // Venstre prikker
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 10, WIDTH / 5, WIDTH / 5, 0, 360);

                    // Midterste prik
                    g.fillArc(xpos + xmargin + 24, ypos + ymargin + 25, WIDTH / 5, WIDTH / 5, 0, 360);

                    // Højre prikker
                    g.fillArc(xpos + xmargin + 38, ypos + ymargin + 40, WIDTH / 5, WIDTH / 5, 0, 360);
                    break;

                case 2:
                    // Venstre prikker
                    g.fillArc(xpos + xmargin + 10, ypos + ymargin + 10, WIDTH / 5, WIDTH / 5, 0, 360);
                    // Højre prikker
                    g.fillArc(xpos + xmargin + 38, ypos + ymargin + 40, WIDTH / 5, WIDTH / 5, 0, 360);
                    break;

                case 1:
                    // Midterste prik
                    g.fillArc(xpos + xmargin + 24, ypos + ymargin + 25, WIDTH/5, WIDTH/5, 0, 360);
                    break;

                default:
                    break;
            }
            xpos += 70;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton_Liar = new javax.swing.JButton();
        jButton_guess = new javax.swing.JButton();
        jSlider_value = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSpinner_antal = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel_antalterningerialt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(600, 500));

        jButton_Liar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton_Liar.setForeground(new java.awt.Color(255, 51, 51));
        jButton_Liar.setText("Løgner!");
        jButton_Liar.setEnabled(false);
        jButton_Liar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LiarActionPerformed(evt);
            }
        });

        jButton_guess.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_guess.setForeground(new java.awt.Color(0, 255, 0));
        jButton_guess.setText("Gæt!");
        jButton_guess.setEnabled(false);
        jButton_guess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_guessActionPerformed(evt);
            }
        });

        jSlider_value.setMajorTickSpacing(1);
        jSlider_value.setMaximum(6);
        jSlider_value.setMinimum(2);
        jSlider_value.setMinorTickSpacing(1);
        jSlider_value.setPaintLabels(true);
        jSlider_value.setPaintTicks(true);
        jSlider_value.setSnapToTicks(true);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Værdi");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Antal");

        jSpinner_antal.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        jSpinner_antal.setValue(3);
        jSpinner_antal.setVerifyInputWhenFocusTarget(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Terninger i alt:");

        jLabel_antalterningerialt.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel_antalterningerialt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_antalterningerialt.setText("0");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_Liar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_antalterningerialt, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinner_antal, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_guess, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSlider_value, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_Liar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel_antalterningerialt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSlider_value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSpinner_antal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_guess, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_guessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_guessActionPerformed
        int antal = (int) jSpinner_antal.getValue();
        int værdi = (int) jSlider_value.getValue();
        System.out.println("Antal er: "+antal+" Værdien er: "+værdi);
        klient.sendKommando("Guess("+antal+","+værdi+")");
        skjulknapper();
    }//GEN-LAST:event_jButton_guessActionPerformed

    private void jButton_LiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LiarActionPerformed
        // TODO add your handling code here:
        System.out.println("Liar!");
        klient.sendKommando("Liar!");
        skjulknapper();
    }//GEN-LAST:event_jButton_LiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Liar;
    private javax.swing.JButton jButton_guess;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_antalterningerialt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider_value;
    private javax.swing.JSpinner jSpinner_antal;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    void setlogik(KlientFunk klient) {
        this.klient = klient;
    }

    void visknapper() {
        jButton_guess.setEnabled(true);
        jButton_Liar.setEnabled(true);
    }

    void skjulknapper() {
        jButton_guess.setEnabled(false);
        jButton_Liar.setEnabled(false);
    }
}
