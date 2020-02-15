package Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.Stack;
import Message.*;
//黑色是1，白色是2

public class Client extends Application{
    BorderPane mainPane=new BorderPane();
    Board board=new Board();
    StackPane boardPane=new StackPane();
    StackPane rightPane=new StackPane();
    ImageView imageViewboard=new ImageView("image/board.png");
    Stack<Button>buttons=new Stack<>();
    Stack<Label>labels=new Stack<>();
    GridPane buttonPane=new GridPane();
    ComboBox<String>modelBox=new ComboBox<>();
    TextField iptext=new TextField();
    TextField myporttext=new TextField();
    TextField yournametext=new TextField();
    TextField roomtext=new TextField();
    TextArea chattext=new TextArea();
    ScrollPane scrollPane=new ScrollPane(chattext);
    Connect connect;
    int yours;
    final int rownum=15;
    boolean isstart=false;
    int turnwho=1;
    int model=0;    // 0是单机游戏，1是人机对战，2是人人对战

    void initmainPane()
    {
        mainPane.setCenter(boardPane);
        imageViewboard.setFitHeight(600);
        imageViewboard.setFitWidth(600);
        boardPane.getChildren().addAll(imageViewboard,new Pane(board.group));
        mainPane.setRight(rightPane);
        rightPane.setPrefSize(300,600);
        ImageView rightGroud=new ImageView("image/pretty.png");
        rightGroud.setFitHeight(600);
        rightGroud.setFitWidth(300);
        rightPane.getChildren().add(rightGroud);
        rightPane.getChildren().add(buttonPane);
        mainPane.setLeft(scrollPane);
        chattext.setPrefWidth(200);
        chattext.setPrefHeight(600);
        initbuttonPane();
    }
    void initbuttonPane()
    {
        labels.add(new Label("游戏模式："));
        labels.add(new Label("服务器IP："));
        labels.add(new Label("连接端口："));
        labels.add(new Label("您的昵称："));
        labels.add(new Label("房间名称："));
        for(int i=0;i<5;i++)
        {
            buttonPane.add(labels.get(i),0,i);
        }
        buttonPane.add(modelBox,1,0);
        modelBox.getItems().addAll("单机对战","人机对战","人人对战");
        buttonPane.add(iptext,1,1);
        iptext.setText("127.0.0.1");
        buttonPane.add(myporttext,1,2);
        myporttext.setText("8189");
        buttonPane.add(yournametext,1,3);
        yournametext.setText("player");
        buttonPane.add(roomtext,1,4);
        roomtext.setText("随机匹配");
        buttons.add(new Button("修改设置"));
        buttons.add(new Button("保存设置"));
        buttons.add(new Button("开始游戏"));
        buttons.add(new Button("发送消息"));
        buttons.add(new Button("请求悔棋"));
        buttons.add(new Button("发起投降"));
        buttons.add(new Button("保存棋局"));
        buttons.add(new Button("读取棋局"));
        for(int i=5,k=0;i<9;i++)
        {
            for(int j=0;j<2;j++)
            {
                buttonPane.add(buttons.get(k),j,i);
                buttonPane.setHalignment(buttons.get(k++), HPos.CENTER);
            }
        }
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setPadding(new Insets(30));
        buttonPane.setVgap(10);
        modelBox.setValue("单机对战");
        iptext.setDisable(true);
        myporttext.setDisable(true);
        yournametext.setDisable(true);
        roomtext.setDisable(true);
        buttons.get(0).setDisable(true);
        buttons.get(1).setDisable(true);
        buttons.get(3).setDisable(true);
        buttons.get(4).setDisable(true);
        buttons.get(5).setDisable(true);
        initbutonAction();
    }
    void initbutonAction()
    {
        modelBox.setOnAction(e->{
            if(modelBox.getValue().equals("人人对战"))
            {
                iptext.setDisable(false);
                myporttext.setDisable(false);
                yournametext.setDisable(false);
                roomtext.setDisable(false);
                buttons.get(0).setDisable(false);
                buttons.get(1).setDisable(false);
                buttons.get(2).setDisable(true);
                model=2;
            }
            else
            {
                iptext.setDisable(true);
                myporttext.setDisable(true);
                yournametext.setDisable(true);
                roomtext.setDisable(true);
                buttons.get(0).setDisable(true);
                buttons.get(1).setDisable(true);
                buttons.get(3).setDisable(true);
                buttons.get(2).setDisable(false);
            }
            if(modelBox.getValue().equals("人机对战"))
            {
                model=1;
            }
            if(modelBox.getValue().equals("单机对战"))
            {
                model=0;
            }
        });
        buttons.get(0).setOnAction(e->{
            iptext.setDisable(false);
            myporttext.setDisable(false);
            yournametext.setDisable(false);
            roomtext.setDisable(false);
        });
        buttons.get(1).setOnAction(e->{
            connect=new Connect(this);
            if(connect.setConnect(iptext.getText(),myporttext.getText(),yournametext.getText(),roomtext.getText()))
            {
                iptext.setDisable(true);
                myporttext.setDisable(true);
                yournametext.setDisable(true);
                roomtext.setDisable(true);
            }
            else
            {
                new Alert(Alert.AlertType.ERROR,"连接失败，请检查你的网络设置").showAndWait();
            }
        });
        buttons.get(2).setOnAction(e->{
            board.clear();
            buttons.get(4).setDisable(false);
            if(model==0)
            {
                new Alert(Alert.AlertType.INFORMATION,"对局开始").show();
                isstart=true;
                boardPane.setOnMousePressed(e1->{
                    if(isstart==false)//判断游戏开始
                        return;
                    int x=(int)e1.getX()/40;
                    int y=(int)e1.getY()/40;
                    board.addCheer(x,y,turnwho);//通过获得的棋子落点添加棋子
                    int winer=board.whowin();//获得赢家
                    if(winer==1)
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("获胜方是：黑色");
                        alert.showAndWait();
                        isstart=false;
                    }
                    if(winer==2)
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("获胜方是：白色");
                        alert.showAndWait();
                        isstart=false;
                    }
                    if(isstart==false)
                    {
                        buttons.get(4).setDisable(true);
                        buttons.get(5).setDisable(true);
                    }
                    if(turnwho==1)//切换下子人
                    {
                        turnwho=2;
                        chattext.appendText("轮到白棋了\n");
                    }
                    else
                    {
                        turnwho=1;
                        chattext.appendText("轮到黑棋了\n");
                    }
                });
            }
            if(model==1)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("先手选择");
                alert.setHeaderText("请选择你的棋子颜色：");
                alert.setContentText("黑色先手，白色后手");
                ButtonType buttonTypeOne = new ButtonType("黑色");
                ButtonType buttonTypeTwo = new ButtonType("白色");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    yours=1;
                } else if (result.get() == buttonTypeTwo) {
                    yours=2;
                    board.addCheer(7,7,1);
                } else {
                    isstart=false;
                    return;
                }
                new Alert(Alert.AlertType.INFORMATION,"对局开始").show();
                isstart=true;
                buttons.get(4).setDisable(false);
                buttons.get(5).setDisable(false);
                AI ai=new AI(yours);
                boardPane.setOnMousePressed(e1->{
                    turnwho=yours;
                    if(isstart==false)//判断是否开始
                        return;
                    int x=(int)e1.getX()/40;
                    int y=(int)e1.getY()/40;
                    board.addCheer(x,y,turnwho);//通过点击的棋子落点添加棋子
                    int winer=board.whowin();//判断获胜者
                    if(winer==yours)
                    {
                        Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("提示");
                        alert1.setHeaderText("您获胜了！");
                        alert1.showAndWait();
                        isstart=false;
                    }
                    else if(winer==0)
                    {}
                    else
                    {
                        Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("提示");
                        alert1.setHeaderText("您被打败了！");
                        alert1.showAndWait();
                        isstart=false;
                    }
                    if(isstart==false)
                    {
                        buttons.get(4).setDisable(true);
                        buttons.get(5).setDisable(true);
                        return;
                    }
                    board.addCheer(ai.getAICheer());
                    winer=board.whowin();
                    if(winer==yours)
                    {
                        Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("提示");
                        alert1.setHeaderText("您获胜了！");
                        alert1.showAndWait();
                        isstart=false;
                    }
                    else if(winer==0)
                    {}
                    else
                    {
                        Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("提示");
                        alert1.setHeaderText("您被打败了！");
                        alert1.showAndWait();
                        isstart=false;
                    }
                    if(isstart==false)
                    {
                        buttons.get(4).setDisable(true);
                        buttons.get(5).setDisable(true);
                    }
                });
            }
            if(model==2)
            {
                buttons.get(4).setDisable(true);
                connect.send(new Message(1,"开始"));
                new Alert(Alert.AlertType.INFORMATION,"开始请求已发出，等待对方确认").show();
                turnwho=1;
                boardPane.setOnMousePressed(e1->{
                    if(isstart==false)
                        return;
                    if(turnwho!=yours)
                        return;
                    int x=(int)e1.getX()/40;
                    int y=(int)e1.getY()/40;
                    board.addCheer(x,y,turnwho);
                    connect.send(new Message(3,x,y));
                    int winer=board.whowin();
                    if(winer==yours)
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("您获胜了！");
                        alert.showAndWait();
                        isstart=false;
                    }
                    else if(winer==0){}
                    else
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("您输了！");
                        alert.showAndWait();
                        isstart=false;
                    }
                    if(isstart==false)
                    {
                        buttons.get(4).setDisable(true);
                        buttons.get(5).setDisable(true);
                    }
                    if(turnwho==1)
                        turnwho=2;
                    else
                        turnwho=1;
                    if(turnwho==yours)
                    {
                        chattext.appendText("轮到你了！\n");
                    }
                    else
                    {
                        chattext.appendText("轮到对手了！\n");
                    }
                });
            }
        });
        buttons.get(3).setOnAction(e->{
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("发送消息");
            dialog.setHeaderText("提示");
            dialog.setContentText("请输入您要发送的消息：");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                connect.send(new Message(2,result.get()));
            }
            new Alert(Alert.AlertType.INFORMATION,"发送成功").show();
            chattext.appendText(yournametext.getText()+"（你）: "+result.get()+"\n");
        });
        buttons.get(4).setOnAction(e->{
            if(model==0)//判断模式
            {
                try
                {
                    Cheer cheer=board.cheerStack.pop();//从栈中弹出最后的一个棋子
                    board.cheers[(int)cheer.x][(int)cheer.y]=0;//在棋盘中删除
                    board.updataGroup();//刷新棋盘
                }
                catch (EmptyStackException e2)
                {
                    new Alert(Alert.AlertType.ERROR,"没有棋可以悔了").showAndWait();
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
            if(model==1)
            {
                try
                {
                    if(board.cheerStack.size()<2)//判断现有棋子数
                        throw new EmptyStackException();
                    Cheer cheer=board.cheerStack.pop();//取出最后的落子
                    board.cheers[(int)cheer.x][(int)cheer.y]=0;//从棋盘删除
                    board.updataGroup();//更新棋盘
                    cheer=board.cheerStack.pop();
                    board.cheers[(int)cheer.x][(int)cheer.y]=0;
                    board.updataGroup();
                }
                catch (EmptyStackException e2)
                {
                    new Alert(Alert.AlertType.ERROR,"没有棋可以悔了").showAndWait();
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
            if(model==2)
            {
                if(yours==turnwho)
                {
                    connect.send(new Message(1,"请求悔棋"));
                    new Alert(Alert.AlertType.INFORMATION,"悔棋请求已发出").show();
                }
                else
                {
                    new Alert(Alert.AlertType.INFORMATION,"您只有在您的回合可以悔棋").show();
                }
            }
        });
        buttons.get(5).setOnAction(e->{
            connect.send(new Message(1,"认输"));
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("提示");
            alert1.setHeaderText("您被打败了！");
            alert1.setContentText("您投降了！");
            alert1.showAndWait();
            isstart=false;
        });
        buttons.get(6).setOnAction(e->{
            FileChooser fileChooser=new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
            File file=fileChooser.showSaveDialog(null);
            try
            {
                DataOutputStream dataOutputStream=new DataOutputStream(new FileOutputStream(file));
                for(int i=0;i<15;i++)
                {
                    for(int j=0;j<15;j++)
                    {
                        dataOutputStream.writeUTF(board.cheers[i][j]+"");
                    }
                }
                new Alert(Alert.AlertType.INFORMATION,"保存成功").showAndWait();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        });
        buttons.get(7).setOnAction(e->{
            FileChooser fileChooser=new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
            File file=fileChooser.showOpenDialog(null);
            try
            {
                DataInputStream dataOutputStream=new DataInputStream(new FileInputStream(file));
                for(int i=0;i<15;i++)
                {
                    for(int j=0;j<15;j++)
                    {
                        board.cheers[i][j]=Integer.valueOf(dataOutputStream.readUTF());
                    }
                }
                board.updataGroup();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        });
    }
    class AI
    {
        int score[][]=new int[rownum][rownum];
        int AIs;
        AI(int n)
        {
            if(n==1)
                AIs=2;
            else if(n==2);
            AIs=1;
        }
        class Location{
            private int x;//某个棋盘位置横坐标，0-14
            private int y;//某个棋盘位置纵坐标，0-14

            private int owner;//占据该位置的棋手方，1是人类，-1是机器，0是空
            private int score;//对该位置的打的分数

            public Location(){}
            public Location(int x, int y, int owner){
                this.x = x;
                this.y = y;
                this.owner = owner;
            }
            public Location(int x, int y, int owner, int score){
                this(x, y, owner);
                this.score = score;
            }
            public int getX(){return this.x;}
            public void setX(int x){this.x = x;}
            public int getY(){return this.y;}
            public void setY(int y){this.y = y;}
            public int getOwner(){return this.owner;}
            public void setOwner(int owner){this.owner = owner;}
            public int getScore(){return this.score;}
            public void setScore(int score){this.score = score;}
        }
        Location searchLocation(){
            if (yours==1)
                AIs=2;
            if(yours==2)
                AIs=1;
            //每次都初始化下score评分数组
            for(int i = 0; i  < rownum; i++){
                for(int j = 0; j < rownum; j++){
                    score[i][j] = 0;
                }
            }
            //每次机器找寻落子位置，评分都重新算一遍（虽然算了很多多余的，因为上次落子时候算的大多都没变）
            //先定义一些变量
            int humanChessmanNum = 0;//五元组中的黑棋数量
            int machineChessmanNum = 0;//五元组中的白棋数量
            int tupleScoreTmp = 0;//五元组得分临时变量
            int goalX = -1;//目标位置x坐标
            int goalY = -1;//目标位置y坐标
            int maxScore = -1;//最大分数

            //1.扫描横向的15个行
            for(int i = 0; i < 15; i++){
                for(int j = 0; j < 11; j++){
                    int k = j;
                    while(k < j + 5){

                        if(board.cheers[i][k] == AIs) machineChessmanNum++;
                        else if(board.cheers[i][k] == yours)humanChessmanNum++;

                        k++;
                    }
                    tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                    //为该五元组的每个位置添加分数
                    for(k = j; k < j + 5; k++){
                        score[i][k] += tupleScoreTmp;
                    }
                    //置零
                    humanChessmanNum = 0;//五元组中的黑棋数量
                    machineChessmanNum = 0;//五元组中的白棋数量
                    tupleScoreTmp = 0;//五元组得分临时变量
                }
            }

            //2.扫描纵向15行
            for(int i = 0; i < 15; i++){
                for(int j = 0; j < 11; j++){
                    int k = j;
                    while(k < j + 5){
                        if(board.cheers[k][i] == AIs) machineChessmanNum++;
                        else if(board.cheers[k][i] == yours)humanChessmanNum++;

                        k++;
                    }
                    tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                    //为该五元组的每个位置添加分数
                    for(k = j; k < j + 5; k++){
                        score[k][i] += tupleScoreTmp;
                    }
                    //置零
                    humanChessmanNum = 0;//五元组中的黑棋数量
                    machineChessmanNum = 0;//五元组中的白棋数量
                    tupleScoreTmp = 0;//五元组得分临时变量
                }
            }

            //3.扫描右上角到左下角上侧部分
            for(int i = 14; i >= 4; i--){
                for(int k = i, j = 0; j < 15 && k >= 0; j++, k--){
                    int m = k;
                    int n = j;
                    while(m > k - 5 && k - 5 >= AIs){
                        if(board.cheers[m][n] == AIs) machineChessmanNum++;
                        else if(board.cheers[m][n] == yours)humanChessmanNum++;

                        m--;
                        n++;
                    }
                    //注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
                    if(m == k-5){
                        tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                        //为该五元组的每个位置添加分数
                        for(m = k, n = j; m > k - 5 ; m--, n++){
                            score[m][n] += tupleScoreTmp;
                        }
                    }

                    //置零
                    humanChessmanNum = 0;//五元组中的黑棋数量
                    machineChessmanNum = 0;//五元组中的白棋数量
                    tupleScoreTmp = 0;//五元组得分临时变量

                }
            }

            //4.扫描右上角到左下角下侧部分
            for(int i = 1; i < 15; i++){
                for(int k = i, j = 14; j >= 0 && k < 15; j--, k++){
                    int m = k;
                    int n = j;
                    while(m < k + 5 && k + 5 <= 15){
                        if(board.cheers[n][m] == AIs) machineChessmanNum++;
                        else if(board.cheers[n][m] == yours)humanChessmanNum++;

                        m++;
                        n--;
                    }
                    //注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
                    if(m == k+5){
                        tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                        //为该五元组的每个位置添加分数
                        for(m = k, n = j; m < k + 5; m++, n--){
                            score[n][m] += tupleScoreTmp;
                        }
                    }
                    //置零
                    humanChessmanNum = 0;//五元组中的黑棋数量
                    machineChessmanNum = 0;//五元组中的白棋数量
                    tupleScoreTmp = 0;//五元组得分临时变量

                }
            }

            //5.扫描左上角到右下角上侧部分
            for(int i = 0; i < 11; i++){
                for(int k = i, j = 0; j < 15 && k < 15; j++, k++){
                    int m = k;
                    int n = j;
                    while(m < k + 5 && k + 5 <= 15){
                        if(board.cheers[m][n] == AIs) machineChessmanNum++;
                        else if(board.cheers[m][n] == yours)humanChessmanNum++;

                        m++;
                        n++;
                    }
                    //注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
                    if(m == k + 5){
                        tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                        //为该五元组的每个位置添加分数
                        for(m = k, n = j; m < k + 5; m++, n++){
                            score[m][n] += tupleScoreTmp;
                        }
                    }

                    //置零
                    humanChessmanNum = 0;//五元组中的黑棋数量
                    machineChessmanNum = 0;//五元组中的白棋数量
                    tupleScoreTmp = 0;//五元组得分临时变量

                }
            }

            //6.扫描左上角到右下角下侧部分
            for(int i = 1; i < 11; i++){
                for(int k = i, j = 0; j < 15 && k < 15; j++, k++){
                    int m = k;
                    int n = j;
                    while(m < k + 5 && k + 5 <= 15){
                        if(board.cheers[n][m] == AIs) machineChessmanNum++;
                        else if(board.cheers[n][m] == yours)humanChessmanNum++;

                        m++;
                        n++;
                    }
                    //注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
                    if(m == k + 5){
                        tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                        //为该五元组的每个位置添加分数
                        for(m = k, n = j; m < k + 5; m++, n++){
                            score[n][m] += tupleScoreTmp;
                        }
                    }

                    //置零
                    humanChessmanNum = 0;//五元组中的黑棋数量
                    machineChessmanNum = 0;//五元组中的白棋数量
                    tupleScoreTmp = 0;//五元组得分临时变量

                }
            }

            //从空位置中找到得分最大的位置
            for(int i = 0; i < 15; i++){
                for(int j = 0; j < 15; j++){
                    if(board.cheers[i][j] == 0 && score[i][j] > maxScore){
                        goalX = i;
                        goalY = j;
                        maxScore = score[i][j];
                    }
                }
            }

            if(goalX != -1 && goalY != -1){
                return new Location(goalX, goalY, -1);
            }

            //没找到坐标说明平局了，笔者不处理平局
            return new Location(-1, -1, -1);
        }
        //各种五元组情况评分表
        int tupleScore(int humanChessmanNum, int machineChessmanNum){
            //1.既有人类落子，又有机器落子，判分为0
            if(humanChessmanNum > 0 && machineChessmanNum > 0){
                return 0;
            }
            //2.全部为空，没有落子，判分为7
            if(humanChessmanNum == 0 && machineChessmanNum == 0){
                return 7;
            }
            //3.机器落1子，判分为35
            if(machineChessmanNum == 1){
                return 35;
            }
            //4.机器落2子，判分为800
            if(machineChessmanNum == 2){
                return 800;
            }
            //5.机器落3子，判分为15000
            if(machineChessmanNum == 3){
                return 15000;
            }
            //6.机器落4子，判分为800000
            if(machineChessmanNum == 4){
                return 800000;
            }
            //7.人类落1子，判分为15
            if(humanChessmanNum == 1){
                return 15;
            }
            //8.人类落2子，判分为400
            if(humanChessmanNum == 2){
                return 400;
            }
            //9.人类落3子，判分为1800
            if(humanChessmanNum == 3){
                return 1800;
            }
            //10.人类落4子，判分为100000
            if(humanChessmanNum == 4){
                return 100000;
            }
            return -1;//若是其他结果肯定出错了。这行代码根本不可能执行
        }
        Cheer getAICheer()
        {
            Location location=searchLocation();
            int x=location.getX();
            int y=location.getY();
            Cheer cheer=new Cheer(x,y,AIs);
            return cheer;
        }
    }
    public void start(Stage stage) throws Exception {
        initmainPane();
        Scene scene=new Scene(mainPane,1100,600);
        stage.setScene(scene);
        stage.setTitle("五子棋程序客户端");
        stage.setResizable(false);
        stage.getIcons().add(new Image("image/icon.png"));
        stage.show();
    }
    public static void main(String args[])
    {
        launch(args);
    }
}
class Cheer
{
    double x,y;
    int id;
    static Image imageblack=new Image("image/black.png");
    static Image imagewhite=new Image("image/white.png");
    ImageView imageView;
    Cheer(double x,double y,int id)
    {
        this.x=x;
        this.y=y;
        this.id=id;
        if(id==1)
            imageView=new ImageView(imageblack);
        if(id==2)
            imageView=new ImageView(imagewhite);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setX(x*40);
        imageView.setY(y*40);
    }
    ImageView getView()
    {
        return imageView;
    }
}
class Board implements Serializable
{
    int cheers[][]=new int[15][15];
    final int rownum=15;
    Group group=new Group();
    Stack<Cheer>cheerStack=new Stack<>();
    Board()
    {
        for(int i=0;i<rownum;i++)
        {
            for(int j=0;j<rownum;j++)
            {
                cheers[i][j]=0;
            }
        }
    }
    void addCheer(int x,int y,int id)
    {
        if(cheers[x][y]!=0)
            return;
        cheers[x][y]=id;
        cheerStack.push(new Cheer(x,y,id));
        updataGroup();
    }
    void addCheer(Cheer cheer)
    {
        int x=(int)cheer.x,y=(int)cheer.y,id=cheer.id;
        if(cheers[x][y]!=0)
            return;
        cheers[x][y]=id;
        cheerStack.push(new Cheer(x,y,id));
        updataGroup();
    }
    void updataGroup()
    {
        group.getChildren().clear();
        for(int i=0;i<rownum;i++)
        {
            for(int j=0;j<rownum;j++)
            {
                if(cheers[i][j]!=0)
                    group.getChildren().add(new Cheer(i,j,cheers[i][j]).getView());
            }
        }
    }
    int whowin()
    {
        for(int i=0;i<15;i++)
        {
            for(int j=0;j<15;j++)
            {
                int sum=0,x=i,y=j,flag=cheers[i][j];
                if(flag==0)
                    continue;
                if((x+4)<15)
                {
                    for(int k=0;k<5;k++)
                    {
                        if(flag==cheers[x][y])
                        {
                            sum++;
                            x++;
                        }
                        else
                            break;
                    }
                    if(sum==5)
                        return flag;
                }
                sum=0;x=i;y=j;
                if((y+4)<15)
                {
                    for(int k=0;k<5;k++)
                    {
                        if(flag==cheers[x][y])
                        {
                            sum++;
                            y++;
                        }
                        else
                            break;
                    }
                    if(sum==5)
                        return flag;
                }
                sum=0;x=i;y=j;
                if((x+4)<15&&(y-4)>=0)
                {
                    for(int k=0;k<5;k++)
                    {
                        if(flag==cheers[x][y])
                        {
                            sum++;
                            x++;
                            y--;
                        }
                        else
                            break;
                    }
                    if(sum==5)
                        return flag;
                }
                sum=0;x=i;y=j;
                if((x+4)<15&&(y+4)<15)
                {
                    for(int k=0;k<5;k++)
                    {
                        if(flag==cheers[x][y])
                        {
                            sum++;
                            x++;
                            y++;
                        }
                        else
                            break;
                    }
                    if(sum==5)
                        return flag;
                }
            }
        }
        return 0;
    }
    void clear()
    {
        for(int i=0;i<rownum;i++)
        {
            for(int j=0;j<rownum;j++)
            {
                cheers[i][j]=0;
            }
        }
        cheerStack.clear();
        group.getChildren().clear();
    }
}
class Connect extends Thread
{
    InetAddress inetAddress;
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    Client client;
    Message message=new Message();
    Connect(Client client)
    {
        this.client=client;
    }
    boolean setConnect(String ip,String port,String username,String roomname)
    {
        try
        {
            socket=new Socket(ip,Integer.valueOf(port));
            outputStream=new ObjectOutputStream(socket.getOutputStream());
            inputStream=new ObjectInputStream(socket.getInputStream());
            message.setMessage(0,username+"/"+roomname);
            send(message);
            start();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    void send(Message message)
    {
        try
        {
            outputStream.writeObject(message);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    void handler(Message acceptmessage)
    {
        if(acceptmessage.kind==0)
        {
            if(acceptmessage.content.equals("连接成功"))
            {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        client.buttons.get(2).setDisable(false);
                        client.buttons.get(3).setDisable(false);
                        new Alert(Alert.AlertType.INFORMATION,"连接成功").show();
                    }
                });
            }
        }
        if(acceptmessage.kind==1)
        {
            if(acceptmessage.content.equals("黑色"))
            {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        client.yours=1;
                        client.isstart=true;
                        new Alert(Alert.AlertType.INFORMATION,"对局开始").show();
                        client.chattext.appendText("你是黑棋（先手）\n");
                        client.buttons.get(4).setDisable(false);
                        client.buttons.get(5).setDisable(false);
                    }
                });
            }
            if(acceptmessage.content.equals("白色"))
            {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        client.yours=2;
                        client.isstart=true;
                        new Alert(Alert.AlertType.INFORMATION,"对局开始").show();
                        client.chattext.appendText("你是白棋（后手）\n");
                        client.buttons.get(4).setDisable(false);
                        client.buttons.get(5).setDisable(false);
                    }
                });
            }
            if(acceptmessage.content.equals("请求悔棋"))
            {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("请求");
                        alert.setHeaderText("您的对手请求悔棋");
                        alert.setContentText("您是否同意");
                        ButtonType agree=new ButtonType("同意");
                        ButtonType disagree=new ButtonType("不同意", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(agree,disagree);
                        Optional<ButtonType> result=alert.showAndWait();
                        if(result.get()==agree)
                        {
                            send(new Message(1,"同意悔棋"));
                        }
                    }
                });
            }
            if(acceptmessage.content.equals("同意悔棋"))
            {
                try
                {
                    Platform.runLater(new Runnable() {//更新javafx界面必须使用
                        @Override
                        public void run() {
                            if(client.board.cheerStack.size()<2)//判断棋盘上的棋子数目
                                throw new EmptyStackException();
                            Cheer cheer=client.board.cheerStack.pop();//取出棋子
                            client.board.cheers[(int)cheer.x][(int)cheer.y]=0;//从棋盘删除
                            client.board.updataGroup();//更新棋盘
                            cheer=client.board.cheerStack.pop();
                            client.board.cheers[(int)cheer.x][(int)cheer.y]=0;
                            client.board.updataGroup();
                        }
                    });
                }
                catch (EmptyStackException e2)
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new Alert(Alert.AlertType.ERROR,"没有棋可以悔了").show();
                        }
                    });
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
            if(acceptmessage.content.equals("对手认输"))
            {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("祝贺");
                        alert.setHeaderText("您获胜了！");
                        alert.setContentText("您的对手投降了！");
                        alert.showAndWait();
                        client.isstart=false;
                    }
                });
            }
        }
        if(acceptmessage.kind==2)
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    client.chattext.appendText(acceptmessage.content);
                }
            });
        }
        if(acceptmessage.kind==3)
        {
            Platform.runLater(new Runnable() {//线程中更改javafx界面时的调动函数
                @Override
                public void run() {
                    client.board.addCheer(acceptmessage.x,acceptmessage.y,client.turnwho);//添加接收到的棋子信息
                    int winer=client.board.whowin();//判断输赢
                    if(winer==client.yours)
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("您获胜了！");
                        alert.showAndWait();
                        client.isstart=false;
                    }
                    else if(winer==0){}
                    else
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("您输了！");
                        alert.showAndWait();
                        client.isstart=false;
                    }
                    if(client.isstart==false)
                    {
                        client.buttons.get(4).setDisable(true);
                        client.buttons.get(5).setDisable(true);
                    }
                    if(client.turnwho==1)
                        client.turnwho=2;
                    else
                        client.turnwho=1;
                }
            });
        }
    }
    public void run() {
        while(socket.isConnected()) {
            try
            {
                Message message=(Message)inputStream.readObject();
                handler(message);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"服务器连接已断开").showAndWait();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=2;i<6;i++)
                        {
                            client.buttons.get(i).setDisable(true);
                        }
                    }
                });
            }
        }
    }
}
