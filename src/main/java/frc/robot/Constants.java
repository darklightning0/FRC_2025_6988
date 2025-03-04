// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class ControllerConstants {
      public static final int kDriverControllerPort = 0;
      public static final int kOperatorControllerPort = 1;

      public static final int driverJoystickID = 1;
      public static final int operatorJoystickID = 0;

      public static final CommandXboxController driverJoystick = new CommandXboxController(driverJoystickID);
      public static final XboxController driverJoystickDef = new XboxController(driverJoystickID);
      public static final CommandXboxController operatorJoystick = new CommandXboxController(operatorJoystickID);
      public static final XboxController operatorJoystickDef = new XboxController(operatorJoystickID);

    }
//ALGEA INTAKE

  
  public static class ReefLayers{
      public static final double L1= 0.46;
      public static final double L2= 0.81;
      public static final double L3= 1.21;
      public static final double L4= 1.83;
  }

//CORAL SHOOTER

public static class SubsystemConstants {
  public static class TalonIDs {
    public static class FX {
      
    }
    public static class SRX {
      public static final int Elevator_Outer = 16; // Big elevator motor
      public static final int Elevator_Inner = 0; // Small elevator motor
      public static final int Intake_Arm = 0; // Intake arm motor
      public static final int Shooter_Left = 0; // Shooter left
      public static final int Shooter_Right = 0; // Shooter right
    }
    public static class Spark {
      public static final int Intake_Left = 23;
      public static final int Intake_Right = 22;
    }
  }

  public static class RemoteButtons {
    /*
     * 
     * 
     * 
     * 
     */
  }
  public static class RemoteOperatorButtons {
    public static final int intakeUp = 1;
    public static final int intakeDown = 2;
    public static final int intakeAlgeaIn = 8;
    public static final int intakeAlgeaShoot = 4;
    public static final int shooter = 5;
    public static final int coralShoot = 5;

  }

  public static class AMTEncoder {
    public static final int Elevator_Outer = 0;
    public static final int Elevator_Inner = 0;
  }

  public static class Other {
    public static final double ULTRASONIC_DETECTION_THRESHOLD_CM = 2.0; // TODO

  }
 

}






}