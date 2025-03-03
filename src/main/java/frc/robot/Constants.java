// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
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

      public static final int driverJoystickID = 0;
      public static final int operatorJoystickID = 1;

      public static final CommandXboxController driverJoystick = new CommandXboxController(driverJoystickID);
      public static final XboxController driverJoystickDef = new XboxController(driverJoystickID);
      public static final CommandJoystick operatorJoystick = new CommandJoystick(operatorJoystickID);
      public static final Joystick operatorJoystickDef = new Joystick(operatorJoystickID);

    }
//ALGEA INTAKE

  
  

//CORAL SHOOTER

public static class SubsystemConstants {
  public static class TalonIDs {
    public static class FX {
      
    }
    public static class SRX {
      public static final int Elevator_Outer = 0;
      public static final int Elevator_Inner = 0;
      public static final int AlgInt_Rotation = 0;
      public static final int CoralShooterLeft=0;
      public static final int CoralShooterRight=0;
    }
    public static class Victor {
      public static final int AlgInt_LeftNEO = 1;
  public static final int AlgInt_RightNEO = 2;
    }
  }

  public static class RemoteButtons {
    
  }
  public static class RemoteOperatorButtons {
    public static final int intakeUp = 1;
    public static final int intakeDown = 2;
    public static final int intakeAlgeaIn = 3;
    public static final int intakeAlgeaShoot = 4;
  }

  public static class AMTEncoderIDs {
    public static final int Elevator_Outer = 0;
    public static final int Elevator_Inner = 0;
  }
 

}






}