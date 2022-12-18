async function getMoveableTypes() {
    try {
        const controller = new AbortController();
        const URL = '/api/MoveableTypes';
        const id = setTimeout(() => controller.abort(), 8000);
        const response = await fetch(URL);
        const jsonData: String[] = await response.json();
        clearTimeout(id);
        console.log(response.text());

        return jsonData;
    } catch(reason) {
        console.log(`ERROR: Fetching MoveableTypes failed: ${reason}`);
    }
}

export function useJoinGame() {
    return {
      getMoveableTypes
    };
}
