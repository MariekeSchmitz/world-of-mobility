/**
 * A Dictionary to translate a orientation as string to its radian amount.
 */
export const orientations: { [id: string]: number } = {
  NORTH: 0,
  NORTH_EAST: 1 * (Math.PI / 4),
  EAST: 2 * (Math.PI / 4),
  SOUTH_EAST: 3 * (Math.PI / 4),
  SOUTH: 4 * (Math.PI / 4),
  SOUTH_WEST: 5 * (Math.PI / 4),
  WEST: 6 * (Math.PI / 4),
  NORTH_WEST: 7 * (Math.PI / 4),
};
