import axios from "axios";
import AuthService from "./AuthService";

const clinic_url = "http://34.125.26.36:9999/proxy-appointment";
class ClinicService {
  getClinicList(selectedHospital) {
    return axios.get(`${clinic_url}/getClinicsByHospitalName`, {
      headers: {
        Authorization: `Bearer ${AuthService.getToken()}`,
      },
      params: {
        hospitalName: selectedHospital,
      },
    });
  }
}

export default new ClinicService();
