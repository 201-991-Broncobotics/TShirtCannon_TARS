package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// import edu.wpi.first.wpilibj.smartdashboard.*;
// import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Joystick;

// import edu.wpi.first.wpilibj.DriverStation;
// import com.ctre.phoenix.motorcontrol.en
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Drivetrain {
    
    // define the motors and other sensors
    private final TalonSRX frontLeft;
    private final TalonSRX frontRight;
    private final TalonSRX backLeft;
    private final TalonSRX backRight;
    
    BuiltInAccelerometer gyro = new BuiltInAccelerometer();
    
    boolean strafing = true;
    double gyro_angle_ref = 0;
    public int dividyNum = 1;

    // SmartDashboard my_dash = new SmartDashboard();

    public Drivetrain(int __frontLeft__, int __frontRight__, int __backLeft__, int __backRight__) {
        
        // defines names for the motors
        frontLeft = new TalonSRX(__frontLeft__);
        frontRight = new TalonSRX(__frontRight__);
        backLeft =  new TalonSRX(__backLeft__);
        backRight =  new TalonSRX(__backRight__);

        // reverses motors so that you ovefoward with positive values
        frontLeft.setInverted(true);
        backLeft.setInverted(true);
        backRight.setInverted(true);

    }

    public void MoveRobot(float leftStickX, float leftStickY, float rightStickX, boolean deadZoneOff) {

        // stops the motors when stick not pressed
        backLeft.set(ControlMode.PercentOutput, 0);
        backRight.set(ControlMode.PercentOutput, 0);
        frontLeft.set(ControlMode.PercentOutput, 0);
        frontRight.set(ControlMode.PercentOutput, 0);

        // which ever stick is pushed farthest is the one that it does
        if (Math.abs(leftStickX) > .2f || Math.abs(rightStickX) > .2f || Math.abs(leftStickY) > .2f || deadZoneOff) {
            
            if (Math.abs(leftStickX) > Math.abs(leftStickY) && Math.abs(leftStickX) > Math.abs(rightStickX)) 
                
                /* if (!strafing) {
                    DriverStation.reportError(String.valueOf(gyro.getY()), false);
                    // SmartDashboard.putNumber(("y_val"), gyro.getY());
                }
                
                double leftStickX_l = rightStickX + (gyro.getY());
                if (leftStickX_l > 1) {
                    leftStickX_l = 1;
                } else if (leftStickX_l < -1) {
                    leftStickX = -1;
                } 
                strafing = true;
                // strafes */
                
                backLeft.set(ControlMode.PercentOutput, leftStickX/dividyNum);
                backRight.set(ControlMode.PercentOutput, -leftStickX/dividyNum);
                frontLeft.set(ControlMode.PercentOutput, -leftStickX/dividyNum);
                frontRight.set(ControlMode.PercentOutput, leftStickX/dividyNum);
            } else if (Math.abs(leftStickY) > Math.abs(leftStickX) && Math.abs(leftStickY) > Math.abs(rightStickX)) {
            
                strafing = false;

                // drives fowards and backwards
                backLeft.set(ControlMode.PercentOutput, leftStickY/dividyNum);
                backRight.set(ControlMode.PercentOutput, leftStickY/dividyNum);
                frontLeft.set(ControlMode.PercentOutput, leftStickY/dividyNum);
                frontRight.set(ControlMode.PercentOutput, leftStickY/dividyNum);
            } else {
                strafing = false;
                // robot rotates
                backLeft.set(ControlMode.PercentOutput, -rightStickX/dividyNum);
                backRight.set(ControlMode.PercentOutput, rightStickX/dividyNum);
                frontLeft.set(ControlMode.PercentOutput, -rightStickX/dividyNum);
                frontRight.set(ControlMode.PercentOutput, rightStickX/dividyNum);
            }
        }
    }

    public void drive(Joystick gamepad) {
        
        double lx = deadzone(gamepad.getRawAxis(0));
        double ly = -deadzone(gamepad.getRawAxis(1)); // negative because it inverts the thing
        double rx = deadzone(gamepad.getRawAxis(4));

        double x1 = Math.abs(lx);
        double y1 = Math.abs(ly);
        double x2 = Math.abs(rx);

        double bL = ((y1 > x1 && y1 > x2) ? ly : (x1 > x2) ? -lx : rx);
        double bR = ((y1 > x1 && y1 > x2) ? ly : (x1 > x2) ? lx : -rx);
        double fR = ((y1 > x1 && y1 > x2) ? ly : (x1 > x2) ? -lx : -rx);
        double fL = ((y1 > x1 && y1 > x2) ? ly : (x1 > x2) ? lx : rx);

        System.out.println(bL + " " + bR + " " + fL + " " + fR);

        backLeft.set(ControlMode.PercentOutput, bL);
        backRight.set(ControlMode.PercentOutput, bR);
        frontLeft.set(ControlMode.PercentOutput, fL);
        frontRight.set(ControlMode.PercentOutput, fR);
        
    }
    
    public double deadzone(double in){
        return Math.abs(in) > 0.1 ? in : 0;
    }
    
}
