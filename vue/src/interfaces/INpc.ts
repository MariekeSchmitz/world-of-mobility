import type { NpcType } from "@/services/editor/NpcType"

export interface INpc {
    orientation: OrientationType,
    xPos: number,
    yPos: number,
    maxVelocity: number,
    capacity: number,
    currentVelocity: number,
    script: string,
    type: NpcType
}