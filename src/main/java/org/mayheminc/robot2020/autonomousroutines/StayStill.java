/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2020.autonomousroutines;

import edu.wpi.first.wpilibj2.command.*;
import org.mayheminc.robot2020.commands.DriveZeroGyro;

/**
 *
 * @author Team1519
 */
public class StayStill extends SequentialCommandGroup {

    public StayStill() {

        // Perform needed initialization
        addCommands(new DriveZeroGyro(0.0));

        // ALL DONE!
    }
}
