/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import edu.wpi.first.wpilibj2.command.*;

public class StartBWTrench3 extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartBWTrench3() {
                addCommands(new StartBWTrench(0.0)); // use extraDistance of 0 inches to stop before control panel
        }
}
