import axios from "../axios";
import { BehaviorSubject } from "rxjs";
import { handleResponse } from "../helpers/handle-authorization";

const authInfoSubject = new BehaviorSubject(
  JSON.parse(localStorage.getItem("authInfo"))
);

const login = (userCredentials) => {
  console.log('asasdfsadf')
  return axios
    .post("/users/login", userCredentials)
    // .then(handleResponse)
    .then((rs) => {
      localStorage.setItem("authInfo", JSON.stringify(rs));
      authInfoSubject.next(rs);
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

