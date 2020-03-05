package frc.robot;

// import org.usfirst.frc.team2078.robot.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.CameraServer;
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
	Talon intake = new Talon(6);

	SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, rearLeft);
	SpeedControllerGroup right = new SpeedControllerGroup(frontRight, rearRight);
	DifferentialDrive drive = new DifferentialDrive(left, right);
		
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
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

	}
	public void teleopInit(){

 	}
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		drive.tankDrive(-(xbox.getRawAxis(1)), -(xbox.getRawAxis(5)));

		// Flywheel on
		if (xbox.getRawButton(1)) {     //A  turn on flywheel
			flyWHeel.set(0.65);
		}

		// Flywheel off
		if (xbox.getRawButton(2)) {      //B  turn off flywheel
			flyWHeel.set(0);
		}

		// Index on
		if (xbox.getRawButton(3))  		//X  turn on index
		{
			index.set(.5);
		}

		// Index off
		if (xbox.getRawButton(4))		//Y turn off index
		{
			index.set(0);
		}

		// Intake on
		if (xbox.getRawAxis(3) >= 0.5) {		//RT turn on intake
			intake.set(.5);
		}

		// Intake off
		if (xbox.getRawButton(6)) {     //RB turn off intake
			intake.set(0);
		}

		System.out.println(intake.get());

		double power = flyWHeel.get();

		if (xbox.getRawAxis(6) >= 0.5) {     //left bumper increase speed
			if (power < .66)				//max speed 
			{
				flyWHeel.set(power + .02);
			}
		}

		if (xbox.getRawAxis(6) >= 0.5) {    //right bumper decrease speed
			if (power > 0) {			    //min speed 
			flyWHeel.set(power - .02);
			}
		}


	}


	public double fixController(double val) {

		

		if (val >= 0.5 || val <= -0.5) {
			System.out.println(val);
			return val;
		} else {
			System.out.println("0.0");
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

