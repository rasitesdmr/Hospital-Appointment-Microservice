import axios from "axios";

const base_url = "http://93.177.102.198:9999/proxy-auth";

class AuthService {
  setToken(token) {
    localStorage.setItem("authToken", token);
  }

  getToken() {
    return localStorage.getItem("authToken");
  }
  loginUser(user) {
    return axios.post(`${base_url}/login`, user);
  }
  registerUser(user) {
    return axios.post(`${base_url}/patientRegister`, user);
  }
}

export default new AuthService();
