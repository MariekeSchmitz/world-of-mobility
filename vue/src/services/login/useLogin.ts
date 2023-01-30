import { reactive, readonly } from "vue";
import type { IAvatarState } from "../../interfaces/login/IAvatarState";
import type { ILoginResponse } from "../../interfaces/login/ILoginResponse";
import type { ILoginState } from "../../interfaces/login/ILoginState";
import type { ISendLogin } from "../../interfaces/login/ISendLogin";
import { avatarImages } from "@/components/User/AvatarURLDict";


/**
 * global State for user
 * @author Tom Gouthier
 * @author Marie Bohnert
 */
const loginState: ILoginState = reactive({
  username: "",
  error: "",
  loggedIn: false,
});

const avatarState: IAvatarState = reactive({
  avatar: avatarImages["GOCKEL"],
});

/**
 * @author Tom Gouthier,
 * @author Marie Bohnert
 * sets Avatar to certain image url
 */

const setAvatar = (avatar: string) => {
  avatarState.avatar = "";
  avatarState.avatar = avatar;
};

/**
 * @author Tom Gouthier
 * @author Marie Bohnert
 * sends login request to server
 * if successfull, sets state accordingly
 */

async function login(username: string) {
  if (loginState.loggedIn) {
    return;
  }
  const login: ISendLogin = {
    name: username,
  };

  const url = "/api/user/login";
  try {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(login),
    });

    // if response is not 200, set error state to error message in error response (403 if not unique or username too short)
    if (!response.ok) {
      const jsondata = await response.json();
      loginState.error = jsondata.message;
    } else {
      // if reponse is ok (200) set state accordingly
      const jsondata: ILoginResponse = await response.json();
      loginState.error = "";
      loginState.username = jsondata.username;
      loginState.loggedIn = true;
    }
  } catch (reason: any) {
    loginState.error = reason;
  }
}

/**
 * @author Tom Gouthier
 * @author Marie Bohnert
 * logout and reset State
 */
async function logout() {
  if (!loginState.loggedIn) {
    loginState.error = "Not logged In";
    return;
  }
  const url = `/api/user/logout?username=${loginState.username}`;
  try {
    const response = await fetch(url, {
      method: "DELETE",
    });
    if (!response.ok) {
      throw new Error(response.statusText);
    } else {
      loginState.username = "";
      loginState.loggedIn = false;
      loginState.error = "";
    }
  } catch (error: any) {
    loginState.error = error;
  }
}

export function useLogin() {
  return {
    loginData: readonly(loginState),
    setAvatar,
    login,
    logout,
    avatarData: readonly(avatarState),
    loginDataError: readonly(loginState)
  };
}
