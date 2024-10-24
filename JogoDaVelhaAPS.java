import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVelhaAPS extends JFrame { 
    private JButton[][] buttons = new JButton[3][3];
    private JLabel labelJogador; 
    private int jogador = 1;
    private int vencedorAnterior = 1;

    public JogoDaVelhaAPS() { 
        setTitle("Jogo da Velha APS");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelJogo = new JPanel();
        panelJogo.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                panelJogo.add(buttons[i][j]);
            }
        }

        labelJogador = new JLabel("Se for X, então 3 pontos pra gente", SwingConstants.CENTER);
        labelJogador.setFont(new Font("Arial", Font.PLAIN, 20));

        add(labelJogador, BorderLayout.NORTH);
        add(panelJogo, BorderLayout.CENTER);
    }

    // Verifica se há uma vitória no jogo
    private boolean verificarVitoria() {
        String[][] tabuleiro = new String[3][3];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = buttons[i][j].getText();
            }
        }

        // Verificação de linhas, colunas e diagonais
        for (int i = 0; i < 3; i++) {
            if (!tabuleiro[i][0].equals("") && tabuleiro[i][0].equals(tabuleiro[i][1]) && tabuleiro[i][1].equals(tabuleiro[i][2])) {
                return true;
            }
            if (!tabuleiro[0][i].equals("") && tabuleiro[0][i].equals(tabuleiro[1][i]) && tabuleiro[1][i].equals(tabuleiro[2][i])) {
                return true;
            }
        }

        if (!tabuleiro[0][0].equals("") && tabuleiro[0][0].equals(tabuleiro[1][1]) && tabuleiro[1][1].equals(tabuleiro[2][2])) {
            return true;
        }
        if (!tabuleiro[0][2].equals("") && tabuleiro[0][2].equals(tabuleiro[1][1]) && tabuleiro[1][1].equals(tabuleiro[2][0])) {
            return true;
        }

        return false;
    }

    // Verifica se houve empate
    private boolean verificarEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    // Reinicia o jogo, fazendo o vencedor anterior ser o próximo a jogar
    private void reiniciarJogo() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        jogador = vencedorAnterior;
        labelJogador.setText("O próximo jogador é " + (jogador == 1 ? "X" : "O"));
    }

    private class ButtonClickListener implements ActionListener {
        private int linha;
        private int coluna;

        public ButtonClickListener(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[linha][coluna].getText().equals("")) {
                
                buttons[linha][coluna].setText(jogador == 1 ? "X" : "O");
                
                if (verificarVitoria()) {
                    String vencedor = jogador == 1 ? "X" : "O"; // Determina o vencedor
                    JOptionPane.showMessageDialog(null, vencedor + " venceu!");

                    vencedorAnterior = jogador;

                    reiniciarJogo();
                } else if (verificarEmpate()) {
                    JOptionPane.showMessageDialog(null, "O jogo empatou!");

                    reiniciarJogo();
                } else {
                    
                    jogador = 3 - jogador;
                    labelJogador.setText("Jogador " + (jogador == 1 ? "X" : "O"));
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JogoDaVelhaAPS jogo = new JogoDaVelhaAPS(); 
            jogo.setVisible(true);
        });
    }
}
