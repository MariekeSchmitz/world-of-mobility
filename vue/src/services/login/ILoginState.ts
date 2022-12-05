/**
 * interface for global User State
 * @author Marie Bohnert
 * @author Tom Gouthier
 * */
export interface ILoginState {
  username: string;
  avatar: string;
  error: string;
  loggedIn: boolean;
}
