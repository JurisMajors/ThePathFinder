import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
public class MazeGenerator {
    World G;
    Random rand = new Random(1);
    MazeGenerator(World G){
        this.G = G;
    }

    void fillGrid(){ // fill grid with obstacles
        for (int i = 0; i < this.G.size; i++) {
            for (int j = 0; j < this.G.size; j++) {
                this.G.getNode(i, j).obstacle = true;
                this.G.getNode(i, j).setColor(Color.BLACK);
            }
        }
    }

    boolean notEnoughExploration(ArrayList<Node> n, int i){ // check if still need to explore
        Node k = n.get(i);
        int c = 0;
        for(Node a : k.adj){
            if(!a.explored && !a.border){
                c++;
            }
        }
        return c <= this.G.allowedDirections()/2;

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
        prim();
    }




}
