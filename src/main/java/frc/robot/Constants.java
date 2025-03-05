// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

	public static class ControllerConstants {
		public static final int kDriverControllerPort = 1;
		public static final int kOperatorControllerPort = 0;

		public static final int driverJoystickID = 1;
		public static final int operatorJoystickID = 0;

		public static final CommandXboxController driverJoystick = new CommandXboxController(driverJoystickID);
		public static final XboxController driverJoystickDef = new XboxController(driverJoystickID);
		public static final CommandXboxController operatorJoystick = new CommandXboxController(operatorJoystickID);
		public static final XboxController operatorJoystickDef = new XboxController(operatorJoystickID);

	}
	// ALGEA INTAKE

	// must be between 0 and 1
	public static class ReefLayers {
		public static final double L1 = 0.2513;
		public static final double L2 = 0.4426;
		public static final double L3 = 0.6612;
		public static final double L4 = 0.9500;
	}

	// CORAL SHOOTER

	public static class SubsystemConstants {
		public static class TalonIDs {
			public static class FX {
				public static final int Intake_Arm = 24; // Intake arm motor
			}

			public static class SRX {
				public static final int Elevator_Outer = 16; // Big elevator motor
				public static final int Elevator_Inner = 18; // Small elevator motor
				public static final int Shooter_Left = 15; // Shooter left
			}

			public static class Spark {
				public static final int Intake_Left = 23;
				public static final int Intake_Right = 22;
			}
		}

		public static class RemoteOperatorButtons {
			public static final int intakeUp = 1;
			public static final int intakeDown = 2;
			// public static final int intakeAlgeaIn = 7;
			// public static final int intakeAlgeaShoot = 8;
			public static final int home = 7;
		}

		public static class AMTEncoder {
			public static final int Elevator_Outer = 0;
			public static final int Elevator_Inner = 0;
		}

	}

}