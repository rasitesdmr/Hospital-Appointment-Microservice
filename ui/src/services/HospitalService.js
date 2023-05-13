import axios from "axios";
import AuthService from "./AuthService";

const hospital_url = "http://93.177.102.198:9999/proxy-hospital";

class HospitalService {
  getHospitalList(selectedCity) {
    return axios.get(`${hospital_url}/getHospitalListByCityName`, {
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
