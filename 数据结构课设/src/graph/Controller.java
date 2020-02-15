package graph;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.*;
import java.io.*;
import java.util.*;
import javafx.scene.layout.*;


public class Controller implements mytool {
    public Button df_traver;
    public Button bf_traver;
    public Button prim;
    public Button kruskal;
    public Button initGraph;
    public Button dijkstra;
    public Button floyd;
    public Button critical;
    public TextArea graphSave;
    public RadioButton AlGraph;
    public RadioButton MGraph;
    public CheckMenuItem isallItem;
    public GridPane codePane;
    @FXML CheckBox createGraph;
    @FXML Group graphGroup;
    @FXML ImageView backgroud;

    PathTransitionSelf pt=new PathTransitionSelf(this);
    SelfGraph graph=new SelfGraph(pt);
    ImageView centerView=new ImageView("image/backgroud.png");
    double startx,starty,endx,endy;
    GraphNode startNode=null;
    Arrow tempArrow;
    Scanner input;
    boolean canpaint;
    int model=0;
    Stack<Text> codetexts=new Stack<>();

    public void handlecreate(ActionEvent actionEvent) {
        canpaint=createGraph.isSelected();
        if(canpaint)
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("选择");
            alert.setHeaderText("请选择您要创建的图的类型");
            ButtonType buttonTypeOne = new ButtonType("有向图");
            ButtonType buttonTypeTwo = new ButtonType("无向图");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                model=1;
            } else if (result.get() == buttonTypeTwo) {
                model=2;
            } else {
                model=0;
                canpaint=false;
                createGraph.setSelected(false);
            }
            df_traver.setDisable(true);
            bf_traver.setDisable(true);
            prim.setDisable(true);
            kruskal.setDisable(true);
            initGraph.setDisable(true);
            dijkstra.setDisable(true);
            floyd.setDisable(true);
            critical.setDisable(true);
        }
        else{
            df_traver.setDisable(false);
            bf_traver.setDisable(false);
            prim.setDisable(false);
            kruskal.setDisable(false);
            initGraph.setDisable(false);
            dijkstra.setDisable(false);
            floyd.setDisable(false);
            critical.setDisable(false);
        }
    }

    public void handledf_traver(ActionEvent actionEvent) {
        pt.stop();
        graph.dfs();
        pt.play();
        readcode("dfs.txt");
    }

    public void handlebf_traver(ActionEvent actionEvent) {
        pt.stop();
        graph.bfs();
        pt.play();
        readcode("bfs.txt");
    }

    public void handleprim(ActionEvent actionEvent) {
        pt.stop();
        pt.setFather(this);
        graph.Prim();
        readcode("prim.txt");
        pt.play();
    }

    public void handlekruskal(ActionEvent actionEvent) {
        pt.stop();
        graph.kruskal();
        readcode("kruskal.txt");
        updataGroup();
        pt.setFather(this);
        pt.play();
    }

    public void handledijkstra(ActionEvent actionEvent) {
        pt.stop();
        pt.setFather(this);
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("请输入");
        dialog.setHeaderText("请输入原点名称");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            int choose=Integer.parseInt(result.get());
            graph.dijstra(graph.matrix,choose);
        }
        readcode("dijstra.txt");
        pt.play();
    }

    public void handlefloyd(ActionEvent actionEvent) {
        pt.stop();
        pt.setFather(this);
        graph.floyd(graph.matrix);
        readcode("flord.txt");
        pt.play();
    }

    public void handlecritical(ActionEvent actionEvent) {
        pt.stop();
        updataGroup();
        pt.setFather(this);
        graph.criticalPath();
        readcode("criticalPath.txt");
        pt.play();
    }

    public void handleclearGraph(ActionEvent actionEvent) {
        pt.stop();
        updataGroup();
    }

    public void setOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void setOnMouseDragged(MouseEvent mouseEvent) {
        if(canpaint)
        {
            tempArrow.line.setEndX(mouseEvent.getSceneX()-200);
            tempArrow.line.setEndY(mouseEvent.getSceneY()-25);
            tempArrow.arc.setLength(30);
            tempArrow.upArcStartAngle();
        }
    }

    public void setOnMousePressed(MouseEvent mouseEvent) {
        if(canpaint)
        {
            startx=mouseEvent.getSceneX()-200;
            starty=mouseEvent.getSceneY()-25;
            startNode=graph.search(startx,starty);
            tempArrow=new Arrow(startx,starty,startx,starty,0);
            tempArrow.arc.setLength(0);
            if(model==1)
                graphGroup.getChildren().add(tempArrow.getArrow());
            if(model==2)
                graphGroup.getChildren().add(tempArrow.getLine());
        }
    }

    public void setOnMouseReleased(MouseEvent mouseEvent) {
        if(canpaint)
        {
            graphGroup.getChildren().remove(tempArrow.getArrow());
            graphGroup.getChildren().remove(tempArrow.getLine());
            tempArrow=null;
            endx=mouseEvent.getSceneX()-200;
            endy=mouseEvent.getSceneY()-25;
            if(ggdl(startx,starty,endx,endy)<=10)
            {
                if(mouseEvent.getButton()== MouseButton.PRIMARY)
                {
                    graph.addNode(startx,starty);
                    graphGroup.getChildren().add(graph.vers.peek().getCircle());
                }
                if(mouseEvent.getButton()==MouseButton.SECONDARY)
                {
                    graphGroup.getChildren().remove(startNode.getCircle());
                    graph.vers.remove(startNode);
                    for(int i=0;i<graph.arcs.size();i++)
                    {
                        if(graph.arcs.get(i).u==startNode||graph.arcs.get(i).v==startNode)
                        {
                            graphGroup.getChildren().remove(graph.arcs.get(i).getArrow());
                            graph.arcs.remove(i--);
                        }
                    }
                }
            }
            if(ggdl(startx,starty,endx,endy)>10)
            {
                if(startNode==null)
                    return;
                GraphNode endNode;
                if((endNode=graph.search(endx,endy))==null)
                {
                    return;
                }
                int weight=0;
                TextInputDialog dialog = new TextInputDialog("0");
                dialog.setTitle("请输入");
                dialog.setHeaderText("请输入这条边的权值");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    weight=Integer.parseInt(result.get());
                }
                graph.addEdge(startNode,endNode,weight);
                if(model==2)
                {
                    graph.addEdge(endNode,startNode,weight);
                    graph.findEdge(startNode,endNode).arrow.line.fillProperty().bind(graph.findEdge(endNode,startNode).arrow.line.fillProperty());
                    graph.findEdge(endNode,startNode).arrow.line.fillProperty().bind(graph.findEdge(startNode,endNode).arrow.line.fillProperty());
                }
                updataGroup();
            }
            printgraphStruct();
        }
    }

    void readcode(String file) {
        codetexts.clear();
        codePane.getChildren().clear();
        try
        {
            input=new Scanner(new File("F:/编程/java/数据结构课设/src/text/code/"+file));
            while(input.hasNext())
            {
                codetexts.add(new Text(input.nextLine()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for(int i=0;i<codetexts.size();i++)
        {
            codePane.add(codetexts.get(i),0,i);
        }
    }

    void updataGroup() {
        graph.initColor();
        graphGroup.getChildren().clear();
        graph.updatamatrix();
        for(int i=0;i<graph.vers.size();i++)
        {
            graphGroup.getChildren().add(graph.vers.get(i).getCircle());
        }
        for(int i=0;i<graph.vers.size();i++){
            int j=0;
            if(model==2)
                j=i;
            for(;j<graph.vers.size();j++)
            {
                if(i!=j&&graph.matrix[i][j]!=10000)
                {
                    if(model==1)
                        graphGroup.getChildren().add(graph.findEdge(i,j).getArrow());
                    if(model==2)
                        graphGroup.getChildren().add(graph.findEdge(i,j).getLine());
                }
            }
        }
        try
        {
            graphGroup.getChildren().add(pt.getNode());
        }
        catch (Exception e)
        {}
    }

    public void openitemAction(ActionEvent actionEvent) {
        graphGroup.getChildren().clear();
        graph.vers.clear();
        graph.arcs.clear();
        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File("F:/编程/java/数据结构课设/src"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file=fileChooser.showOpenDialog(null);
        try
        {
            DataInputStream inputStream=new DataInputStream(new FileInputStream(file));
            model=inputStream.readInt();
            int n=inputStream.readInt();
            for(int i=0;i<n;i++)
            {
                graph.addNode(inputStream.readDouble(),inputStream.readDouble());
                graph.vers.peek().id=inputStream.readUTF();
                graph.vers.peek().idtext.setText(graph.vers.peek().id);
                graphGroup.getChildren().add(graph.vers.peek().getCircle());
            }
            n=inputStream.readInt();
            for(int i=0;i<n;i++)
            {
                GraphNode firstnode=null,lastnode=null;
                double x=inputStream.readDouble();
                double y=inputStream.readDouble();
                for(int j=0;j<graph.vers.size();j++)
                {
                    if(graph.vers.get(j).x==x&&graph.vers.get(j).y==y)
                    {
                        firstnode=graph.vers.get(j);
                        break;
                    }
                }
                x=inputStream.readDouble();
                y=inputStream.readDouble();
                for(int j=0;j<graph.vers.size();j++)
                {
                    if(graph.vers.get(j).x==x&&graph.vers.get(j).y==y)
                    {
                        lastnode=graph.vers.get(j);
                        break;
                    }
                }
                int weight=inputStream.readInt();
                graph.addEdge(firstnode,lastnode,weight);
            }
            updataGroup();
            graph.updatamatrix();
            printgraphStruct();
            canpaint=false;
            createGraph.setSelected(false);
            df_traver.setDisable(false);
            bf_traver.setDisable(false);
            prim.setDisable(false);
            kruskal.setDisable(false);
            initGraph.setDisable(false);
            dijkstra.setDisable(false);
            floyd.setDisable(false);
            critical.setDisable(false);
            try
            {
                graphGroup.getChildren().add(pt.getNode());
            }
            catch (Exception e)
            {}
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

    public void saveitemAction(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file=fileChooser.showSaveDialog(null);
        try
        {
            DataOutputStream outputStream=new DataOutputStream(new FileOutputStream(file));
            outputStream.writeInt(model);
            outputStream.writeInt(graph.vers.size());
            for(int i=0;i<graph.vers.size();i++)
            {
                outputStream.writeDouble(graph.vers.get(i).x);
                outputStream.writeDouble(graph.vers.get(i).y);
                outputStream.writeUTF(graph.vers.get(i).id);
            }
            outputStream.writeInt(graph.arcs.size());
            for(int i=0;i<graph.arcs.size();i++)
            {
                outputStream.writeDouble(graph.arcs.get(i).u.x);
                outputStream.writeDouble(graph.arcs.get(i).u.y);
                outputStream.writeDouble(graph.arcs.get(i).v.x);
                outputStream.writeDouble(graph.arcs.get(i).v.y);
                outputStream.writeInt(graph.arcs.get(i).weight);
            }
            new Alert(Alert.AlertType.INFORMATION,"保存成功").showAndWait();
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

    public void printAlGraph(ActionEvent actionEvent) {
        printgraphStruct();
    }

    public void printMgraph(ActionEvent actionEvent) {
        printgraphStruct();
    }

    void printgraphStruct() {
        if(AlGraph.isSelected())
        {
            graphSave.setText("");
            for(int i=0;i<graph.vers.size();i++)
            {
                graphSave.appendText(graph.vers.get(i).id+"->");
                for(int j=0;j<graph.vers.get(i).arcs.size();j++)
                {
                    graphSave.appendText(graph.vers.get(i).arcs.get(j).v.id+"/"+graph.vers.get(i).arcs.get(j).weight+",");
                }
                graphSave.appendText("\n");
            }
        }
        if(MGraph.isSelected())
        {
            graphSave.setText("");
            for(int i=0;i<graph.vers.size();i++)
            {
                for(int j=0;j<graph.vers.size();j++)
                {
                    graphSave.appendText(graph.matrix[i][j]+" ");
                }
                graphSave.appendText("\n");
            }
        }
    }

    public void playItem(ActionEvent actionEvent) {
        pt.play();
    }

    public void pauseItem(ActionEvent actionEvent) {
        pt.pause();
    }

    public void palyAllItem(ActionEvent actionEvent) {
        pt.playall();
    }

    public void anispeedItem(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog("1000");
        dialog.setTitle("设置");
        dialog.setHeaderText("设置动画间隔时间");
        dialog.setContentText("单位：毫秒:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            pt.setSpeed(Integer.parseInt(result.get()));
        }
    }
}