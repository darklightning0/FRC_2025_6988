package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.Slot0Configs;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.util.Util;


public class OuterElevator {
    private final TalonSRX motor = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.Elevator_Outer);

    private final double maxPos = 0.80; // 0.80 meters

    // private final Encoder innerEncoder = new Encoder(null, null);
    private final Encoder encoder = new Encoder(8, 9);

    private final DigitalInput bottomLimitSwitch = new DigitalInput(5);

    // private static SimplePID innerPid = new SimplePID("elevatorInner", 12);
    // private static SimplePID outerPid = new SimplePID("elevatorOuter", 12);

    double targetPos = 0.00;
    boolean motorEnabled = false;

    public OuterElevator() {}

    public void setTargetPos(double value) {
        targetPos = value;
    }

    public void setEnabled(boolean value) {
        motorEnabled = value;
    }

    // progress: 0 ~ 1
    public double calculateFactor(double progress) {
        return Util.lerp(Util.clamp(progress, 0., 1.), 1., 1.8);
    }

    public void config() {
        encoder.setDistancePerPulse(0.005 / 6.284 * 2. * Math.PI / 2048.);
        encoder.reset();

        motor.setNeutralMode(NeutralMode.Coast);
        motor.setInverted(true);
        // motor.config_kP(0, getPrefDouble("outerP", 0.01));
        // motor.config_kI(0, getPrefDouble("outerI", 0.00));
        // motor.config_kD(0, getPrefDouble("outerD", 0.00));
        // motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.SubsystemConstants.AMTEncoder.Elevator_Outer, 10);
        // motor.setSensorPhase(true);
    }

    double calculateOutput(double currentPos, boolean bottomSwitchReached) {
        if (motorEnabled && !bottomSwitchReached) {
            double factor = calculateFactor(currentPos / maxPos);
            // outerMotor.set(TalonSRXControlMode.PercentOutput, output);

            if (Math.abs(targetPos - currentPos) > 0.01) {
                if (currentPos < targetPos) {
                    // go up
                    return 0.60 * factor;
                } else {
                    // go down
                    return 0.35 * factor;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public void mainloop() {
        double currentPos = encoder.getDistance();
        boolean bottomSwitchReached = !bottomLimitSwitch.get();

        double output = calculateOutput(currentPos, bottomSwitchReached);
        motor.set(TalonSRXControlMode.PercentOutput, output);
        
        SmartDashboard.putNumber("elevatorOuterOutput", output);
        SmartDashboard.putBoolean("elevatorBottomSwitch", bottomSwitchReached);

        // double innerVoltage = innerPid.getSignal(innerCurrentPos);
        // innerMotor.set(ControlMode.PercentOutput, 0, DemandType.Neutral, innerVoltage);

        // outerMotor.set(ControlMode.Position, outerTargetPos);
        SmartDashboard.putNumber("elevatorEncoder", currentPos);
    }
}
