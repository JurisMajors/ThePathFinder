import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PathFinder {
    World G;
    Node start, end;
    String algorithm;
    Comparator<Node> nodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
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

    int nodeHeuristic(Node n){
        int dx = Math.abs(n.getX() - end.getX());
        int dy = Math.abs(n.getY() - end.getY());
        int diagonal_steps = Math.min(dx, dy);
        int straight_steps = Math.max(dx, dy) - diagonal_steps;
        return 14*diagonal_steps + 10*straight_steps;
    }

    void calculateAttributes(){
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
        PriorityQueue<Node> OPEN = new PriorityQueue<>(nodeComparator);
        OPEN.add(start);
        while(!OPEN.isEmpty()){
            Node current = OPEN.poll();
            current.visited = true;
            G.repaint();
            if(current.equals(end)){
                return;
            }
            for(Node neighbour : current.adj){
                if(neighbour.obstacle || neighbour.visited){
                    continue;
                }

                if(!OPEN.contains(neighbour)){
                    OPEN.add(neighbour);
                }
            }

        }
    }



    void doDjikstra(){
        System.out.println("Doing Djikstra");
    }

}
