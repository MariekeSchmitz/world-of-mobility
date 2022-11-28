// interface that mirrors dto sent by server as reponse to login attempt
export interface ILoginResponse {
  username: string;
  error: string;
}
