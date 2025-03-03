package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.Constants.SubsystemConstants.RemoteOperatorButtons;
import frc.robot.util.UltrasonicSensor;

import static frc.robot.Constants.ControllerConstants.operatorJoystickDef;

public class Shooter extends SubsystemBase {

  private static UltrasonicSensor ultrasonicSensor;
  public static TalonSRX leftRedline = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.CoralShooterLeft);
  public static TalonSRX rightRedline = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.CoralShooterRight);
  private final double DETECTION_THRESHOLD_CM= 2.0;//TODO

  enum ShooterMode {
    Idle,
    See,
    Hold,
    Shoot,
  }

  private boolean isdropping;

  private ShooterMode mode;

  public Shooter() {
    isdropping=false;
    ultrasonicSensor = new UltrasonicSensor(0, 1);//TODO
    
    leftRedline.setInverted(false);//left
    rightRedline.setInverted(false);

    leftRedline.setNeutralMode(NeutralMode.Brake);
    rightRedline.setNeutralMode(NeutralMode.Brake);

    leftRedline.config_kP(0, 0.2);
    leftRedline.config_kI(0, 0.0);
    leftRedline.config_kD(0, 0.0);
    leftRedline.config_kF(0, 0.0);

    rightRedline.config_kP(0, 0.2);
    rightRedline.config_kI(0, 0.0);
    rightRedline.config_kD(0, 0.0);
    rightRedline.config_kF(0, 0.0);
  }

 
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    ultrasonicSensor.measureDistance();
    
    if (ultrasonicSensor.getDistanceCm() < DETECTION_THRESHOLD_CM) {
      if (mode != ShooterMode.Shoot) {
        mode = ShooterMode.See;
      }
    } else {
      if (mode == ShooterMode.See) {
        mode = ShooterMode.Hold;
      }
    }

    double vel;
    switch (mode) {
      case Idle:
        vel = 0;
        break;
      case See:
        vel = 1.5;
      case Hold:
        vel = -0.05;
      case Shoot:
        vel = 5;
    }

    
    // Run motors to intake the game piece
    leftRedline.set(ControlMode.Velocity, vel);
    rightRedline.set(ControlMode.Velocity, vel);
  }

  public void shooter(){
    if(operatorJoystickDef.getRawButton(RemoteOperatorButtons.coralShoot)){
      isdropping=true;
      leftRedline.set(ControlMode.Velocity, 100);
      rightRedline.set(ControlMode.Velocity, 100);
    } else {
      leftRedline.set(ControlMode.Velocity, 0);
      rightRedline.set(ControlMode.Velocity, 0);
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}