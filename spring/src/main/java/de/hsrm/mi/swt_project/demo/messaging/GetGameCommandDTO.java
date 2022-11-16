package de.hsrm.mi.swt_project.demo.messaging;

import de.hsrm.mi.swt_project.demo.controls.GameControl;

/**
 * Data Transfer Object that the Server receives by the Client on every game command by a user
 * @author Tom Gouthier
 */
public record GetGameCommandDTO(String user, GameControl control) {}

