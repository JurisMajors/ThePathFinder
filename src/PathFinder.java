import java.awt.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PathFinder {
    World G;
    Node start, end;
    String algorithm;
    Comparator<Node> nodeComparatorAStar = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) { // comparison which compares nodes by their f-cost.
            return o1.getHeuristic() + o1.getCost() - o2.getHeuristic() - o2.getCost();
        }
    };


    PathFinder(World graph, String type){
        this.G = graph;
        setAlgorithm(type);
    }

    void setAlgorithm(String type){
        this.algorithm = type;
    }

    void run(){
        getStartAndEnd();
        System.out.println("HALLO");
        if(this.algorithm.equals("A*")){
            doAStar();
        }else if(this.algorithm.equals("Djikstra")){
            doDjikstra();
        }
    }

    void getStartAndEnd(){
        Node[] tmp = G.getStartAndEnd();
        start = tmp[0];
        end = tmp[1];
    }

    int nodeHeuristic(Node n){ // h-cost
        int dx = Math.abs(n.getX() - end.getX());
        int dy = Math.abs(n.getY() - end.getY());
        int diagonal_steps = Math.min(dx, dy);
        int straight_steps = Math.max(dx, dy) - diagonal_steps;
        return 14*diagonal_steps + 10*straight_steps;
    }

    void calculateAttributes(){ // calculates h-cost and g-cost for all nodes
        //TODO: optimization by calculating only in specific area
        for (int i = 0; i < this.G.size ; i++) {
            for (int j = 0; j < this.G.size; j++) {
                Node n = G.getNode(i, j);
                if(n.obstacle){
                    n.setHeuristic(-1);
                }else{
                    n.setHeuristic(nodeHeuristic(n));
                }
                n.setCostToStart();
            }
        }
    }

    void doAStar(){
        System.out.println("Doing A*");
        calculateAttributes();
        PriorityQueue<Node> OPEN = new PriorityQueue<>(nodeComparatorAStar); // setup the min-heap which compares f-cost
        OPEN.add(start);
        while(!OPEN.isEmpty()){
            Node current = OPEN.poll();
            current.visited = true;
            if(current.equals(end)){
                break; // if end is found break out of loop
            }
            for(Node neighbour : current.adj){ // for each neighbour
                if(neighbour.obstacle || neighbour.visited){ // skip if unusable
                    continue;
                }
                if(!OPEN.contains(neighbour)){ // if havent expanded yet, put in queue to expand
                    OPEN.add(neighbour);
                    neighbour.setColor(Color.GRAY);
                    neighbour.setParent(current); // set parent for later determining the shortest path
                }
            }
        }
        end.setColor(Color.BLUE);
        // visualize the path
        Node x = end.getParent();
        while(!x.equals(start)){
            x.setColor(Color.GREEN);
            G.repaint();
            x = x.getParent();
        }

    }



    void doDjikstra(){
        System.out.println("Doing Djikstra");
    }

}
