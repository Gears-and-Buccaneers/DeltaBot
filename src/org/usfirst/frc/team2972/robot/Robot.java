package org.usfirst.frc.team2972.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive class.
 */
public class Robot extends SampleRobot {
    RobotDrive robotDrive;
    Joystick stick;
    Talon frontLtTalon, frontRtTalon, rearTalon;
    
    // Channels for the wheels
    final int frontLeftChannel = 1;
    final int frontRightChannel = 3;
    final int rearChannel = 2;
    
    // The channel on the driver station that the joystick is connected to
    final int joystickChannel = 0;

    public Robot() {
    //set vars
    stick = new Joystick(joystickChannel);
   
    frontLtTalon = new Talon(frontLeftChannel);
    frontRtTalon = new Talon(frontRightChannel);
    rearTalon = new Talon(rearChannel);
   
    //if (isOperatorControl()){}else if (isTest()){}
    }
        
    public void operatorControl() {
    double offsetAngle1 = 30; //front left
    double offsetAngle2 = -30; //front right
    double offsetAngle3 = 180; //rear

        while (isOperatorControl() && isEnabled()) {
            double x = stick.getX();
            double y = stick.getY()*-1;
            double rotation = stick.getRawAxis(4)*-0.3;
            
            //convert cartesian to polar
            double joyRadians = Math.atan2(y,x);
            double joyAngle = Math.toDegrees(joyRadians) + 90; //offset 
            double joyR = Math.sqrt((y*y)+(x*x));
            
            SmartDashboard.putNumber("Joy Radian",joyRadians);
            SmartDashboard.putNumber("Joy Angle",joyAngle);
            SmartDashboard.putNumber("Joy R",joyR);
            SmartDashboard.putNumber("Rotation",rotation);
            
            //get angle diffs 
            double diffAngle1 = offsetAngle1 - joyAngle;
            double diffAngle2 = offsetAngle2 - joyAngle;
            double diffAngle3 = offsetAngle3 - joyAngle;
            
            //get speeds
            double speed1 = joyR * Math.sin(Math.toRadians(diffAngle1)) + rotation;
            double speed2 = joyR * Math.sin(Math.toRadians(diffAngle2)) + rotation;
            double speed3 = joyR * Math.sin(Math.toRadians(diffAngle3)) + rotation;
            
            //set talons
            frontLtTalon.set(speed1);
            frontRtTalon.set(speed2);
            rearTalon.set(speed3);
            
            Timer.delay(0.005); // wait 5ms to avoid hogging CPU cycles
        }
    }
    
    public void test(){
        while (isTest() && isEnabled()) {
    if (stick.getRawButton(1)){
        frontLtTalon.set(1);
       }else{
        frontLtTalon.set(0);
       }
       if (stick.getRawButton(2)){
        frontRtTalon.set(1);
       }else{
        frontRtTalon.set(0);
       }
       if (stick.getRawButton(3)){
        rearTalon.set(1);
       }else{
        rearTalon.set(0);
       }
       Timer.delay(0.005);
        }
    }
}
