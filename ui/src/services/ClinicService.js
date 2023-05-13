import axios from "axios";
import AuthService from "./AuthService";

const clinic_url = "http://93.177.102.198:9999/proxy-clinic";
class ClinicService {
  getClinicList(selectedHospital) {
    return axios.get(`${clinic_url}/getClinicListByHospitalName`, {
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
