//region_Copyright

  /*----------------------------------------------------------------------------*/
  /* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
  /* Open Source Software - may be modified and shared by FRC teams. The code   */
  /* must be accompanied by the FIRST BSD license file in the root directory of */
  /* the project.                                                               */
  /*----------------------------------------------------------------------------*/

//endregion

package frc.robot;

//region_Imports

  //regular imports
    import edu.wpi.first.wpilibj.*;
    import edu.wpi.first.wpilibj.GenericHID.Hand;
    import edu.wpi.first.wpilibj.GenericHID.RumbleType;
    import edu.wpi.first.networktables.*;
    import edu.wpi.first.wpilibj.smartdashboard.*;
    import edu.wpi.first.wpilibj.drive.*;
    import edu.wpi.first.wpilibj.SpeedControllerGroup.*;

  //spark max/neos imports
    import com.revrobotics.*;
    import com.revrobotics.CANSparkMaxLowLevel.MotorType;

  //navx imports
    import com.kauailabs.navx.frc.*;

  //endregion

public class Robot extends TimedRobot {

  //region_Variables

    //joysticks
      public Joystick j_Left = new Joystick(0);
      public Joystick j_Right = new Joystick(1);
      public Joystick j_Operator = new Joystick(2);
      public XboxController j_XboxController = new XboxController(4);

    //neos
      public CANSparkMax m_Left1 = new CANSparkMax(12, MotorType.kBrushless);
      public CANSparkMax m_Left2 = new CANSparkMax(13, MotorType.kBrushless);
      public CANSparkMax m_Right1 = new CANSparkMax(1, MotorType.kBrushless);
      public CANSparkMax m_Right2 = new CANSparkMax(2, MotorType.kBrushless);
      public CANSparkMax m_Intake = new CANSparkMax(6, MotorType.kBrushless); //negative power for in, positive power for out
      public CANSparkMax m_Feeder = new CANSparkMax(7, MotorType.kBrushless); //positive power for in, negative power for out
      public CANSparkMax m_Tilting = new CANSparkMax(5, MotorType.kBrushless); //positive power for up, negative power for down
      public CANSparkMax m_TopShooter = new CANSparkMax(11, MotorType.kBrushless); //positive power for out
      public CANSparkMax m_BotShooter = new CANSparkMax(10, MotorType.kBrushless); //negative power for out
      public CANSparkMax m_ControlPanel = new CANSparkMax(8, MotorType.kBrushless); //when facing robot's control panel wheel from front of bot, positive power spins ccw and negative power spins cw
      public CANSparkMax m_Climb = new CANSparkMax(3, MotorType.kBrushless);
      public CANSparkMax m_LeftWinch = new CANSparkMax(9, MotorType.kBrushless);
      public CANSparkMax m_RightWinch = new CANSparkMax(4, MotorType.kBrushless);

    //neo encoders
      public CANEncoder e_Left1 = m_Left1.getEncoder(); //positive forward for Left
      public CANEncoder e_Left2 = m_Left2.getEncoder();
      public CANEncoder e_Right1 = m_Right1.getEncoder(); //negative forward for right
      public CANEncoder e_Right2 = m_Right2.getEncoder();
      public CANEncoder e_Intake = m_Intake.getEncoder(); //negative when intaking
      public CANEncoder e_Feeder = m_Feeder.getEncoder(); //positive when intaking
      public CANEncoder e_Tilting = m_Tilting.getEncoder(); //negative when leaning back
      public CANEncoder e_TopShooter = m_TopShooter.getEncoder(); //positive when shooting ball out
      public CANEncoder e_BotShooter = m_BotShooter.getEncoder(); //negative when shooting ball out
      public CANEncoder e_ControlPanel = m_ControlPanel.getEncoder(); //positive when ccw, negative when cw
      public CANEncoder e_Climb = m_Climb.getEncoder();
      public CANEncoder e_LeftWinch = m_LeftWinch.getEncoder();
      public CANEncoder e_RightWinch = m_RightWinch.getEncoder();



    //neo pidcontrollers
      public CANPIDController pc_Left1 = m_Left1.getPIDController();
      public CANPIDController pc_Left2 = m_Left2.getPIDController();
      public CANPIDController pc_Right1 = m_Right1.getPIDController();
      public CANPIDController pc_Right2 = m_Right2.getPIDController();
      public CANPIDController pc_Intake = m_Intake.getPIDController();
      public CANPIDController pc_Feeder = m_Feeder.getPIDController();
      public CANPIDController pc_Tilting = m_Tilting.getPIDController();
      public CANPIDController pc_TopShooter = m_TopShooter.getPIDController();
      public CANPIDController pc_BotShooter = m_BotShooter.getPIDController();
      public CANPIDController pc_ControlPanel = m_ControlPanel.getPIDController();
      public CANPIDController pc_Climb = m_Climb.getPIDController();
      public CANPIDController pc_LeftWinch = m_LeftWinch.getPIDController();
      public CANPIDController pc_RightWinch = m_RightWinch.getPIDController();



    //neo controllers
      public SpeedControllerGroup m_Left = new SpeedControllerGroup(m_Left1, m_Left2);
      public SpeedControllerGroup m_Right = new SpeedControllerGroup(m_Right1, m_Right2);
      public DifferentialDrive m_DriveTrain = new DifferentialDrive(m_Left, m_Right); //negative power makes bot move forward, positive power makes bot move packwards

    //tuning variables
      public double kP_Left1, kI_Left1, kD_Left1, kIz_Left1, kFF_Left1;
      public double kP_Left2, kI_Left2, kD_Left2, kIz_Left2, kFF_Left2;
      public double kP_Right1, kI_Right1, kD_Right1, kIz_Right1, kFF_Right1;
      public double kP_Right2, kI_Right2, kD_Right2, kIz_Right2, kFF_Right2;
      public double kP_Feeder, kI_Feeder, kD_Feeder, kIz_Feeder, kFF_Feeder;
      public double kP_Tilting, kI_Tilting, kD_Tilting, kIz_Tilting, kFF_Tilting;
      public double kP_TopShooter, kI_TopShooter, kD_TopShooter, kIz_TopShooter, kFF_TopShooter;
      public double kP_BotShooter, kI_BotShooter, kD_BotShooter, kIz_BotShooter, kFF_BotShooter;
      public double kP_ControlPanel, kI_ControlPanel, kD_ControlPanel, kIz_ControlPanel, kFF_ControlPanel;

    //solenoid variables
      public Solenoid s_LeftIntake = new Solenoid(7);
      public Solenoid s_RightIntake = new Solenoid(5);
      public Solenoid s_ControlPanel = new Solenoid(6);

    //navx variables
      public AHRS navX = new AHRS(SPI.Port.kMXP);
      public float imu_Yaw;

    //vision variables
      public NetworkTableInstance ntwrkInst = NetworkTableInstance.getDefault();
      public NetworkTable visionTable;
      public NetworkTable chameleonVision;
      public double chameleon_Yaw;
      public double chameleon_Pitch;

    //sensors
      public DigitalInput interruptSensor = new DigitalInput(1);
      public Counter lidarSensor = new Counter(9);
      final double off  = 10; //offset for sensor. test with tape measure
      public double dist;

    //logic variables

      //gear switching
        public boolean lowGear=true;
        public boolean switchGears;
      
      //intake booleans
        public boolean intakeExtended = false;

      //ball counting variables
        public boolean oldBallBoolean = false;
        public boolean newBallBoolean = false;
        public boolean ballDebounceBoolean = false;
        public int ballCounter = 0;

      //shooting booleans
        public boolean readyToFeed = false;

  //endregion
 
  @Override
  public void robotInit() {
    e_Tilting.setPosition(0);
    m_Left.setInverted(true);
    m_Right.setInverted(false);
    lidarSensor.setMaxPeriod(1.00); //set the max period that can be measured
    lidarSensor.setSemiPeriodMode(true); //Set the counter to period measurement
    lidarSensor.reset();

    //region_SettingPidVariables
      kP_Left1 = .0001;
      kI_Left1 = 0;
      kD_Left1 = 0.01;
      kIz_Left1 = 0;
      kFF_Left1 = .0001746724891;

      kP_Left2 = .0001;
      kI_Left2 = 0;
      kD_Left2 = 0.01;
      kIz_Left2 = 0;
      kFF_Left2 = .0001746724891;
      
      kP_Right1 = .0001;
      kI_Right1 = 0;
      kD_Right1 = 0.01;
      kIz_Right1 = 0;
      kFF_Right1 = .0001746724891;
      
      kP_Right2 = .0001;
      kI_Right2 = 0;
      kD_Right2 = 0.01;
      kIz_Right2 = 0;
      kFF_Right2 = .0001746724891;
        
      kP_Feeder = .5;
      kI_Feeder = 0;
      kD_Feeder = 0;
      kIz_Feeder = 0;
      kFF_Feeder = 0;
      
      kP_Tilting = 1;
      kI_Tilting = 0;
      kD_Tilting = 0;
      kIz_Tilting = 0;
      kFF_Tilting = 0;
      
      kP_TopShooter = .00025;
      kI_TopShooter = 0;
      kD_TopShooter = 0.01;
      kIz_TopShooter = 0;
      kFF_TopShooter = .00017969;
      
      kP_BotShooter = .00035;
      kI_BotShooter = 0;
      kD_BotShooter = .0001;
      kIz_BotShooter = 0;
      kFF_BotShooter = .00018501;
      
      kP_ControlPanel = 1;
      kI_ControlPanel = 0;
      kD_ControlPanel = 0;
      kIz_ControlPanel = 0;
      kFF_ControlPanel = 0;
      

    //endregion

    //region_SettingPidValues
      pc_Left1.setP(kP_Left1);
      pc_Left1.setI(kI_Left1);
      pc_Left1.setD(kD_Left1);
      pc_Left1.setIZone(kIz_Left1);
      pc_Left1.setFF(kFF_Left1);

      pc_Left2.setP(kP_Left2);
      pc_Left2.setI(kI_Left2);
      pc_Left2.setD(kD_Left2);
      pc_Left2.setIZone(kIz_Left2);
      pc_Left2.setFF(kFF_Left2);

      pc_Right1.setP(kP_Right1);
      pc_Right1.setI(kI_Right1);
      pc_Right1.setD(kD_Right1);
      pc_Right1.setIZone(kIz_Right1);
      pc_Right1.setFF(kFF_Right1);

      pc_Right2.setP(kP_Right2);
      pc_Right2.setI(kI_Right2);
      pc_Right2.setD(kD_Right2);
      pc_Right2.setIZone(kIz_Right2);
      pc_Right2.setFF(kFF_Right2);

      pc_Feeder.setP(kP_Feeder);
      pc_Feeder.setI(kI_Feeder);
      pc_Feeder.setD(kD_Feeder);
      pc_Feeder.setIZone(kIz_Feeder);
      pc_Feeder.setFF(kFF_Feeder);
      pc_Feeder.setOutputRange(-.69, .69);

      pc_Tilting.setP(kP_Tilting);
      pc_Tilting.setI(kI_Tilting);
      pc_Tilting.setD(kD_Tilting);
      pc_Tilting.setIZone(kIz_Tilting);
      pc_Tilting.setFF(kFF_Tilting);
      pc_Tilting.setOutputRange(-.5, .5);

      pc_TopShooter.setP(kP_TopShooter);
      pc_TopShooter.setI(kI_TopShooter);
      pc_TopShooter.setD(kD_TopShooter);
      pc_TopShooter.setIZone(kIz_TopShooter);
      pc_TopShooter.setFF(kFF_TopShooter);

      pc_BotShooter.setP(kP_BotShooter);
      pc_BotShooter.setI(kI_BotShooter);
      pc_BotShooter.setD(kD_BotShooter);
      pc_BotShooter.setIZone(kIz_BotShooter);
      pc_BotShooter.setFF(kFF_BotShooter);

      pc_ControlPanel.setP(kP_ControlPanel);
      pc_ControlPanel.setI(kI_ControlPanel);
      pc_ControlPanel.setD(kD_ControlPanel);
      pc_ControlPanel.setIZone(kIz_ControlPanel);
      pc_ControlPanel.setFF(kFF_ControlPanel);

    //endregion

  }


  @Override
  public void autonomousInit() {
      
  }

 
  @Override
  public void autonomousPeriodic() {
    m_Feeder.set(.5);
    SmartDashboard.putNumber("hi", j_Right.getY());
  }


  @Override
  public void teleopInit() {
    
  }


  @Override
  public void teleopPeriodic() {
    chameleonVision = ntwrkInst.getTable("chameleon-vision");
    visionTable = chameleonVision.getSubTable("VisionTable");
    chameleon_Yaw = visionTable.getEntry("targetYaw").getDouble(0);
    chameleon_Pitch = visionTable.getEntry("targetPitch").getDouble(0);

    //if/else series controlling drivetrain motors
    if (j_Right.getTrigger()){
      visionTracking();
    }
    else {
      joystickControl();
      gearSwitching();
    }

    //if/else series controlling intaking and shooting balls
    if (j_Operator.getRawButton(1)){
      intakingBalls();
      oldBallBoolean = newBallBoolean;
    }
    else if (j_Operator.getRawButton(2)) {
      shootingBalls();
    }
    else {
      intake();
      readyToFeed = false;
      m_BotShooter.setIdleMode(CANSparkMax.IdleMode.kBrake);
      m_TopShooter.setIdleMode(CANSparkMax.IdleMode.kBrake);
      m_TopShooter.stopMotor();
      m_BotShooter.stopMotor();
      m_Feeder.stopMotor();
    } 

    tiltingControl();
    ballCounterReset();
    lidarDistance();

    //region_SmartDashboard
      //values that are being put into smart dashboard
      SmartDashboard.putNumber("right joy", j_Right.getY());
      SmartDashboard.putNumber("left joy", j_Left.getY());
      SmartDashboard.putBoolean("Do I see ball", interruptSensor.get());
      SmartDashboard.putNumber("right encoder 1 ", e_Right1.getPosition());
      SmartDashboard.putNumber("right encoder 2 ", e_Right2.getPosition());
      SmartDashboard.putNumber("left encoder 1 ", e_Left1.getPosition());
      SmartDashboard.putNumber("left encoder 2 ", e_Left2.getPosition());
      SmartDashboard.putNumber("feeder position", e_Feeder.getPosition());
      SmartDashboard.putNumber("feeder velocity", e_Feeder.getVelocity());
      SmartDashboard.putNumber("ball counter", ballCounter);
      SmartDashboard.putNumber("top motor velocity", e_TopShooter.getVelocity());
      SmartDashboard.putNumber("bot motor velocity", e_BotShooter.getVelocity());
      SmartDashboard.putNumber("tilting encoder", e_Tilting.getPosition());
      SmartDashboard.putNumber("Chameleon Yaw", chameleon_Yaw);
      SmartDashboard.putNumber("Distance", dist);
      SmartDashboard.putBoolean("Lowgear", lowGear);
      if (chameleon_Yaw > -2 && chameleon_Yaw < 2){
        SmartDashboard.putBoolean("Aligned", true);
      }
      else {
        SmartDashboard.putBoolean("Aligned", false);
      }

    //endregion
  }
  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    chameleonVision = ntwrkInst.getTable("chameleon-vision");
    visionTable = chameleonVision.getSubTable("VisionTable");
    chameleon_Yaw = visionTable.getEntry("yaw").getDouble(0);
    chameleon_Pitch = visionTable.getEntry("pitch").getDouble(0);

    if (j_Operator.getRawButton(7)) {
      m_BotShooter.setIdleMode(CANSparkMax.IdleMode.kCoast);
      m_TopShooter.setIdleMode(CANSparkMax.IdleMode.kCoast);

      if (e_BotShooter.getVelocity() > -5350){
        m_BotShooter.set(-1);
      }
      else {
        m_BotShooter.set(0);
        readyToFeed = true;
      }

      if ( e_TopShooter.getVelocity() < 5350){
        m_TopShooter.set(1);
      }
      else {
        m_TopShooter.set(0);
      }

      if (readyToFeed = true){
        m_Feeder.set(.61);
      }

      else {
        m_Feeder.stopMotor();
      }

    }
    else {
      readyToFeed = false;
      m_BotShooter.setIdleMode(CANSparkMax.IdleMode.kBrake);
      m_TopShooter.setIdleMode(CANSparkMax.IdleMode.kBrake);
      m_TopShooter.stopMotor();
      m_BotShooter.stopMotor();
      m_Feeder.set(j_Operator.getY());
      intake();

    }

    if (j_Operator.getRawButton(1)){

      if (chameleon_Yaw < -2) {
        pc_Right1.setReference(-500, ControlType.kVelocity);
        pc_Right2.setReference(-500, ControlType.kVelocity);
        pc_Left1.setReference(-500, ControlType.kVelocity);
        pc_Left2.setReference(-500, ControlType.kVelocity);

      }

      else if (chameleon_Yaw > 2) {
        pc_Right1.setReference(500, ControlType.kVelocity);
        pc_Right2.setReference(500, ControlType.kVelocity);
        pc_Left1.setReference(500, ControlType.kVelocity);
        pc_Left2.setReference(500, ControlType.kVelocity);
      }

      else {
        m_Left1.stopMotor();
        m_Left2.stopMotor();
        m_Right1.stopMotor();
        m_Right2.stopMotor();
      }

    }
    
    SmartDashboard.putNumber("top motor velocity", e_TopShooter.getVelocity());
    SmartDashboard.putNumber("bot motor velocity", e_BotShooter.getVelocity());
    SmartDashboard.putNumber("tilting encoder", e_Tilting.getPosition());
    SmartDashboard.putNumber("Chameleon Yaw", chameleon_Yaw);

  }

  //region_Methods
    
    public void joystickControl(){ //method for implementing our lowgear/highgear modes into our driver controls
      if(lowGear){
        m_DriveTrain.tankDrive(j_Left.getY() * .7, -j_Right.getY() * .7);
        //m_DriveTrain.tankDrive(j_XboxController.getY(Hand.kLeft)*.75, -j_XboxController.getY(Hand.kRight)*.75);
      }
      else{
        m_DriveTrain.tankDrive(j_Left.getY(), -j_Right.getY());
        //m_DriveTrain.tankDrive(j_XboxController.getY(Hand.kLeft), -j_XboxController.getY(Hand.kRight));
      }
    }

    public void gearSwitching(){ //method for switching our bot to lowgear(less sensitive) or highgear(speedyboi)
      if(j_Right.getRawButton(2) && switchGears || j_XboxController.getTriggerAxis(Hand.kLeft) >.5 && switchGears ){
        if(lowGear){
          lowGear = false;
          switchGears = false;
        }
        else{
          lowGear = true;
          switchGears = false;
        }
      }
      else if(j_Right.getRawButton(2) || j_XboxController.getTriggerAxis(Hand.kLeft) > .5){
        switchGears = true;
      }
    }

    public void intake(){ //method for spinning our intake and for ejecting it
      if(j_Operator.getRawButton(1)){
        intakeExtended = true;
      }
      else{
        if(j_Operator.getRawButton(5)){
          m_Intake.set(-1);
        }
        else{
          m_Intake.set(0);
        }
      }
      if(intakeExtended){
        s_LeftIntake.set(true);
        s_RightIntake.set(true);
        if(j_Operator.getRawButton(3)){
          intakeExtended = false;
        }
      }
      else{
        s_LeftIntake.set(false);
        s_RightIntake.set(false);
      }
    }    

    public void visionTracking() {
      if (chameleon_Yaw < -2) {
        pc_Right1.setReference(-500, ControlType.kVelocity);
        pc_Right2.setReference(-500, ControlType.kVelocity);
        pc_Left1.setReference(-500, ControlType.kVelocity);
        pc_Left2.setReference(-500, ControlType.kVelocity);

      }

      else if (chameleon_Yaw > 2) {
        pc_Right1.setReference(500, ControlType.kVelocity);
        pc_Right2.setReference(500, ControlType.kVelocity);
        pc_Left1.setReference(500, ControlType.kVelocity);
        pc_Left2.setReference(500, ControlType.kVelocity);
      }

      else {
        m_Left1.stopMotor();
        m_Left2.stopMotor();
        m_Right1.stopMotor();
        m_Right2.stopMotor();
      }
    }

    public void intakingBalls() {
      newBallBoolean = interruptSensor.get();
      if(oldBallBoolean != newBallBoolean && newBallBoolean == true && ballDebounceBoolean == false){
        //j_XboxController.setRumble(RumbleType.kLeftRumble, .5);
        ballCounter++;
        if(ballCounter < 3) {
          e_Feeder.setPosition(0);
          pc_Feeder.setReference(120, ControlType.kPosition);
        }
        else if (ballCounter >= 3 && ballCounter < 5){
          e_Feeder.setPosition(0);
          pc_Feeder.setReference(120, ControlType.kPosition);
        }
      }
      else if (newBallBoolean == true){
        m_Intake.set(.25);
      }
      else if (ballDebounceBoolean == true){
        ballDebounceBoolean = false;
      }

      else if (oldBallBoolean == true && newBallBoolean == false){
        ballDebounceBoolean = true;
      }

      else{
        m_Intake.set(1);
        //j_XboxController.setRumble(RumbleType.kLeftRumble, 0);
      }

      if (ballCounter > 3){
        Timer.delay(.5);
        intakeExtended = false;
        s_LeftIntake.set(false);
        s_RightIntake.set(false);
      }
      else {
        intakeExtended = true;
        s_LeftIntake.set(true);
        s_RightIntake.set(true);
      }
    }
    
    public void shootingBalls() {
      ballCounter = 0;
      m_BotShooter.setIdleMode(CANSparkMax.IdleMode.kCoast);
      m_TopShooter.setIdleMode(CANSparkMax.IdleMode.kCoast);
      if (e_BotShooter.getVelocity() > -5350){
        m_BotShooter.set(-1);
      }
      else {
        m_BotShooter.set(0);
        readyToFeed = true;
      }

      if ( e_TopShooter.getVelocity() < 5350){
        m_TopShooter.set(.69);
      }
      else {
        m_TopShooter.set(0);
      }

      if (readyToFeed = true){
      m_Feeder.set(1);
      }

      else {
        m_Feeder.stopMotor();
      }
    }
  
    public void tiltingControl() {
      if (j_Operator.getRawButton(9)){
        pc_Tilting.setReference(67, ControlType.kPosition);
      }
  
      else if (j_Operator.getRawButton(8)){
        pc_Tilting.setReference(0, ControlType.kPosition);
      }
    }
    
    public void ballCounterReset() {  
      if (j_Operator.getRawButton(4)){
        ballCounter = 0;
      }
    }
    
    public void lidarDistance() {
      if(lidarSensor.get() < 1){
        dist = 0;
      }
      else{
        dist = (lidarSensor.getPeriod()*1000000.0/10.0) - off; //convert to distance. sensor is high 10 us for every centimeter. 
      }
    }
    //endregion

}




