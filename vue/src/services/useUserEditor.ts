/**
 * Sends user to the backend to update userlist of editor instance
 * @returns if adding user worked or not
 * @author Astrid Klemmer
 */
export function useUserEditor(): any {
  /**
   * add User
   * @param instanceId id of editor instance
   * @param user name of user to be added
   * @returns if it worked
   */
  async function joinEditor(instanceId: number, user: string) {
    try {
      const controller = new AbortController();
      const URL = "/api/editor/" + instanceId + "/join-editor";

      const data = { user: user };

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify(data),
      });

      clearTimeout(id);

      if (!response.ok) {
        return false;
      }
      return true;
    } catch (reason) {
      console.log(`ERROR: Sending Command failed: ${reason}`);
      return false;
    }
  }

  /**
   * remove User
   * @param instanceId id of editor instance
   * @param user name of user to be removed
   * @returns if it worked
   * @author Astrid Klemmer
   */
  async function leaveEditor(instanceId: number, user: string) {
    try {
      const controller = new AbortController();
      const URL = "/api/editor/" + instanceId + "/leave-editor";

      const data = { user: user };

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify(data),
      });

      clearTimeout(id);

      if (!response.ok) {
        return false;
      }
      return true;
    } catch (reason) {
      console.log(`ERROR: Sending Command failed: ${reason}`);
      return false;
    }
  }

  return {
    joinEditor,
    leaveEditor,
  };
}
