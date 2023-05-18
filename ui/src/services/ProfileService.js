import axios from "axios";
import AuthService from "./AuthService";

const base_url = "http://93.177.102.198:9999/proxy-patient";
class ProfileService {
  getAppointmentList() {
    return axios.get(`${base_url}/getActivePatientAppointmentList`, {
      headers: {
        Authorization: `Bearer ${AuthService.getToken()}`,
      },
    });
  }
}
export default new ProfileService();
