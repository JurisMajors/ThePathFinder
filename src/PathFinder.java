
public class PathFinder {
    World G;
    int n;
    Node start, end;
    String algorithm;

    PathFinder(World graph, String type){
        this.G = graph;
        this.n = G.board[0].length;
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
        int k = 0;
        for (int i = 0; i < this.n ; i++) {
            for (int j = 0; j < this.n; j++) {
                // assign start and end
                if(G.getNode(i, j).start){
                    start = G.getNode(i, j);
                    k++;
                } else if (G.getNode(i, j).end){
                    end = G.getNode(i, j);
                    k++;
                }
                if(k == 2){ break;} // break when both found
            }
        }
    }

    double nodeHeuristic(Node n){
        int dx = Math.abs(n.getX() - end.getX());
        int dy = Math.abs(n.getY() - end.getY());
        int diagonal_steps = Math.min(dx, dy);
        int straight_steps = Math.max(dx, dy) - diagonal_steps;
        return Math.sqrt(2)*diagonal_steps + straight_steps;
    }

    void calculateAttributes(){
        for (int i = 0; i < this.n ; i++) {
            for (int j = 0; j < this.n; j++) {
                Node n = G.getNode(i, j);
                n.setHeuristic(nodeHeuristic(n));
                n.setCostToStart();
            }
        }
    }

    void doAStar(){
        System.out.println("Doing A*");
        calculateAttributes();



    }




    void doDjikstra(){
        System.out.println("Doing Djikstra");
    }

}
