<template>
  <p>
    Um Skripte sinnvoll zu integrieren, steht eine API bereit, die über die
    Variable npc genutzt werden kann. Folgende Schnittstellen können genutzt
    werden:
  </p>
  <ul>
    <li>turnLeft(): Dreht das Objekt gegen den Uhrzeigersinn</li>
    <li>turnRight(): Dreht das Objekt im Uhrzeigersinn</li>
    <li>accelerate(): Erhöht die Geschwindigkeit des Objekts</li>
    <li>brake(): Verringert die Geschwindigkeit des Objekts</li>
    <li>emergencyBrake(): Setzt die Geschwindigkeit des Objekts auf 0</li>
    <li>surroundingTiles(): Gibt die Kacheln zurück, die das Objekt umgeben</li>
    <li>
      nearbyRoadUsers(): Gibt die beweglichen Objekte zurück, die das Objekt
      umgeben
    </li>
    <li>
      distanceTo(other): Gibt den Abstand des Objekts zu einem anderen zurück
    </li>
  </ul>
</template>
