import axios from "axios";
import AuthService from "./AuthService";

const doctor_url = "http://93.177.102.198:9999/proxy-doctor";

class DoctorService {
  getDoctorList(selectedClinic) {
    return axios.get(`${doctor_url}/getDoctorListByClinicName`, {
      headers: {
        Authorization: `Bearer ${AuthService.getToken()}`,
      },
      params: {
        clinicName: selectedClinic,
      },
    });
  }
}
export default new DoctorService();
