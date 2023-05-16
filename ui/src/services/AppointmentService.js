import axios from "axios";
import AuthService from "./AuthService";

const base_url = "http://93.177.102.198:9999/proxy-appointment";

class AppointmentService {
  // createAppointment(
  //   selectedCity,
  //   selectedHospital,
  //   selectedClinic,
  //   selectedDoctor,
  //   selectedTime,
  //   selectedDate
  // ) {
  //   return axios.post(`${base_url}/appointmentMakingProcess`, {
  //     headers: {
  //       Authorization: `Bearer ${AuthService.getToken()}`,
  //     },
  //     params: {
  //       cityId: selectedCity,
  //       hospitalId: selectedHospital,
  //       clinicId: selectedClinic,
  //       doctorIdentityNumber: selectedDoctor,
  //       appointmentTime: selectedTime,
  //       appointmentDate: selectedDate,
  //     },
  //   });
  // }

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
}
export default new AppointmentService();
