/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.robot.commands;

import com.techhounds.robot.subsystems.RobotParts;
import com.techhounds.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author 2014 System
 */
public class IncrementShooterSpeed extends Command {
    double increment;
    ShooterSubsystem shooter;
    public IncrementShooterSpeed(double increment) {
        shooter = RobotParts.getInstance().getShooter();
        this.increment = increment;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        double power = increment + shooter.getPower();
        shooter.setPower(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
