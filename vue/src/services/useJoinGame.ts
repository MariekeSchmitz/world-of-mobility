export function useJoinGame() {
  async function getMoveableTypes() {
    try {
      const controller = new AbortController();
      const URL = "/api/MovableTypes";
      const id = setTimeout(() => controller.abort(), 8000);
      const response = await fetch(URL);
      const jsonData: string[] = await response.json();
      clearTimeout(id);

      return jsonData;
    } catch (reason) {
      console.log(`ERROR: Fetching MoveableTypes failed: ${reason}`);
    }
  }

  return {
    getMoveableTypes,
  };
}
