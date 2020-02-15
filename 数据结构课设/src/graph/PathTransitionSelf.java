package graph;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.*;

public class PathTransitionSelf
{
    private ImageView imageView=new ImageView("image/fly.png");
    private PathTransition pt=new PathTransition();
    private Timeline ani=new Timeline();
    private Queue<Frameani> frameQueue=new LinkedList<>();
    int m,n;
    Controller father;
    boolean isall=true;
    int speed=1000;
    Stack<Arrow> arrows=new Stack<>();
    PathTransitionSelf(Controller father) {
        this.father=father;
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        pt.setNode(imageView);
        pt.setOrientation(PathTransition.OrientationType.NONE);
        pt.setDuration(Duration.millis(speed));
        pt.setCycleCount(1);
    }
    void play(){
        m=0;n=0;
        father.updataGroup();
        father.graph.initColor();
        EventHandler<ActionEvent> eventEventHandler=e->{
            Frameani frameani=frameQueue.poll();;
            try
            {
                if(frameani.variety==0)
                {
                    textani(frameani.codes);
                }
                else if(frameani.variety==1)
                {
                    lineani(frameani.edge);
                }
                else if(frameani.variety==2)
                {
                    nodeani(frameani.node);
                }
                else if(frameani.variety==3)
                {
                    if(frameani.commond==0)
                    {
                        father.updataGroup();
                    }
                    if(frameani.commond==1)
                    {
                        father.graph.initColor();
                    }
                    if(frameani.commond==2)
                    {
                        for(int q=0;q<arrows.size();q++)
                        {
                            father.graph.initColor();
                            arrows.get(q).setColor(Color.BLUE);
                        }
                    }
                }
                else if(frameani.variety==4)
                {
                    arrows.push(new Arrow(frameani.edge.u.x,frameani.edge.u.y,frameani.edge.v.x,frameani.edge.v.y,frameani.edge.weight));
                    arrows.peek().setColor(frameani.color);
                    if(father.model==1)
                        father.graphGroup.getChildren().add(arrows.peek().getArrow());
                    if(father.model==2)
                        father.graphGroup.getChildren().add(arrows.peek().getLine());
                    pt.setPath(frameani.edge.arrow.line);
                    pt.play();
                }
                if(!isall&&frameani.ifpause)
                    pause();
            }
            catch (NullPointerException e1)
            {
                stop();
            }
        };
        ani=new Timeline(new KeyFrame(Duration.millis(speed+50),eventEventHandler));
        ani.setCycleCount(Timeline.INDEFINITE);
        ani.play();
    }
    void pause()
    {
        ani.pause();
    }
    void stop() {
        ani.stop();
        clearframe();
    }
    void playall(){
        isall=father.isallItem.isSelected();
    }
    void setSpeed(int s)
    {
        speed=s;
    }
    void addFrame(int a) {
        frameQueue.offer(new Frameani(a));
    }
    void addFrame(ArrayList a) {
        frameQueue.offer(new Frameani(a));
        frameQueue.offer(new Frameani(a));
    }
    void addFrame(ArrayList a,boolean ispause) {
        Frameani frameani=new Frameani(a);
        frameani.ifpause=ispause;
        frameQueue.offer(frameani);
    }
    void addFrame(GraphEdge a) {
        frameQueue.offer(new Frameani(a));
    }
    void addFrame(GraphEdge a,boolean ispause) {
        Frameani frameani=new Frameani(a);
        frameani.ifpause=ispause;
        frameQueue.offer(frameani);
    }
    void addFrame(GraphEdge a,Color color) {
        Frameani frameani=new Frameani(a,color);
        frameQueue.offer(frameani);
    }
    void addFrame(GraphNode a) {
        frameQueue.offer(new Frameani(a));
    }
    void addFrame(GraphNode a,boolean ispause) {
        Frameani frameani=new Frameani(a);
        frameani.ifpause=ispause;
        frameQueue.offer(frameani);
    }
    void clearframe() {
        frameQueue.clear();
    }
    void setFather(Controller father)
    {
        this.father=father;
    }
    ImageView getNode()
    {
        return imageView;
    }

    void textani(ArrayList<Integer> a)
    {
        for(int i=0;i<father.codetexts.size();i++)
        {
            father.codetexts.get(i).setFill(Color.BLACK);
        }
        for(int i=0;i<a.size();i++)
        {
            father.codetexts.get(a.get(i)).setFill(Color.BLUE);
        }
    }
    void lineani(GraphEdge edge)
    {
        arrows.push(new Arrow(edge.u.x,edge.u.y,edge.v.x,edge.v.y,edge.weight));
        arrows.peek().setColor(Color.RED);
        if(father.model==1)
            father.graphGroup.getChildren().add(arrows.peek().getArrow());
        if(father.model==2)
            father.graphGroup.getChildren().add(arrows.peek().getLine());
        pt.setPath(edge.arrow.line);
        pt.play();
    }
    void nodeani(GraphNode node)
    {
        node.setCircleFill(Color.YELLOW);
    }
}
class Frameani
{
    int variety;//0为text动画，1为线动画，2为点动画,3为其他操作,4为拓扑排序
    ArrayList<Integer> codes=new ArrayList<>();
    GraphEdge edge;
    GraphNode node;
    Color color;
    boolean ifpause=true;
    int commond;//0为刷新界面,1为清空颜色
    Frameani(GraphNode graphNode)
    {
        variety=2;
        node=graphNode;
    }
    Frameani(GraphEdge graphEdge)
    {
        variety=1;
        edge=graphEdge;
    }
    Frameani(GraphEdge graphEdge,Color color)
    {
        variety=4;
        edge=graphEdge;
        this.color=color;
    }
    Frameani(ArrayList<Integer> a)
    {
        variety=0;
        for(int i=0;i<a.size();i++)
        {
            codes.add(a.get(i));
        }
    }
    Frameani(int commond)
    {
        variety=3;
        this.commond=commond;
    }
}