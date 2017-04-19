/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlrrobot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Jacob
 */
public class JLRRobot {

    Robot robot;

    public static void main(String[] args) throws AWTException {
        JLRRobot RobotHandler = new JLRRobot();
//        RobotHandler.test1();
//        RobotHandler.colorTest();
//        RobotHandler.pointerLocationTest();
        RobotHandler.test2();
    }

    public JLRRobot() throws AWTException {
        robot = new Robot();

        robot.setAutoDelay(5);
        robot.setAutoWaitForIdle(true);
    }

    private void colorTest() {
        robot.delay(4000);
        Point thisPoint;
        for (int i = 0; i < 15; i++) {
            robot.delay(1000);
            thisPoint = getPointerPosition();
            getColorList(thisPoint.x, thisPoint.y);
//            robot.mouseMove(i, 400);
//            getColorList(i, 400);
//            robot.delay(5);
        }

    }

    private void pointerLocationTest() {
        robot.delay(3000);
        Point thisPoint;
        for (int i = 0; i < 15; i++) {
            robot.delay(1000);
            thisPoint = getPointerPosition();
            getColorList(thisPoint.x, thisPoint.y);
            System.out.println(thisPoint.toString());
        }
    }

    private Point getPointerPosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    private ArrayList<Integer> getColorList(int xPosition, int yPosition) {
        ArrayList<Integer> returnList = new ArrayList();
        Color testColor = robot.getPixelColor(xPosition, yPosition);
        float[] thisColor = testColor.getRGBColorComponents(null);
        System.out.println(Arrays.toString(thisColor));
        return returnList;
    }

    private void test1() {

        robot.delay(4000);
        robot.mouseMove(40, 130);
        robot.delay(500);

        leftClick();
        robot.delay(500);
        leftClick();

        robot.delay(500);
        type("Hello, world");

        robot.mouseMove(160, 160);
        robot.delay(500);

        leftClick();
        robot.delay(500);
        leftClick();

        robot.delay(500);
        type("This is a test of the Java Robot class");

        robot.delay(50);
        type(KeyEvent.VK_DOWN);

        robot.delay(250);
        type("Four score and seven years ago, our fathers ...");

        robot.delay(1000);
        System.exit(0);
    }

    private void test2() {
        clickAtPosition(643, 1065);
        clickAtPosition(716, 1055);
        clickAtPosition(754, 1057);
        clickAtPosition(808, 1058);
    }

    private void clickAtPosition(int x, int y) {
        robot.delay(500);
        robot.mouseMove(x, y);
        robot.delay(500);
        leftClick();
        robot.delay(500);
    }

    private void leftClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(200);
    }

    private void type(int i) {
        robot.delay(40);
        robot.keyPress(i);
        robot.keyRelease(i);
    }

    private void type(String s) {
        byte[] bytes = s.getBytes();
        for (byte b : bytes) {
            int code = b;
            // keycode only handles [A-Z] (which is ASCII decimal [65-90])
            if (code > 96 && code < 123) {
                code = code - 32;
            }
            robot.delay(5);
            robot.keyPress(code);
            robot.keyRelease(code);
        }
    }
}
