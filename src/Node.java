import java.awt.*;
import java.awt.geom.Rectangle2D;
public class Node {
    boolean visited;
    boolean obstacle;
    private final Rectangle2D.Double square;
    private Color my_color;

    public Node(int type,  double x, double y, double sizex, double sizey) {
        visited = false;
        if(type == 0){
            obstacle = false;
            my_color = Color.WHITE;
        }else if (type == 1){
            obstacle = true;
            my_color = Color.BLACK;
        }
        this.square = new Rectangle2D.Double(x,y,sizex,sizey);
    }

    Rectangle2D getSquare(){
        return this.square;
    }
    void setColor(Color c){
        this.my_color = c;
    }
    Color getColor(){
        return this.my_color;
    }

    void reverse(){
        if(this.obstacle){
            this.obstacle = false;
            setColor(Color.WHITE);
        }else if (!this.obstacle){
            this.obstacle = true;
            setColor(Color.BLACK);
        }
    }


}
