import axios from "axios";

const base_url = "http://34.125.26.36:1006/auth";

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
}

export default new AuthService();
