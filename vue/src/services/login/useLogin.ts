import { reactive, readonly } from "vue";
import type { ILoginState } from "./ILoginState";

const loginState: ILoginState = reactive({
  username: "",
  avatar: "src/assets/avatar/Schwein.png",
  error: "",
  loggedIn: false,
});

const setAvatar = (avatar: string) => {
  loginState.avatar = avatar;
  console.log(avatar);
};

const login = (username: string) => {
  if (username.length >= 3) {
    loginState.username = username;
    loginState.loggedIn = true;
    console.log("logged in as", username);
  } else {
    loginState.error = "Name too Short";
  }
};

const logout = () => {
  loginState.username = "";
  loginState.loggedIn = false;
};

export function useLogin() {
  return {
    loginData: readonly(loginState),
    setAvatar,
    login,
    logout,
  };
}
