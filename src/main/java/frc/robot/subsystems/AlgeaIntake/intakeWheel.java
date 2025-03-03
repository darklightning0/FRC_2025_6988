// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgeaIntake;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;


import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.subsystems.Remote;
import frc.robot.subsystems.Remote.IntakeWheelMode;

public class intakeWheel extends SubsystemBase {

  public static VictorSPX leaderNeo = new VictorSPX(Constants.SubsystemConstants.TalonIDs.Victor.AlgInt_LeftNEO);
  public static VictorSPX followerNeo = new VictorSPX(Constants.SubsystemConstants.TalonIDs.Victor.AlgInt_RightNEO);

  
  private static double neoSpeed = 0.4;


  public intakeWheel(){

    leaderNeo.setInverted(false);//left
    followerNeo.setInverted(true);
    
    leaderNeo.setNeutralMode(NeutralMode.Brake);
    followerNeo.setNeutralMode(NeutralMode.Brake);

    followerNeo.follow(leaderNeo);
    

  }

   double modeToPercent(IntakeWheelMode mode){
    switch (mode) {
      case In:
        return 1.;
      case Shoot:
        return -1.;
      case Idle:
      default:
       return 0.;
    }
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
  

  }

  public void teleopPeriodica(){
    double percent = modeToPercent(Remote.getIntakeWheelMode())* neoSpeed;
    leaderNeo.set(VictorSPXControlMode.PercentOutput, percent);
  }


  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}