// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.ControllerConstants.driverJoystickDef;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.OuterElevator;

import frc.robot.subsystems.Remote;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  public Robot() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); 
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    m_robotContainer.m_innerElevator.config();
    m_robotContainer.m_outerElevator.config();
  }

  @Override
  public void teleopPeriodic() {
    // Check ultrasonic sensor
    m_robotContainer.m_ultrasonicSensor.measureDistance();
    double ultrasonicDistance = m_robotContainer.m_ultrasonicSensor.getDistanceCm();
    boolean objectSeen = ultrasonicDistance < Constants.SubsystemConstants.Other.ULTRASONIC_DETECTION_THRESHOLD_CM;
    

    Remote.mainloop();
    // Inner Elevator PID Tuning
   // if (driverJoystickDef.getYButton()) {
     // m_robotContainer.m_innerElevator.config();
    //}
    // Inner Elevator
    m_robotContainer.m_innerElevator.setEnabled(false);
    //m_robotContainer.m_innerElevator.setTargetPos(0.40);
    m_robotContainer.m_innerElevator.mainloop();
    
    // Outer Elevator
    m_robotContainer.m_outerElevator.setEnabled(false);
    m_robotContainer.m_outerElevator.setTargetPos(0.40);
    m_robotContainer.m_outerElevator.mainloop();

    // Shooter
    m_robotContainer.m_shooter.mainloop(objectSeen);
    
    // Intake wheel
    m_robotContainer.m_intakeWheels.mainloop();
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
