// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgeaIntake;

import frc.robot.Constants;


import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Remote;
import frc.robot.subsystems.Remote.IntakeArmMode;



public class intakeArmRotation extends SubsystemBase {

  public static TalonSRX rotationMotor = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.AlgInt_Rotation);

  


  public intakeArmRotation(){

    rotationMotor.setInverted(false);

    rotationMotor.setNeutralMode(NeutralMode.Brake);
  }


  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  double modeToPercent(IntakeArmMode mode){
    switch (mode) {
      case Up:
        return 1.;
      case Down:
      
        return -1.;
      case Idle:
      default:
       return 0.;
    }
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
    double percent = modeToPercent(Remote.getIntakeArmMode()) * 0.50;
    rotationMotor.set(TalonSRXControlMode.PercentOutput, percent);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}