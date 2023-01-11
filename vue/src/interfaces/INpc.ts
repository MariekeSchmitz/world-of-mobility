import type { NpcType } from "@/services/editor/NpcType";

/**
 * interface that represents a NPC as sent by backend
 */
export interface INpc {
  orientation: OrientationType;
  xPos: number;
  yPos: number;
  maxVelocity: number;
  capacity: number;
  currentVelocity: number;
  script: string;
  type: NpcType;
}
