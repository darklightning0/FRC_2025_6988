package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.Constants.SubsystemConstants.RemoteOperatorButtons;

import static frc.robot.Constants.ControllerConstants.operatorJoystickDef;

public class Shooter {

  public final TalonSRX leftRedline = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.Shooter_Left);
  public final TalonSRX rightRedline = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.Shooter_Right);

  enum ShooterMode {
    Idle,
    See,
    Hold,
    Shoot,
  }


  private double vel = 0;
  private ShooterMode mode = ShooterMode.Idle;

  public Shooter() {
    leftRedline.setInverted(false);//left
    rightRedline.setInverted(false);

    leftRedline.setNeutralMode(NeutralMode.Brake);
    rightRedline.setNeutralMode(NeutralMode.Brake);
  }

  public void mainloop(boolean objectSeen) {
    if (objectSeen) {
      if (mode != ShooterMode.Shoot) {
        mode = ShooterMode.See;
      }
    } else {
      if (mode == ShooterMode.See) {
        mode = ShooterMode.Hold;
      }
    }

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
      leftRedline.set(ControlMode.Velocity, vel);
      rightRedline.set(ControlMode.Velocity, vel);
    } else {
      leftRedline.set(ControlMode.Velocity, vel);
      rightRedline.set(ControlMode.Velocity, vel);
    }
  }
}