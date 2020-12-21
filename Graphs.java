import java.io.File;
import java.util.*;
import java.lang.Math.*;

public class Graphs {

    private static int matrix[][]; // matrix to stores input from file
    private List<List<Integer>> adj; // adjacency list to store the graph
    private int V;
    private int d[];
    int depth;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("input2.txt"));
        String firstline = sc.nextLine(); // reads the first line and stores in a string
        String[] chars = firstline.split(" "); // splitting the string into an array of chars
        int row = chars.length; // row will be the size 'n' of the matrix which is the number of chars of the
        matrix = new int[row][row];

        for (int i = 0; i < row; i++) {
            matrix[0][i] = Integer.parseInt(chars[i]); // storing the parsed integers inside the first row of matrix
        }
        int index = 1; // represents the line number. we already read line 0 above
        while (sc.hasNextLine()) {
            String nextline = sc.nextLine(); // this reads the second line of the file
            String[] newChars = nextline.split(" "); // storing each char from line inside array
            for (int i = 0; i < row; i++) {
                matrix[index][i] = Integer.parseInt(newChars[i]); // populate matrix with parsed integers
            }
            index++;
        }
        sc.close();

        Graphs graph = new Graphs(matrix.length);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (matrix[i][j] == 1) {
                    graph.addEdge(i, j); //add edge 
                }
            }
        }
        if (graph.cycleConnected()) {
            System.out.println("Does cycle exist?: Yes");//print if there is a cycle exist
        } else {
            System.out.println("Does cycle exist?: No");//print if there is no cycle
        }
        System.out.println("Number of layers in the graph: " + graph.BFS(0));//printing number of layer in graph 
    }

    private void addEdge(int start, int dest) { //method to add edge
        adj.get(start).add(dest);
    }

    public Graphs(int V) 
    {
        this.V = V;
        adj = new ArrayList<>(V);
        d = new int[V];
        for (int i = 0; i < V; i++) {
            adj.add(new LinkedList<>());
            d[i] = 0;
        }
        depth = -1;
    }

    Boolean isCycleConnected(int v, Boolean marked[], int parent) {
        marked[v] = true; // Mark the current node as visited
        Integer i;
        Iterator<Integer> it = adj.get(v).iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!marked[i]) { // check to see if not visited
                if (isCycleConnected(i, marked, v))
                    return true;
            } else if (i != parent) //if vertex is visited and it is not parent of current vertex than there is a cycle
                return true;
        }
        return false;
    }

     Boolean cycleConnected()// check to see if the graph contains cycle
    {
        Boolean marked[] = new Boolean[V]; // mark all vertices as not visited
        for (int i = 0; i < V; i++)
            marked[i] = false;
        for (int u = 0; u < V; u++)
            if (!marked[u])
                if (isCycleConnected(u, marked, -1))
                    return true;

        return false;
    }

    int BFS(int s) {
        boolean marked[] = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();//create queue for bfs
        int[] dist = new int[V];
        Arrays.fill(dist, 0);
        marked[s] = true; //mark all node as visited and enqueue it
        queue.add(s);
        dist[s] = 0;
        while (queue.size() != 0) {
            s = queue.poll(); //dequeue vertex from queue
            Iterator<Integer> i = adj.get(s).listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!marked[n]) {
                    dist[n] = dist[s] + 1;
                    marked[n] = true;
                    queue.add(n);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < dist.length; ++i) {
            max = Math.max(max, dist[i]);//node having maximum distance answer for number of layers
        }
        return max;
    }
}
