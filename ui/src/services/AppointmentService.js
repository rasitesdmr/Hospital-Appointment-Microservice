import axios from "axios";
import AuthService from "./AuthService";

const base_url = "http://93.177.102.198:9999/proxy-appointment";

class AppointmentService {
  createAppointment(appointment) {
    return axios.post(
      `${base_url}/appointmentMakingProcess`,
      {},
      {
        headers: {
          Authorization: `Bearer ${AuthService.getToken()}`,
        },
        params: appointment,
      }
    );
  }
  /* Randevuları Listelediğimiz metod URL kısmında
   proxy-appiontment olmadığı için onu
ProfilService.js altında yazdım */

  deleteAppointment(appointmentId) {
    const url = `${base_url}/appointmentRemove?appointmentId=${appointmentId}`;
    return axios.delete(url, {
      headers: {
        Authorization: `Bearer ${AuthService.getToken()}`,
      },
    });
  }
}
export default new AppointmentService();
