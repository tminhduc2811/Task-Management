import { authenticationService } from "../Services/AuthenticationService";

const handleResponse = (error) => {
  console.log("ahhhh", error.response);
  if (error.response) {
    if ([401, 403].indexOf(error.response.data.status) !== -1) {
      authenticationService.logout();
    }
  }
};

export default handleResponse;
