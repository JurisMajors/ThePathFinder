import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
public class MazeGenerator {
    World G;
    Random rand = new Random(1);
    MazeGenerator(World G){
        this.G = G;
    }

    boolean getOrientation(int width, int height){
        if(width != height) {
            return width < height;
        }
        return rand.nextInt(2) == 0;
    }

    void divide(int x, int y, int width, int height, boolean orientation){
        if(width <= 2 || height <= 2) return;
        boolean horizontal = orientation;
        //length of wall
        int length = horizontal ? width : height;
        // line point
        int wx = x + (horizontal ? rand.nextInt(height - 2) : 0 );
        int wy = y + (horizontal ? 0 :  rand.nextInt(width - 2));


        // passage point
        int px = wx + (horizontal ? 0 : rand.nextInt(height-1));
        int py = wy + (horizontal ? rand.nextInt(width-1) : 0);
        // what direction
        System.out.println("LINE: "+ wx + " " + wy);
        System.out.println("ENTRANCE: "+ px + " " + py);
        int dx = horizontal ? 0 : 1;
        int dy = horizontal ? 1 : 0;
        for (int i = 0; i < length ; i++) {
            if(wx != px || wy != py) {
                // if the node is not passage make it an obstacle
                this.G.getNode(wx, wy).obstacle = true;
                this.G.getNode(wx, wy).setColor(Color.BLACK);
            }
            wx += dx;
            wy += dy;
        }
        // set new x's and y's
        int nx = x;
        int ny = y;
        int nw = horizontal ? width : wy - y ;
        int nh = horizontal ? wx - x  : height;
        divide(nx, ny, nw, nh, getOrientation(nw, nh));
        nx = horizontal ? wx + 1 : x ;
        ny = horizontal ? y : wy + 1;
        nw = horizontal ? width : y +  width - wy - 1 ;
        nh = horizontal ? x + height - wx - 1 : height ;
        divide(nx, ny, nw, nh, getOrientation(nw, nh));
    }

    void fillGrid(){
        for (int i = 0; i < this.G.size; i++) {
            for (int j = 0; j < this.G.size; j++) {
                this.G.getNode(i, j).obstacle = true;
                this.G.getNode(i, j).setColor(Color.BLACK);
            }
        }


    }

    boolean notEnoughExploration(ArrayList<Node> n, int i){
        Node k = n.get(i);
        int c = 0;
        for(Node a : k.adj){
            if(!a.explored && !a.border){
                c++;
            }
        }
        return c <= 2;

    }

    void prim(){
        fillGrid();
        Node start = this.G.getNode(1 + rand.nextInt(this.G.size-3), 1 + rand.nextInt(this.G.size-3));
        start.setColor(Color.WHITE);
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(start);
        boolean r = false;
        int index = 0;
        while(!nodes.isEmpty()){
            while(notEnoughExploration(nodes, index)){
                nodes.remove(index);
                if(nodes.size() > 0){
                    index = rand.nextInt(nodes.size());
                }else{
                    r = true;
                    break;
                }
            }
            if(r){
                break;
            }
            Node current = nodes.get(index);
            current.explore();
            for(Node n : current.adj){
                if(!n.explored){
                    nodes.add(n);
                }
            }
            nodes.remove(current);
        }

    }

    void run(){
//        divide(1, 1, this.G.size - 1, this.G.size - 1, getOrientation(this.G.size, this.G.size));
        prim();
    }




}
