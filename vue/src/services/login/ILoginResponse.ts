/**
 * interface that mirrors dto sent by server as reponse to login attempt
 * @author Tom Gouthier
 * @author Marie Bohnert
 */
export interface ILoginResponse {
  username: string;
  error: string;
}
