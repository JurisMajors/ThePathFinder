import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Node {
    boolean visited;
    boolean obstacle;
    boolean start = false; // path start and end
    boolean end = false;
    public ArrayList<Node> adj;
    private final Rectangle2D.Double square;
    private Color my_color; // color
    private int h; // heuristic
    private int g = -1; // cost to start
    private int worldx, worldy;
    private Node parent;

    public Node(int type,int i,int j, double x, double y, double sizex, double sizey) {
        this.visited = false;
        if(type == 0){
            this.obstacle = false;
            this.my_color = Color.WHITE;
        }else if (type == 1){
            this.obstacle = true;
            this.my_color = Color.BLACK;
        }
        this.square = new Rectangle2D.Double(x,y,sizex,sizey);
        this.adj = new ArrayList<>();
        this.worldx = i;
        this.worldy = j;
    }
    void setStart(){
        if(this.end){
            this.end = false;
        }
        this.start = true;
        setColor(Color.RED);
    }
    void setEnd(){
        if(this.start){
            this.start = false;
        }
        this.end = true;
        setColor(Color.BLUE);
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
        if(this.obstacle || this.start || this.end){
            this.obstacle = false;
            this.start = false;
            this.end = false;
            setColor(Color.WHITE);
        }else {
            this.obstacle = true;
            setColor(Color.BLACK);
        }
    }

    void reset(){
        this.obstacle = false;
        this.start = false;
        this.end = false;
        this.visited = false;
        setColor(Color.WHITE);
    }



    void setHeuristic(int h){
        this.h = h;
    }

    int getHeuristic(){
        return this.h;
    }
    int findMinimumCost(){
        int min = 0;
        for(Node n : adj){
            if(n.g != -1){
                min = Math.min(min, n.g);
            }
        }
        return min;
    }

    // parent for developing the shortest path
    void setParent(Node p){
        this.parent = p;
    }
    Node getParent(){
        return this.parent;
    }

    void setCostToStart(){
        if(this.obstacle){
            this.g = -1;
        }else if(this.start){
            this.g = 0;
        }else{
            this.g = findMinimumCost() + 1;
        }
    }
    int getCost(){
        return this.g;
    }


    int getX(){
        return this.worldx;
    }
    int getY(){
        return this.worldy;
    }



}
