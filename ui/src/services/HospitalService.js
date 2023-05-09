import axios from "axios";
import AuthService from "./AuthService";

const hospital_url = "http://34.125.26.36:9999/proxy-appointment";

class HospitalService {
  getHospitalList(selectedCity) {
    return axios.get(`${hospital_url}/getHospitalsByCityName`, {
      headers: {
        Authorization: `Bearer ${AuthService.getToken()}`,
      },
      params: {
        cityName: selectedCity,
      },
    });
  }
}

export default new HospitalService();
