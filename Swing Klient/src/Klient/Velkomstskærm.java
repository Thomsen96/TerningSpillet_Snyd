/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Velkomstskærm extends javax.swing.JPanel {

    private KlientFunk klient;
    private Rest_Klient rest;
    public int start = 0;
    private String navn;
    private int port;
    private String IP;
    public boolean server_started = false;
    private final short MAX_NAME_LENGTH = 38;
    DefaultTableModel model;
    
    private boolean noGamesOnline = Boolean.FALSE;

    /**
     * Creates new form Velkomstskærm
     */
    public Velkomstskærm() {
        initComponents();
        this.rest = new Rest_Klient();

        model = (DefaultTableModel) jTable_games.getModel();
        jSpinner_playerNum.setValue(2);
        
        updateGames();
        inputFraJtable();
                
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    updateGames();
                    System.out.println("Async: Update table");
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {

                    }

                }
            }
        });

        t.start();
        
        //update game has to be run before taking imput from the table.
        /*try{
            Thread.sleep(500);
        } catch (Exception e){
            
        }*/
        
       
    }

    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField_ip = new javax.swing.JTextField();
        jButton_connect = new javax.swing.JButton();
        jTextField_port = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_playername = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_games = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField_username = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPasswordField_password = new javax.swing.JPasswordField();
        diceChoice = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSpinner_playerNum = new javax.swing.JSpinner();
        jButton_create = new javax.swing.JButton();
        jButton_Closegame = new javax.swing.JButton();
        jButton_update = new javax.swing.JButton();

        jTextField_ip.setText("130.225.170.205");
        jTextField_ip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_ipActionPerformed(evt);
            }
        });

        jButton_connect.setText("Forbind");
        jButton_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_connectActionPerformed(evt);
            }
        });

        jTextField_port.setText("?");
        jTextField_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_portActionPerformed(evt);
            }
        });
        jTextField_port.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_portKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_portKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_portKeyReleased(evt);
            }
        });

        jLabel2.setText("IP");

        jLabel3.setText("Port");

        jLabel5.setText("Spillernavn");

        jTextField_playername.setText("Navn");
        jTextField_playername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_playernameActionPerformed(evt);
            }
        });

        jTable_games.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Skaber", "Spillere", "Terninger", "Port"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_games.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable_games.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_gamesMouseClicked(evt);
            }
        });
        jTable_games.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTable_gamesCaretPositionChanged(evt);
            }
        });
        jTable_games.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable_gamesKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable_gamesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable_gamesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_games);
        if (jTable_games.getColumnModel().getColumnCount() > 0) {
            jTable_games.getColumnModel().getColumn(0).setResizable(false);
            jTable_games.getColumnModel().getColumn(1).setResizable(false);
            jTable_games.getColumnModel().getColumn(2).setResizable(false);
            jTable_games.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("Velkommen til terningespillet Snyd!");

        jTextField_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_usernameActionPerformed(evt);
            }
        });

        jLabel6.setText("Brugernavn");

        jLabel7.setText("Kode");

        jPasswordField_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField_passwordActionPerformed(evt);
            }
        });

        diceChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));
        diceChoice.setSelectedIndex(5);
        diceChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diceChoiceActionPerformed(evt);
            }
        });

        jLabel8.setText("Terninger");

        jLabel9.setText("Spillere");

        jSpinner_playerNum.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton_create.setText("Opret Server");
        jButton_create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_createActionPerformed(evt);
            }
        });

        jButton_Closegame.setText("Luk Server");
        jButton_Closegame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ClosegameActionPerformed(evt);
            }
        });

        jButton_update.setText("Opdater");
        jButton_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel5)
                                .addGap(154, 154, 154))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_ip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTextField_playername, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jTextField_port, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_connect))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPasswordField_password, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_username, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(diceChoice, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSpinner_playerNum)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(25, 25, 25)
                                .addComponent(jButton_create)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_Closegame)
                                .addGap(73, 73, 73)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_update)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_ip, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_connect)
                    .addComponent(jTextField_port, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_playername, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_update)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_create)
                            .addComponent(jButton_Closegame)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(diceChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner_playerNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField_username, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addGap(5, 5, 5)
                                .addComponent(jPasswordField_password, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void diceChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diceChoiceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diceChoiceActionPerformed

    private void jTextField_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_usernameActionPerformed

    private void jTextField_playernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_playernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_playernameActionPerformed

    private void jTextField_portKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_portKeyReleased
        jTextField_portKeyPressed(evt);
    }//GEN-LAST:event_jTextField_portKeyReleased

    private void jTextField_portKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_portKeyPressed
        // Gør knapperne inaktive
        jButton_connect.setEnabled(false);
        //jButton_Opretserver.setEnabled(false);

        // Gør dem aktive igen, når de overholder reglerne
        if (jTextField_port.getText().trim().matches("[0-9]+") && jTextField_port.getText().length() < 5 && jTextField_port.getText().length() > 3) { // Max port er "65535" så vi sætter et max på port 9999 og min på 1000
            jButton_connect.setEnabled(true);
            //jButton_Opretserver.setEnabled(true);
        }
    }//GEN-LAST:event_jTextField_portKeyPressed

    private void jTextField_portKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_portKeyTyped
        jTextField_portKeyPressed(evt);
    }//GEN-LAST:event_jTextField_portKeyTyped

    private void jTextField_portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_portActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_portActionPerformed

    private void jButton_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_connectActionPerformed

        validerPortogNavn();

        IP = jTextField_ip.getText();
        start = 1;
    }//GEN-LAST:event_jButton_connectActionPerformed

    private void jTextField_ipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_ipActionPerformed

    private void jButton_createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_createActionPerformed
        createGame();
        updateGames();
    }//GEN-LAST:event_jButton_createActionPerformed

    private void jTable_gamesCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTable_gamesCaretPositionChanged
        //InputFraJtable();
    }//GEN-LAST:event_jTable_gamesCaretPositionChanged

    private void jTable_gamesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_gamesKeyTyped
        //InputFraJtable();

    }//GEN-LAST:event_jTable_gamesKeyTyped

    private void jTable_gamesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_gamesKeyPressed
        // InputFraJtable();
    }//GEN-LAST:event_jTable_gamesKeyPressed

    private void jTable_gamesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_gamesMouseClicked
        inputFraJtable();
    }//GEN-LAST:event_jTable_gamesMouseClicked

    private void jTable_gamesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_gamesKeyReleased
        inputFraJtable();
    }//GEN-LAST:event_jTable_gamesKeyReleased

    private void jPasswordField_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField_passwordActionPerformed

    private void jButton_ClosegameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ClosegameActionPerformed
        // TODO add your handling code here:
        closeGame();
        updateGames();
        System.out.println("Update game finished");
        inputFraJtable();
        
    }//GEN-LAST:event_jButton_ClosegameActionPerformed

    private void jButton_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateActionPerformed
        // TODO add your handling code here:
        updateGames();
    }//GEN-LAST:event_jButton_updateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> diceChoice;
    private javax.swing.JButton jButton_Closegame;
    private javax.swing.JButton jButton_connect;
    private javax.swing.JButton jButton_create;
    private javax.swing.JButton jButton_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPasswordField_password;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner_playerNum;
    private javax.swing.JTable jTable_games;
    private javax.swing.JTextField jTextField_ip;
    private javax.swing.JTextField jTextField_playername;
    private javax.swing.JTextField jTextField_port;
    private javax.swing.JTextField jTextField_username;
    // End of variables declaration//GEN-END:variables

    String getnavn() {
        return navn;
    }

    int getport() {
        return port;
    }

    String getIP() {
        return IP;
    }

    void resetstart() {
        start = 1;
    }
    
    void updateGames() {
        ArrayList<SpilData> spil = rest.getGames();
        if (spil.isEmpty()) {
            String noGamesMsg = "No games online";
                        
            //Checks for update on already empty list
            if(noGamesOnline){
                return;
            }
            try{
                model.removeRow(0);
            } catch (Exception e){
                System.out.println("Client started with no games online.");
            }
            model.addRow(new Object[]{noGamesMsg});
            noGamesOnline = true;
        } else {
            noGamesOnline = false;
            int x = jTable_games.getSelectedColumn();
            int y = jTable_games.getSelectedRow();
            
            int size = model.getRowCount();
            for (int i = 0; i < size; i++) {
                model.removeRow(0);
            }
            for (int i = 0; i < spil.size(); i++) {
                model.addRow(new Object[]{
                    spil.get(i).getBrugernavn(),
                    spil.get(i).getSpillere(),
                    spil.get(i).getTerninger(),
                    spil.get(i).getPort()});
            }
            try {
                jTable_games.setRowSelectionInterval(y, y);
            } catch (Exception e) {
                jTable_games.setRowSelectionInterval(0, 0);
            }

        }
    }
    
    void createGame() {
        String user = jTextField_username.getText();
        String pass = new String(jPasswordField_password.getPassword());
        int tern = diceChoice.getSelectedIndex() + 1;
        int spillere = (int) jSpinner_playerNum.getValue();
        
        System.out.println("createGame kaldes med:" + user + " " + pass + " " + tern + " " + spillere+".");
        SpilData nytSpil = rest.createGame(user, pass, tern, spillere);
        
        if (nytSpil == null) {
            System.err.println("Failed to create game");
            return;
        }
        System.out.println(nytSpil.getBrugernavn());
        
    }
    
    void closeGame() {
        String user = jTextField_username.getText();
        String pass = new String(jPasswordField_password.getPassword());
        int tempPort;
        try {
            tempPort = Integer.parseInt(jTextField_port.getText());
        } catch (java.lang.NumberFormatException e){
            System.out.println("No port selected.");
            return;
        }
        
        System.out.println("closeGame kaldes med:" + user + " " + pass + " " + tempPort + ".");
        tempPort = rest.closeGame(tempPort, user, pass);
        System.out.println("Closed game returned with: "+ tempPort);
        
    }

    /**
     * Tager den indskrevede port og navn fra tekstboksene og forsøger at
     * validere dem. Hvis det ikke lykkes bliver navnet ændret til
     * "Spillernavn".
     */
    private void validerPortogNavn() {

        /*Tag strengen og gør den til en int der kan være i port */
        String port_streng = jTextField_port.getText();
        port = 0;
        for (int i = 0; i < port_streng.length(); i++) {
            port += (port_streng.charAt(port_streng.length() - 1 - i) - 48) * Math.pow(10, i);
        }

        /* Valider navnet */
        String navn_valid = jTextField_playername.getText();
        navn_valid = navn_valid.replaceAll("[^\\x00-\\xFF]", ""); // Frasorterer alle karaktere udover dem der er fra 00 til 255

        if (navn_valid.length() > MAX_NAME_LENGTH || navn_valid.trim().equals("")) { // Hvis navnet er for langt eller kun indeholder mellemrum
            navn = "Spillernavn";
        } else {
            navn = navn_valid;
        }
    }

    /**
     * Kaldes når der sker et input på de aktive spil og opdatere porten som at
     * det køre på.
     */
    void inputFraJtable() {
        int i = jTable_games.getSelectedRow();
        
        if (jTable_games.getSize().height < 1|| i < 0){
            return;
        }
        
        Object port;
        port = model.getValueAt(i, 3);
        try{
            jTextField_port.setText(port.toString());
        } catch (java.lang.NullPointerException e){
            System.out.println("No values in table. No port selected.");
        }
        System.out.println("New port selected: "+port);
    }
}
