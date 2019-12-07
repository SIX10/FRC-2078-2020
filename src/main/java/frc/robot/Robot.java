package frc.robot;

// import org.usfirst.frc.team2078.robot.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import java.util.concurrent.TimeUnit;;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
@SuppressWarnings("deprecation")
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	Talon frontRight = new Talon(2);
	Talon frontLeft = new Talon(3);
	Talon rearRight = new Talon(1);
	Talon rearLeft = new Talon(0);
	Talon frontStrafeMotor = new Talon(4);
	Victor rearStrafeMotor = new Victor(5);
	Victor leftLiftMotor = new Victor(6);
	Jaguar rightLiftMotor = new Jaguar(7);
	Jaguar leftRollerMotor = new Jaguar(8);
	Victor rightRollerMotor = new Victor(9);

	Compressor mainCompressor = new Compressor(0);
	DoubleSolenoid Piston = new DoubleSolenoid(0, 1);
	
	private static final int pistonTrigger = 1;
	private static final int liftButton=3;
	private static final int lowerButton=2;
	private static final int compressorOff=10;
	private static final int compressorOn=11;
	private static final int rollerOut=6;
	private static final int rollerIn=7;
	private static final int switchForward=4;
	private static final int switchBackward=5;
	private static final int strafeRight=9;
	private static final int strafeLeft=8;

	
	RobotDrive arcadeDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	
	Joystick JS1 = new Joystick(0);
	
	double x;
	double y;
	
	CameraServer server;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(320, 240);

		//chooser.addDefault("Default Auto", defaultAuto);
		//chooser.addObject("My Auto", customAuto);
		//SmartDashboard.putData("Auto choices", chooser);
		//new Thread(() -> {
            //UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
            //camera.setResolution(640, 480);
            
            //CvSink cvSink = CameraServer.getInstance().getVideo();
            //CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
            
            //Mat source = new Mat();
            //Mat output = new Mat();
            
            //while(!Thread.interrupted()) {
                //cvSink.grabFrame(source);
                //Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
                //outputStream.putFrame(output);
            //}
        //}).start();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		arcadeDrive.setInvertedMotor(MotorType.kFrontRight, true);
		arcadeDrive.setInvertedMotor(MotorType.kFrontLeft, false);
		arcadeDrive.setInvertedMotor(MotorType.kRearRight, true);
		arcadeDrive.setInvertedMotor(MotorType.kRearLeft, false);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		x = JS1.getX();
		y = JS1.getY();
		arcadeDrive.arcadeDrive(x, y);
		
		if(JS1.getRawButton(liftButton)) {
			leftLiftMotor.set(-0.35);
			rightLiftMotor.set(0.15);
		} else if(JS1.getRawButton(lowerButton)) {
			leftLiftMotor.set(0.15);
			rightLiftMotor.set(0.075);
		} else {
			leftLiftMotor.set(-0.05);
			rightLiftMotor.set(0.025);
			}

		
		//mainCompressor.setClosedLoopControl(false);
		if(JS1.getRawButton(pistonTrigger)) {
			Piston.set(Value.kForward);
			Timer.delay(0.5);
			Piston.set(Value.kReverse);
		} else {
			Piston.set(DoubleSolenoid.Value.kOff);
		}
		
		if(JS1.getRawButton(compressorOn)) {
			mainCompressor.setClosedLoopControl(true);
		} else if (JS1.getRawButton(compressorOff)){
			mainCompressor.setClosedLoopControl(false);
		}	

		if(JS1.getRawButton(rollerOut)){
			leftRollerMotor.set(-1);
			rightRollerMotor.set(1);
		} else if(JS1.getRawButton(rollerIn)){
			leftRollerMotor.set(1);
			rightRollerMotor.set(-1);
		} else {
			leftRollerMotor.set(0);
			rightRollerMotor.set(0);
		}
		
		if(JS1.getRawButton(strafeLeft)){
			frontStrafeMotor.set(-0.25);
			rearStrafeMotor.set(0.25);
		} else if(JS1.getRawButton(strafeRight)){
			frontStrafeMotor.set(0.25);
			rearStrafeMotor.set(-0.25);
		} else {
			frontStrafeMotor.set(0);
			rearStrafeMotor.set(0);
		}
	}
 public void teleopInit(){
	 arcadeDrive.setInvertedMotor(MotorType.kFrontRight, true);
	 arcadeDrive.setInvertedMotor(MotorType.kFrontLeft, false);
	 arcadeDrive.setInvertedMotor(MotorType.kRearRight, true);
	 arcadeDrive.setInvertedMotor(MotorType.kRearLeft, false);
 }
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		x = JS1.getX();
		y = JS1.getY();
		arcadeDrive.arcadeDrive(x, y);
		
		if(JS1.getRawButton(liftButton)) {
			leftLiftMotor.set(-0.70); 
			rightLiftMotor.set(0.30);
		} else if(JS1.getRawButton(lowerButton)) {
			leftLiftMotor.set(0.15);
			rightLiftMotor.set(-0.15);
		} else {
			leftLiftMotor.set(-0.05);
			rightLiftMotor.set(0.025);
			}

		
		//mainCompressor.setClosedLoopControl(false);

		if(JS1.getRawButton(pistonTrigger)) {
			Piston.set(Value.kForward);
			Timer.delay(0.5);
			Piston.set(Value.kReverse);
		} else {
			Piston.set(DoubleSolenoid.Value.kOff);
		}
		
		if(JS1.getRawButton(compressorOn)) {
			mainCompressor.setClosedLoopControl(true);
		} else if (JS1.getRawButton(compressorOff)){
			mainCompressor.setClosedLoopControl(false);
		}	

		if(JS1.getRawButton(rollerOut)){
			leftRollerMotor.set(-0.5);
			rightRollerMotor.set(0.5);
		} else if(JS1.getRawButton(rollerIn)){
			leftRollerMotor.set(0.5);
			rightRollerMotor.set(-0.5);
		} else {
			leftRollerMotor.set(0);
			rightRollerMotor.set(0.05);
		}
		
		if(JS1.getRawButton(strafeLeft)){
			frontStrafeMotor.set(-0.25);
			rearStrafeMotor.set(0.25);
		} else if(JS1.getRawButton(strafeRight)){
			frontStrafeMotor.set(0.25);
			rearStrafeMotor.set(-0.25);
		} else {
			frontStrafeMotor.set(0);
			rearStrafeMotor.set(0);
		}

		if(JS1.getRawButton(switchForward)) {
			arcadeDrive.setInvertedMotor(MotorType.kFrontRight, true);
			arcadeDrive.setInvertedMotor(MotorType.kFrontLeft, false);
			arcadeDrive.setInvertedMotor(MotorType.kRearRight, true);
			arcadeDrive.setInvertedMotor(MotorType.kRearLeft, false);
		} else if(JS1.getRawButton(switchBackward)){
			arcadeDrive.setInvertedMotor(MotorType.kFrontRight, false);
			arcadeDrive.setInvertedMotor(MotorType.kFrontLeft, true);
			arcadeDrive.setInvertedMotor(MotorType.kRearRight, false);
			arcadeDrive.setInvertedMotor(MotorType.kRearLeft, true);
		}
	}


	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

