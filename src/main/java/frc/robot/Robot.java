// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.subsystems.Remote.IntakeArmMode;
import frc.robot.subsystems.Remote.IntakeWheelMode;
import frc.robot.subsystems.Remote.ShooterMode;

public class Robot extends TimedRobot {
	private Command m_autonomousCommand;

	private final RobotContainer m_robotContainer;

	public Robot() {
		m_robotContainer = new RobotContainer();
	}

	@Override
	public void robotInit() {
		CameraServer.startAutomaticCapture();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	@Override
	public void disabledExit() {
	}

	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	@Override
	public void autonomousPeriodic() {
		/*
		 * if (time < 5) {
		 * ...
		 * } else if (time > 5 && time < 10) {
		 * ...
		 * }
		 */
	}

	@Override
	public void autonomousExit() {
	}

	@Override
	public void teleopInit() {
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		m_robotContainer.m_innerElevator.config();
		m_robotContainer.m_outerElevator.config();
		m_robotContainer.m_remote.config();
	}

	@Override
	public void teleopPeriodic() {

		m_robotContainer.m_remote.mainloop();
		double elevatorTarget = m_robotContainer.m_remote.getElevatorTarget();
		IntakeWheelMode intakeWheelMode = m_robotContainer.m_remote.getIntakeWheelMode();
		IntakeArmMode intakeArmMode = m_robotContainer.m_remote.getIntakeArmMode();
		ShooterMode shooterMode = m_robotContainer.m_remote.getShooterMode();

		SmartDashboard.putString("input_shooterMode", shooterMode.toString());
		SmartDashboard.putString("input_intakeWheelMode", intakeWheelMode.toString());
		SmartDashboard.putString("input_intakeArmMode", intakeArmMode.toString());
		SmartDashboard.putNumber("input_elevatorTarget", elevatorTarget);
		SmartDashboard.putBoolean("input_elevatorManual", m_robotContainer.m_remote.getElevatorManual());

		// Check ultrasonic sensor
		// m_robotContainer.m_ultrasonicSensor.measureDistance();
		// double ultrasonicDistance =
		// m_robotContainer.m_ultrasonicSensor.getDistanceCm();
		// boolean objectSeen = ultrasonicDistance <
		// Constants.SubsystemConstants.Other.ULTRASONIC_DETECTION_THRESHOLD_CM;

		// Inner Elevator PID Tuning
		// if (driverJoystickDef.getYButton()) {
		// m_robotContainer.m_innerElevator.config();
		// }

		// Outer Elevator
		m_robotContainer.m_outerElevator.setEnabled(true);
		boolean outerElevatorWorking = m_robotContainer.m_outerElevator.mainloop(elevatorTarget);

		// Inner Elevator
		m_robotContainer.m_innerElevator.setEnabled(false);
		// m_robotContainer.m_innerElevator.setTargetPos(0.40);
		m_robotContainer.m_innerElevator.mainloop(elevatorTarget, !outerElevatorWorking);

		// Shooter
		m_robotContainer.m_shooter.mainloop(shooterMode);

		// Intake wheel
		m_robotContainer.m_intakeWheels.mainloop(intakeWheelMode);

		// Intake arm
		m_robotContainer.m_intakeArm.mainloop(intakeArmMode);
	}

	@Override
	public void teleopExit() {}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {}

	@Override
	public void testExit() {}

	@Override
	public void simulationPeriodic() {}
}
