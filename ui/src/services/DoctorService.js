import axios from "axios";
import AuthService from "./AuthService";

const doctor_url = "http://34.125.26.36:9999/proxy-appointment";

class DoctorService {
  getDoctorList(selectedClinic) {
    return axios.get(`${doctor_url}/getDoctorsByClinicName`, {
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
