import axios from "../axios";
import { BehaviorSubject } from "rxjs";

const authInfoSubject = new BehaviorSubject(
  JSON.parse(localStorage.getItem("authInfo"))
);

const login = (userCredentials) => {
  console.log('asasdfsadf')
  return axios
    .post("/users/login", userCredentials)
    // .then(handleResponse)
    .then((rs) => {
      console.log('received token, ', rs.data)
      localStorage.setItem("authInfo", JSON.stringify(rs.data));
      authInfoSubject.next(rs.data);
      return rs;
    }, err => {
      console.log(err.response)
      return Promise.reject(err.response.data.details)
    });
};

const logout = () => {
  localStorage.removeItem("authInfo");
  authInfoSubject.next(null);
};

export const authenticationService = {
  login,
  logout,
  authInfo: authInfoSubject.asObservable(),
  get authInfoValue() {
    return authInfoSubject.value;
  },
};

