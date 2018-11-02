import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class World extends JPanel {
    Node[][] board;
    int size;
    private final int[] di = { -1,  0, 0,  1 , };
    private final int[] dj = { 0, -1, 1,  0, };
//    private final int[] di = { -1,  0, 0,  1 , -1, 1, -1, 1};
//    private final int[] dj = { 0, -1, 1,  0, -1, 1, 1, -1};
    private MazeGenerator generator;
    World(int n) {
        reset(n);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    boolean is_start = isStartIn();
                    boolean is_end = isEndIn();
                    if(is_start && is_end){
                        // do nothing
                    }else if(is_start){ // if there is a start node already than the clicked one must be an end node
                        reactOnClick(e, 0);
                    }else if(!is_end) {
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
        generator = new MazeGenerator(this);
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
                    if(!isBorder(i, j)){ // protect borders
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

    boolean outOfBounds(int x, int y, int i) {
        if (x + di[i] >= size || y + dj[i] >= size) {
            return true;
        } else if (x + di[i] < 0 || y + dj[i] < 0) {
            return true;
        }
        return false;
    }

    void reset(int size){
        this.board = new Node[size][size];
        this.size = size;
        double node_size_x = 790./ size;
        double node_size_y = 700. / size;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                double x = j*node_size_x;
                double y = i*node_size_y;
                this.board[i][j] = new Node(i, j, x, y, node_size_x, node_size_y);
                if(isBorder(i, j)){ // build the wall
                    this.board[i][j].makeBorder();
                    this.board[i][j].setColor(Color.BLACK);
                }else{
                    this.board[i][j].setColor(Color.WHITE);
                }
            }
        }
        addNeighbours();
    }

    Node getNode(int row, int col){
        return this.board[row][col];
    }

    void addNeighbours(){
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size ; j++) {
                for (int k = 0; k < di.length; k++) {
                    if(!outOfBounds(i, j, k)){
                        board[i][j].adj.add(board[i + di[k]][j + dj[k]]);
                    }
                }
            }
        }
    }

    Node[] getStartAndEnd(){
        int k = 0;
        Node[] tmp = new Node[2];
        for (int i = 0; i < this.size ; i++) {
            for (int j = 0; j < this.size; j++) {
                // assign start and end
                if(getNode(i, j).start){
                    tmp[0] = getNode(i, j);
                    k++;
                } else if (getNode(i, j).end){
                    tmp[1] = getNode(i, j);
                    k++;
                }
                if(k == 2){ break;} // break when both found
            }
        }
        return tmp;
    }

    void resetPath(){
        for (int i = 0; i < this.size ; i++) {
            for (int j = 0; j < this.size; j++) {
                if(this.board[i][j].obstacle){
                    continue;
                }else{
                    this.board[i][j].reset();
                }
            }
        }
    }
    // generates a maze
    void generate(){
        generator.run();
    }

    int allowedDirections(){
        return di.length;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
