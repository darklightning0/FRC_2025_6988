// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgeaIntake;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.subsystems.Remote;
import frc.robot.subsystems.Remote.IntakeArmMode;

public class IntakeArm {
  public static TalonSRX armMotor = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.Intake_Arm);

  public IntakeArm() {
    armMotor.setInverted(false);
    armMotor.setNeutralMode(NeutralMode.Brake);
  }

  double modeToPercent(IntakeArmMode mode) {
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

  public void mainloop() {
    double percent = modeToPercent(Remote.getIntakeArmMode()) * 0.50;
    armMotor.set(TalonSRXControlMode.PercentOutput, percent);
  }
}