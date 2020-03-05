package frc.robot;

// import org.usfirst.frc.team2078.robot.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.cscore.UsbCamera;




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
	Talon flyWHeel = new Talon(4);
	Talon index = new Talon(5);

	SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, rearLeft);
	SpeedControllerGroup right = new SpeedControllerGroup(frontRight, rearRight);
	DifferentialDrive drive = new DifferentialDrive(left, right);

	Compressor mainCompressor = new Compressor(0);
	DoubleSolenoid Piston = new DoubleSolenoid(0, 1);

	
	RobotDrive arcadeDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	
	Joystick xbox = new Joystick(0);
	
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
		/*
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
		*/




		drive.tankDrive(fixController(-xbox.getRawAxis(1))*0.8, fixController(xbox.getRawAxis(5))*0.8);

		if (xbox.getRawButton(1)) {     //A  turn on flywheel
			flyWHeel.set(0.65);
		}

		if (xbox.getRawButton(2)) {      //B  turn off flywheel
			flyWHeel.set(0);
		}

		if (xbox.getRawButton(3))  		//X  turn on index
		{
			index.set(.5);
		}

		if (xbox.getRawButton(4))		//Y turn off index
		{
			index.set(0);
		}

		double power = flyWHeel.get();

		if (xbox.getRawButton(5)) {     //left bumper increase speed
			if (power < .66)				//max speed 
			{
				flyWHeel.set(power + .02);
			}
		}

		if (xbox.getRawButton(6)) {     //right bumper decrease speed
			flyWHeel.set(power - .02);
		}


	}


	public double fixController(double val) {

		System.out.println(val);

		if (val >= 0.5 || val <= 0.5) {
			return val;
		} else {
			return 0.0;
		}
		
	}


	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

