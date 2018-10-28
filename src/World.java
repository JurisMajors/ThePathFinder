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
                if(SwingUtilities.isRightMouseButton(e)){
                    if(isStartIn()){ // if there is a start node already than the clicked one must be an end node
                        reactOnClick(e, 0);
                    }else if(!isEndIn()){
                        reactOnClick(e, 1);
                    }else{
                        reactOnClick(e, 1);
                    }
                }else {
                    reactOnClick(e, 2);
                }
                repaint();
            }
        });
    }

    /**
     * @param e mouse event
     * @param way 0: end point, 1: start point, 2: obstacle/path point
     */
    void reactOnClick(MouseEvent e, int way){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Node n = board[i][j];
                if (n.getSquare().contains(e.getPoint())) {
                    if(!isBorder(i, j)){
                        if(way == 0){
                            n.setEnd();
                        }else if( way == 1){
                            n.setStart();
                        }else {
                            n.reverse();
                        }
                    }
                }
            }
        }

    }
    boolean isEndIn(){
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size ; j++) {
                if(board[i][j].end){
                    return true;
                }
            }
        }
        return false;
    }

    boolean isStartIn(){
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size ; j++) {
                if(board[i][j].start){
                    return true;
                }
            }
        }
        return false;

    }

    boolean isBorder(int i, int j){
        return (i == 0 || j == 0 || i == this.size-1 || j == this.size - 1);
    }
    void reset(int size){
        this.board = new Node[size][size];
        this.size = size;
        int type;
        double node_size_x = 790./ size;
        double node_size_y = 700. / size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double x = i*node_size_x;
                double y = j*node_size_y;
                if(isBorder(i, j)){
                    type = 1;
                }else{
                    type = 0;
                }
                this.board[i][j] = new Node(type,x,y,node_size_x, node_size_y);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        System.out.println("Printing world");
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
