import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class World extends JPanel {
    Node[][] board;
    int size;
    public World(int n) {
        reset(n);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j <size; j++) {
                        Node n = board[i][j];
                        if(n.getSquare().contains(e.getPoint())){
                            n.reverse();
                        }
                    }
                }
                repaint();
            }
        });
    }


    void reset(int size){
        this.board = new Node[size][size];
        this.size = size;
        double node_size_x = 800./ size;
        double node_size_y = 700. / size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double x = i*node_size_x;
                double y = j*node_size_y;
                this.board[i][j] = new Node(0,x,y,node_size_x, node_size_y);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("Printing world");
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size ; j++) {
                g2d.setColor(board[i][j].getColor());
                g2d.fill(board[i][j].getSquare());
            }
        }
    }
}
