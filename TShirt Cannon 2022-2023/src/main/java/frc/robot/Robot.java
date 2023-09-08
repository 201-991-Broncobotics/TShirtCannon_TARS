
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Robot extends TimedRobot {
    private final Joystick m_stick = new Joystick(0);
  
    // Solenoid corresponds to a single solenoid.
    private final Solenoid m_solenoid_shoot = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private final Solenoid m_solenoid_load = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
    private final Solenoid m_solenoid_horn = new Solenoid(PneumaticsModuleType.CTREPCM, 4);
    private TalonSRX shooter = new TalonSRX(0);
    
    // DoubleSolenoid corresponds to a double solenoid.
    // private final DoubleSolenoid m_doubleSolenoid = new DoubleSolenoid(1, 2);
  
    private Drivetrain dT;
  
    @Override
    public void robotInit() {
        
        /* UsbCamera camera = CameraServer.startAutomaticCapture();
        
        camera.setResolution(1024, 1024);
        camera.setFPS(30); */
      
      dT = new Drivetrain(3, 8, 6, 4);
    }

    @Override
    public void teleopPeriodic() { 
      
        dT.drive(m_stick);
      
        if (m_stick.getRawAxis(3) > 0.1) {
            m_solenoid_shoot.set(true);
            m_solenoid_load.set(false);
        } else {
            m_solenoid_shoot.set(false);
            m_solenoid_load.set(true);
        }
      
        if (m_stick.getRawButton(5)) {
            shooter.set(ControlMode.PercentOutput, 0.2);
        } else if (m_stick.getRawButton(6)) {
            shooter.set(ControlMode.PercentOutput, -0.2);
        } else {
            shooter.set(ControlMode.PercentOutput, 0);
        }
    
        if (m_stick.getRawButton(4) && m_stick.getRawAxis(2) > 0.5 && m_stick.getRawButton(2)) {
            m_solenoid_horn.set(true);
        } else {
            m_solenoid_horn.set(false);
        }
    }
}
