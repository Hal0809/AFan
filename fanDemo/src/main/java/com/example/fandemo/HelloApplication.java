package com.example.fandemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FanPane fanPane = new FanPane();
        fanPane.setStyle("-fx-background-color: rgb(255,255,255);");
        EventHandler<ActionEvent> eventHandler = e->{
            fanPane.setCurrent();
        };

        //创建动画：设置时间线类，不断重绘FanPane
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1),eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        //下方按钮面板
        ControlPane controlPane = new ControlPane();
        controlPane.setStyle("-fx-background-color: rgb(255,255,255);");
        controlPane.setMaxWidth(330);
        controlPane.setFocusTraversable(true);
        //定义按钮功能
        controlPane.pause.setOnAction(e-> {
            fanPane.turnOff();
            controlPane.changeBtn();
            controlPane.requestFocus();
        });
        controlPane.start.setOnAction(e-> {
            fanPane.turnOn();
            controlPane.changeBtn();
            controlPane.requestFocus();
        });
        controlPane.speedUp.setOnAction(e-> {
            fanPane.speedUp();
            controlPane.requestFocus();
        });
        controlPane.speedLow.setOnAction(e-> {
            fanPane.speedDown();
            controlPane.requestFocus();
        });
        controlPane.turnL.setOnAction(e-> {
            fanPane.turnL();
            controlPane.requestFocus();
        });
        controlPane.turnR.setOnAction(e-> {
            fanPane.turnR();
            controlPane.requestFocus();
        });
        //重写ControlPane上的键盘事件
        controlPane.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.UP){
                fanPane.speedUp();
            }
            if(e.getCode()== KeyCode.DOWN){
                fanPane.speedDown();
            }
            if(e.getCode()== KeyCode.LEFT){
                fanPane.turnL();
            }
            if(e.getCode()== KeyCode.RIGHT){
                fanPane.turnR();
            }
            if(e.getCode()==KeyCode.SPACE) {
                fanPane.stateChange();
                controlPane.changeBtn();
            }
        });
        //不要在按钮上重写键盘事件！！！

        //上方的事件面板
        TimeLable timeLable = new TimeLable();
        timeLable.setStyle("-fx-background-color: rgb(255,255,255);");
        timeLable.paint();

        //主面板容器，存放上述三种面板
        BorderPane pan = new BorderPane();
        pan.setCenter(fanPane);
        pan.setBottom(controlPane);
        pan.setTop(timeLable);
        //主场景
        Scene scene = new Scene(pan, 330, 540);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        //在controlPane上重写鼠标进入和离开事件，实现鼠标样式的改变
        controlPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                scene.setCursor(Cursor.HAND);
            }
        });
        controlPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}

class TimeLable extends StackPane {
    //设置实时时间
    public void paint(){
        double centerX = 200;
        double centerY = 50;
        double radiusX = centerX-50;
        double radiusY = centerY-10;
        Ellipse ellipse = new Ellipse(centerX,centerY,radiusX,radiusY);

        ellipse.setFill(Color.rgb(191,184,48,0.3));
        ellipse.setStroke(Color.color(0.1,0.3,0.42,0.9));
        Label time = new Label();
        time.setFont(new Font(20));
        time.setAlignment(Pos.CENTER);
        DateFormat currentTime = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

        EventHandler<ActionEvent> eventHandler = e->{
            time.setText(currentTime.format(new Date()));
        };
        Timeline clock = new Timeline(new KeyFrame(Duration.millis(1000),eventHandler));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();

        getChildren().add(ellipse);
        getChildren().add(time);
    }
}

class FanPane extends Pane {
    private double baseAng,speed,preSpeed=0;
    public FanPane(){
        baseAng = 0;
        speed = 0.1;
        setMinSize(50,50);
    }
    public FanPane(double baseAng,double speed){
        this.baseAng=baseAng;
        this.speed=speed;
    }

    void stateChange(){//设置风扇启停状态
        double temp;
        temp = speed;
        speed=preSpeed;
        preSpeed=temp;
    }
    public void speedUp(){//风扇加速，即 ↑ 按钮的功能。
        speed*=1.3;
        paintFan();
    }
    public void speedDown(){//风扇减速，即 ↓ 按钮的功能
        speed*=0.7;
        paintFan();
    }
    public void turnL(){
        speed=Math.abs(speed);
        paintFan();
    }
    public void turnR(){
        speed = -Math.abs(speed);
        paintFan();
    }

    public void turnOff(){
        preSpeed = speed;
        speed = 0;
        paintFan();
    }
    public void turnOn(){
        speed = preSpeed;
        preSpeed = 0;
        paintFan();
    }
    public void setCurrent(){
        paintFan();
    }

    public void setCurrent(double baseAng,double speed){
        this.baseAng = baseAng;
        this.speed = speed;
        paintFan();
    }
    public void paintFan(){

        double fanRadius = Math.min(getWidth(),getHeight())*0.8*0.5;//设置半径大小，为当前面板的长/宽最小值
        double centerX = getWidth()/2;
        double centerY = getHeight()/2;

        Circle circle = new Circle(centerX,centerY,fanRadius);
        circle.setFill(Color.rgb(0,0,0,0.1));
        circle.setStroke(Color.rgb(166,157,0));

        double angRadius = fanRadius*0.8;//扇形半径
        baseAng=(baseAng+speed)%360.0;//扇形起始点每次调用不断增加，由speed控制
        Arc arc1 = new Arc(centerX,centerY,angRadius,angRadius,baseAng,60);
        arc1.setFill(Color.rgb(119,158,0));
        arc1.setType(ArcType.ROUND);

        Arc arc2 = new Arc(centerX,centerY,angRadius,angRadius,baseAng+120,60);
        arc2.setFill(Color.rgb(221,100,191));
        arc2.setType(ArcType.ROUND);

        Arc arc3 = new Arc(centerX,centerY,angRadius,angRadius,baseAng+240,60);
        arc3.setFill(Color.rgb(161,104,213));
        arc3.setType(ArcType.ROUND);

        getChildren().clear();
        getChildren().addAll(circle,arc1,arc2,arc3);
    }
    //以下两个函数暂时不需要。
    @Override
    public void setWidth(double width){
        super.setWidth(width);
        paintFan();
    }

    @Override
    public void setHeight(double height){
        super.setHeight(height);
        paintFan();
    }
}

class ControlPane extends BorderPane {
    Button speedUp = new Button("↑");
    Button speedLow = new Button("↓");
    Button turnR = new Button("→");
    Button turnL = new Button("←");
    Button pause = new Button("||");
    Button start = new Button(">");
    public ControlPane(){
        StackPane centerPane = new StackPane(start,pause);

        setTop(speedUp);
        setBottom(speedLow);
        setLeft(turnL);
        setRight(turnR);
        setCenter(centerPane);
        turnL.setMinSize(110,20);
        turnR.setMinSize(110,20);
        speedUp.setMinSize(330,20);
        speedLow.setMinSize(330,20);;
        pause.setMinSize(110,20);
        start.setMinSize(110,20);
        setAlignment(speedUp, Pos.CENTER);
        setAlignment(speedLow, Pos.CENTER);
        setAlignment(this,Pos.CENTER);
//        setMaxWidth(330);
//        setAlignment(turnL,Pos.CENTER_RIGHT);
//        setAlignment(turnR,Pos.CENTER);
    }
    void changeBtn(){
        pause.setVisible(!pause.isVisible());
    }
}
