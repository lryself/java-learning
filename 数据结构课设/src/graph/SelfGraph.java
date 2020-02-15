package graph;

import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import java.util.*;

public class SelfGraph implements mytool {
    Stack<GraphNode> vers=new Stack<>();  //结点的栈储存
    Stack<GraphEdge> arcs=new Stack<>();  //边的栈储存
    int matrix[][];  //邻接矩阵
    PathTransitionSelf pt;  //动画
    Group group;  //图的显示
    int num;

    boolean next=true;
    private static final int MAXNUM=10000;;
    SelfGraph(PathTransitionSelf pt){
        this.pt=pt;
        updatamatrix();
    }
    GraphNode search(double x,double y)    {
        for(int i=0;i<vers.size();i++)
        {
            if(ggdl(vers.get(i).x,vers.get(i).y,x,y)<=10)
            {
                return vers.get(i);
            }
        }
        return null;
    }
    void addNode(double x,double y) {
        GraphNode graphNode=new GraphNode(x, y,String.valueOf(num++));
        vers.add(graphNode);
        updatamatrix();
    }
    void addEdge(GraphNode v1,GraphNode v2,int weight) {
        GraphEdge graphEdge=new GraphEdge(v1,v2,weight);
        arcs.add(graphEdge);
        v1.arcs.add(graphEdge);
        updatamatrix();
    }
    void updatamatrix() {
        matrix=new int[vers.size()][vers.size()];
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix.length;j++)
            {
                matrix[i][j]=MAXNUM;
            }
        }
        for(int i=0;i<matrix.length;i++)
        {
            matrix[i][i]=0;
        }
        for(int i=0;i<arcs.size();i++)
        {
            int w=vers.indexOf(arcs.get(i).u);
            int j=vers.indexOf(arcs.get(i).v);
            matrix[w][j]=arcs.get(i).weight;
        }
    }
    void initColor() {
        for(int i=0;i<vers.size();i++)
        {
            vers.get(i).setCircleFill(Color.WHITE);
        }
        for (int i=0;i<arcs.size();i++)
        {
            arcs.get(i).arrow.setColor(Color.BLACK);
        }
    }
    //图的操作
    GraphEdge findEdge(int i,int k) {
        for(int j=0;j<vers.get(i).arcs.size();j++)
        {
            if(vers.get(i).arcs.get(j).v==(vers.get(k)))
            {
                return vers.get(i).arcs.get(j);
            }
        }
        return null;
    }
    GraphEdge findEdge(GraphNode a,GraphNode b) {
        for(int i=0;i<a.arcs.size();i++)
        {
            if(a.arcs.get(i).v==b)
            {
                return a.arcs.get(i);
            }
        }
        return null;
    }
    GraphEdge findEdgeWeight(int i,int k) {
        for(int j=0;j<vers.get(i).arcs.size();j++)
        {
            if(vers.get(i).arcs.get(j).weight==k)
            {
                return vers.get(i).arcs.get(j);
            }
        }
        return null;
    }
    private boolean isVisited[];
    ArrayList<Integer> showcode(int begin,int last)
    {
        ArrayList<Integer>codelist=new ArrayList<>();
        for(int w=begin-1;w<=last-1;w++)
        {
            codelist.add(w);
        }
        return codelist;
    }
    ArrayList<Integer> showcode(ArrayList<Integer> codelist,int begin,int last)
    {
        for(int w=begin-1;w<=last-1;w++)
        {
            codelist.add(w);
        }
        return codelist;
    }
    void dfs() {
        initColor();
        isVisited = new boolean[vers.size()];
        for(int i = 0 ; i <vers.size();i++) {
            if(!isVisited[i]) {
                pt.addFrame(showcode(5,8));
                dfs(isVisited, i);
            }
        }
    }
    void dfs(boolean[] isVisited,int i) {
        isVisited[i]=true;
        pt.addFrame(showcode(12,14),false);
        pt.addFrame(vers.get(i));
        int w=0,q=0;
        while(vers.get(i).arcs.size()>w)
        {
            pt.addFrame(showcode(15,17),false);
            pt.addFrame(vers.get(i).arcs.get(w));
            q=nextvex(w,i);
            if(!isVisited[q])
            {
                pt.addFrame(showcode(showcode(18,22),27,30),false);
                pt.addFrame(vers.get(q));
                dfs(isVisited, q);
            }
            w++;
            pt.addFrame(showcode(22,25));
        }
    }
    int nextvex(int w,int e) {
        int i= vers.indexOf(vers.get(e).arcs.get(w).v);
        return i;
    }
    void bfs() {
        initColor();
        isVisited = new boolean[vers.size()];
        for(int i = 0 ; i <vers.size();i++) {
            if(!isVisited[i]) {
                pt.addFrame(showcode(4,6));
                bfs(isVisited, i);
            }
        }
    }
    void bfs(boolean[] isVisited,int i) {
        Queue<GraphNode> nodeQueue=new LinkedList<>();
        isVisited[i]=true;
        nodeQueue.offer(vers.get(i));
        pt.addFrame(showcode(11,16),false);
        pt.addFrame(vers.get(i));
        while(!nodeQueue.isEmpty()){
            int w=0,q=0,v=vers.indexOf(nodeQueue.poll());
            while(vers.get(v).arcs.size()>w)
            {
                pt.addFrame(showcode(17,19),false);
                pt.addFrame(vers.get(v).arcs.get(w));
                q=nextvex(w,v);
                if(!isVisited[q])
                {
                    pt.addFrame(showcode(20,23),false);
                    pt.addFrame(vers.get(q));
                    isVisited[q]=true;
                    nodeQueue.offer(vers.get(nextvex(w,v)));
                }
                w++;
                pt.addFrame(showcode(24,28));
            }
        }
    }

    void Prim(){
        int min = MAXNUM;	//定义min变量保存每一个lowcost数组中的最小值，默认为无效权值
        int minId = 0;//定义minId变量保存最小权值的顶点编号
        int sum = 0;//定义sum变量记录总权值
        int vertexSize = vers.size();//顶点个数
        int lowcost[]  = new int[vertexSize];//定义最小代价矩阵
        int adjvex[] = new int[vertexSize];//定义数组保存最小权值的顶点编号
        //lowcost矩阵初始化
        for(int i=0;i<vertexSize;i++) {
            lowcost[i] = matrix[0][i];
        }
        pt.addFrame(showcode(2,11));
        for(int i=1;i<vertexSize;i++) {
            min = Integer.MAX_VALUE;
            minId = 0;
            pt.addFrame(showcode(13,15));
            for(int j=1;j<vertexSize;j++) {
                if(lowcost[j]!=0&&lowcost[j]<min) {//找到lowcost中的最小有效权值
                    min = lowcost[j];//记录最小值
                    minId = j;//记录最小权值的顶点编号
                }
            }
            pt.addFrame(showcode(16,21));
            pt.addFrame(showcode(22,22),false);
            pt.addFrame(vers.get(adjvex[minId]));
            lowcost[minId] = 0;
            sum += min;
            pt.addFrame(showcode(23,25),false);
            pt.addFrame(findEdgeWeight(adjvex[minId],min));
            //System.out.println("连接顶点：" +vers.get(adjvex[minId]).id+" 权值：" +min);
            for(int j=1;j<vertexSize;j++) {
                if(lowcost[j]!=0&&matrix[minId][j]<lowcost[j]) {//在邻接矩阵中以编号为minId的顶点作为下一个顶点对lowcost中进行最小值替换
                    lowcost[j] = matrix[minId][j];
                    adjvex[j] = minId;
                }
            }
            pt.addFrame(showcode(26,31));
        }
        //System.out.println("总权值为：" + sum);
    }

    class Edge1 implements Comparable {
        int a;  //边的一个顶点，从数字0开始
        int b;  //边的另一个顶点
        double weight;  //权重

        Edge1(int a,int b,double weight){
            this.a=a;
            this.b=b;
            this.weight=weight;
        }

        @Override
        public int compareTo(Object o){
            Edge1 m = (Edge1)o;
            int result=(int)(this.weight - m.weight);
            if(result>0) return 1;
            else if(result==0) return 0;
            else return -1;
        }

    }
    int unionsearch(int x ,int[] father){ //查找根结点+路径压缩
        return (x == father[x]) ? x : unionsearch(father[x],father);
    }
    boolean join(int x, int y,int[] father,int []son){ //合并
        int root1, root2;
        root1 = unionsearch(x,father);
        root2 = unionsearch(y,father);
        if(root1 == root2){ //为环
            return false;
        }
        else if(son[root1] >= son[root2]){
            father[root2] = root1;
            son[root1] += son[root2];
        }
        else
        {
            father[root1] = root2;
            son[root2] += son[root1];
        }
        return true;
    }
    double kruskal(){
        int father[];
        int son[];
        int n=vers.size();//结点个数
        int l=arcs.size();//边的数目
        Edge1 e[]=new Edge1[l];
        father=new int[n];
        son=new int[n];
        for(int i = 0; i < n; ++i){
            father[i] = i;//将每个顶点初始化为一个集合，父节点指向自己。
            son[i]=1;//初始化每个父节点的儿子数为1
        }
        for(int i=0,k=0;i<matrix.length;i++) {
            for(int j=0;j<matrix.length;j++) {
                if (matrix[i][j]>0&&matrix[i][j]!=MAXNUM) {
                    e[k] = new Edge1(i,j,matrix[i][j]);
                    k++;
                }
            }
        }
        double sum=0;
        int ltotal=0;
        boolean flag=false;
        Arrays.sort(e); //按权值由小到大排序
        pt.addFrame(showcode(1,16));
        for(int i = 0; i < l; ++i)
        {
            if(join(e[i].a, e[i].b,father,son)==true)
            {
                pt.addFrame(showcode(showcode(37,54),19,19));
                ltotal++; //边数加1
                sum += e[i].weight; //记录权值之和
                pt.addFrame(showcode(29,34),false);
                pt.addFrame(vers.get(e[i].a),false);
                pt.addFrame(vers.get(e[i].b),false);
                pt.addFrame(findEdge(e[i].a,e[i].b));
                System.out.println(e[i].a+"-->"+e[i].b);
            }
            if(ltotal == n - 1) //最小生成树条件：边数=顶点数-1
            {
                flag = true;
                pt.addFrame(showcode(27,31));
                break;
            }
        }
        if(flag) return sum;
        else{
            System.out.println("此图没有最小生成树");
            return -1;
        }
    }

    void dijstra(int[][] matrix, int source) {
        //最短路径长度
        int MaxValue=MAXNUM;
        int[] shortest = new int[matrix.length];
        //判断该点的最短路径是否求出
        int[] visited = new int[matrix.length];
        //存储输出路径
        String[] path = new String[matrix.length];

        //初始化输出路径
        for (int i = 0; i < matrix.length; i++) {
            path[i] = new String(source + "->" + i);
        }

        //初始化源节点
        shortest[source] = 0;
        visited[source] = 1;
        pt.addFrame(showcode(1,15),false);
        pt.addFrame(vers.get(source));
        for (int i = 1; i < matrix.length; i++) {
            int min = Integer.MAX_VALUE;
            int index = -1;

            for (int j = 0; j < matrix.length; j++) {
                //已经求出最短路径的节点不需要再加入计算并判断加入节点后是否存在更短路径
                if (visited[j] == 0 && matrix[source][j] < min) {
                    min = matrix[source][j];
                    index = j;
                }
            }
            pt.addFrame(showcode(18,26));
            //更新最短路径
            shortest[index] = min;
            visited[index] = 1;
            pt.addFrame(showcode(27,40),false);
            pt.addFrame(vers.get(index),false);
            for(int w=0;w<vers.size();w++)
            {
                for(int j=0;j+3<path[w].length();j+=3)
                {
                    if(w==index)
                    {
                        pt.addFrame(findEdge(Integer.parseInt(path[w].charAt(j)+""),Integer.parseInt(path[w].charAt(j+3)+"")));
                    }
                }
            }
            //更新从index跳到其它节点的较短路径
            for (int m = 0; m < matrix.length; m++) {
                if (visited[m] == 0 && matrix[source][index] + matrix[index][m] < matrix[source][m]) {
                    matrix[source][m] = matrix[source][index] + matrix[index][m];
                    path[m] = path[index] + "->" + m;
                }
            }
            pt.addFrame(showcode(41,47));
        }
        //打印最短路径
        for (int i = 0; i < matrix.length; i++) {
            if (i != source) {
                if (shortest[i] == MaxValue) {
                    System.out.println(source + "到" + i + "不可达");
                } else {
                    System.out.println(source + "到" + i + "的最短路径为：" + path[i] + "，最短距离是：" + shortest[i]);
                }
            }
        }
        updatamatrix();
    }

    void floyd(int[][] matrix) {
        int[][] path = new int[matrix.length][matrix.length];
        int MaxValue=MAXNUM;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                path[i][j] = -1;
            }
        }
        pt.addFrame(showcode(2,8));
        for (int m = 0; m < matrix.length; m++) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][m] + matrix[m][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][m] + matrix[m][j];
                        //记录经由哪个点到达
                        path[i][j] = m;
                    }
                }
            }
        }
        pt.addFrame(showcode(9,19));
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i != j) {
                    if (matrix[i][j] == MaxValue) {
                        System.out.println(i + "到" + j + "不可达");
                    } else {
                        System.out.print(i + "到" + j + "的最短路径长度是：" + matrix[i][j]);
                        System.out.print("最短路径为：" + i + "->");
                        pt.addFrame(showcode(22,24),false);
                        pt.addFrame(vers.get(i));
                        findPath(i, j,path);
                        pt.addFrame(showcode(26,28),false);
                        pt.addFrame(vers.get(j));
                        System.out.println(j);
                        /*for(int w=0;w<list.size()-1;w++)
                        {
                            pt.addFrame(findEdge(list.get(w),list.get(w+1)));
                        }*/
                    }
                }
            }
            pt.addFrame(showcode(30,30),false);
            pt.addFrame(2);
        }
    }
    void findPath(int i, int j,int[][] path) {
        int m = path[i][j];
        if (m == -1) {
            pt.addFrame(showcode(showcode(25,25),35,38),false);
            pt.addFrame(findEdge(i,j));
            return;
        }
        pt.addFrame(showcode(showcode(25,25),39,39));
        findPath(i, m,path);
        System.out.print(m + "->");
        pt.addFrame(showcode(showcode(25,25),40,40));
        findPath(m, j,path);
    }

    void  criticalPath() {
        int []topo=topological();
        pt.addFrame(0);
        int []ve=early(topo);
        pt.addFrame(showcode(showcode(41,41),75,89));
        int []vl=late(topo);
        pt.addFrame(showcode(showcode(42,42),57,74));
        System.out.println(Arrays.toString(ve));
        System.out.println(Arrays.toString(vl));
        for(int i=0;i<vers.size();i++) {
            for(int j=0;j<vers.size();j++) {
                if(matrix[i][j]!=MAXNUM&&i!=j) {
                    int ee=ve[i];
                    int el=vl[j]-matrix[i][j];
                    pt.addFrame(showcode(45,47));
                    if(ee==el) {
                        System.out.println("v"+i+" "+"v"+j);
                        pt.addFrame(showcode(48,52),false);
                        pt.addFrame(vers.get(i),false);
                        pt.addFrame(findEdge(i,j));
                        pt.addFrame(vers.get(j),false);
                    }
                }
            }
        }
    }
    int [] late(int []s) {
        int ve[]=early(s);
        int vl[]=new int[vers.size()];
        int n=vers.size();
        for(int i=0;i<vl.length;i++)
            vl[i]=ve[n-1];
        for(int i=n-1;i>=0;i--) {
            int j=s[i];
            for(int k=0;k<n;k++) {
                if(matrix[j][k]!=MAXNUM&&j!=k) {
                    if(vl[j]>(vl[k]-matrix[j][k])) {
                        vl[j]=vl[k]-matrix[j][k];
                    }
                }
            }
        }
        return vl;
    }
    int[] early(int []s) {
        int[]ve=new int[vers.size()];
        for(int i=0;i<vers.size();i++)
            ve[i]=0;
        for(int i=0;i<vers.size();i++) {
            int k=s[i];
            for(int j=0;j<vers.size();j++) {
                if(matrix[k][j]!=MAXNUM) {
                    if(ve[j]<ve[k]+matrix[k][j])
                    {ve[j]=ve[k]+matrix[k][j];}
                    //	System.out.println(e[j]);
                }
            }
        }
        return ve;
    }
    int[] topological() {
        int []r=new int[vers.size()];//各点入度
        int []str=new int[vers.size()];
        int [][]a1=matrix;
        for(int i=0;i<vers.size();i++) {
            int k=-1;
            for(int j=0;j<vers.size();j++) {
                if(a1[j][i]<MAXNUM) {
                    k++;
                }
            }
            r[i]=k;
        }
        Stack<Integer> s=new Stack();
        for(int i=0;i<vers.size();i++) {
            if(r[i]==0) {
                s.push(i);
            }
        }
        int n=0;
        pt.addFrame(showcode(2,20));
        while(!s.isEmpty()) {
            int x=s.pop();
            pt.addFrame(showcode(22,23),false);
            pt.addFrame(vers.get(x));
            pt.addFrame(showcode(24,27),false);
            for(int i=0;i<vers.get(x).arcs.size();i++)
            {
                pt.addFrame(vers.get(x).arcs.get(i),Color.WHITE);
            }
            str[n++]=x;
            for(int i=0;i<vers.size();i++)
                if(matrix[x][i]<MAXNUM&&x!=i)
                {
                    r[i]--;
                    if(r[i]==0)
                        s.push(i);
                }
            pt.addFrame(showcode(28,35));
        }
        return str;
    }
}
class GraphNode{
    private Circle circle=new Circle();  //显示结点
    String id;              //节点内容
    Text idtext=new Text(); //显示内容
    double r=10;    //半径
    double x,y;     //圆心坐标
    Stack<GraphEdge> arcs=new Stack<>();    //边集
    Group group=new Group();
    GraphNode(){}
    GraphNode(double x,double y,String id)
    {
        this.x=x;
        this.y=y;
        this.id=id;
        circle.setRadius(r);
        circle.setCenterX(this.x);
        circle.setCenterY(this.y);
        idtext.setX(this.x-3);
        idtext.setY(this.y+4);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        idtext.setText(id);
        group.getChildren().addAll(circle,idtext);
    }
    Group getCircle()
    {
        return group;
    }
    void setCircleFill(Color color)
    {
        circle.setFill(color);
    }
}
class GraphEdge{
    GraphNode u, v; //头结点，尾结点
    Arrow arrow;  //箭头
    GraphEdge(){}
    double angle;  //角度
    int weight=0;  //权值
    Text text=new Text();  //边的显示
    GraphEdge(GraphNode u, GraphNode v,int weight) {
        this.u = u;
        this.v = v;
        this.weight=weight;
        angle=Math.toDegrees(Math.atan((v.y-u.y)/(v.x-u.x)));
        arrow=new Arrow(u.x,u.y,v.x,v.y,weight);
    }
    Group getArrow()
    {
        return arrow.getArrow();
    }
    Group getLine()
    {
        return arrow.getLine();
    }
}
class Arrow{
    Line line;
    double arcsize=20;
    Arc arc=new Arc();
    Group group=new Group();
    double rod=30;
    Text text=new Text();
    Arrow(){}
    Arrow(double x1,double y1,double x2,double y2,int weight)    {
        line=new Line(x1,y1,x2,y2);
        arc.centerXProperty().bind(line.endXProperty());
        arc.centerYProperty().bind(line.endYProperty());
        arc.fillProperty().bind(line.fillProperty());
        arc.setLength(rod);
        arc.setType(ArcType.ROUND);
        arc.setRadiusX(arcsize);
        arc.setRadiusY(arcsize);
        text.setX((x1+x2)/2);
        text.setY((y1+y2)/2);
        line.setStrokeWidth(2);
        setWeight(weight);
        upArcStartAngle();
        group.getChildren().addAll(line,arc,text);
    }
    Group getArrow()
    {
        return group;
    }
    Group getLine() {
        group.getChildren().remove(arc);
        return group;
    }
    void upArcStartAngle()    {
        arc.setStartAngle(Math.toDegrees(Math.atan2((line.getEndY()-line.getStartY()),-(line.getEndX()-line.getStartX())))-rod/2);
    }
    void setWeight(int weight)
    {
        text.setText(weight+"");
    }
    void setColor(Color color)
    {
        line.setStroke(color);
        line.setFill(color);
    }
}
