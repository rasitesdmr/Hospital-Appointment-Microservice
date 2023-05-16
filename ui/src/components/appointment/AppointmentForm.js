import React, { useState, useEffect } from "react";
import CityService from "../../services/CityService";
import HospitalService from "../../services/HospitalService";
import ClinicService from "../../services/ClinicService";
import DoctorService from "../../services/DoctorService";
import "../../css/appointment.css";
import AppointmentService from "../../services/AppointmentService";

import { DemoContainer, DemoItem } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { MobileTimePicker } from "@mui/x-date-pickers/MobileTimePicker";
import dayjs from "dayjs";
import Button from "@mui/material/Button";
import "dayjs/locale/tr";

const AppointmentForm = () => {
  const [cities, setCities] = useState([]);
  const [selectedCity, setSelectedCity] = useState("");
  const [hospitals, setHospitals] = useState([]);
  const [selectedHospital, setSelectedHospital] = useState("");
  const [clinics, setClinics] = useState([]);
  const [selectedClinic, setSelectedClinic] = useState("");
  const [doctors, setDoctors] = useState([]);
  const [selectedDoctor, setSelectedDoctor] = useState("");
  const [selectedDate, setSelectedDate] = useState(dayjs());
  const [selectedTime, setSelectedTime] = useState(dayjs());

  // console.log(clinics);
  // console.log(cities);

  console.log(
    "ŞEHİR" + " " + cities.find((city) => city.name === selectedCity)?.id
  );
  console.log(
    "HASTANE" +
      " " +
      hospitals.find((hospital) => hospital.name === selectedHospital)?.id
  );
  console.log(
    "KLİNİK" +
      " " +
      clinics.find((clinic) => clinic.name === selectedClinic)?.id
  );
  console.log(
    "DOKTOR" +
      " " +
      doctors.find(
        (doctor) => doctor.firstName + " " + doctor.lastName === selectedDoctor
      )?.identityNumber
  );
  console.log(selectedTime);
  console.log(selectedDate);
  // console.log(selectedTime.format("HH:mm"));
  // console.log(selectedDate.format("YYYY-MM-DD"));

  dayjs.locale("tr"); // Locale ayarla
  const localeMap = {
    tr: {
      date: "YYYY.MM.DD",
      time: "HH:mm",
    },
  };

  // Şehir listesini çekme
  useEffect(() => {
    CityService.getCityList()
      .then((resp) => {
        setCities(resp.data);
        console.log(resp.data);
      })
      .catch((error) => {
        console.log(error.message);
      });
  }, []);

  // Şehire göre hastane çekme
  useEffect(() => {
    if (selectedCity) {
      HospitalService.getHospitalList(selectedCity)
        .then((resp) => {
          setHospitals(resp.data);
          console.log(resp.data);
        })
        .catch((error) => {
          console.log(error.message);
        });
    }
  }, [selectedCity]);

  // Hastaneye göre klinik seçme
  useEffect(() => {
    if (selectedHospital) {
      ClinicService.getClinicList(selectedHospital)
        .then((resp) => setClinics(resp.data))
        .catch((error) => {
          console.log("Hata Alındı ", error);
        });
    }
  }, [selectedHospital]);

  // Kliniğe göre doktor seçme
  useEffect(() => {
    if (selectedClinic) {
      DoctorService.getDoctorList(selectedClinic)
        .then((resp) => {
          console.log(resp.data);
          setDoctors(resp.data);
        })
        .catch((error) => {
          console.log("Hata alındı", error);
        });
    }
  }, [selectedClinic]);

  const appointmentSave = () => {
    const appointment = {
      cityId: cities.find((city) => city.name === selectedCity)?.id,
      hospitalId: hospitals.find(
        (hospital) => hospital.name === selectedHospital
      )?.id,
      clinicId: clinics.find((clinic) => clinic.name === selectedClinic)?.id,
      doctorIdentityNumber: doctors.find(
        (doctor) => doctor.firstName + " " + doctor.lastName === selectedDoctor
      )?.identityNumber,
      appointmentTime: selectedTime.format("HH:mm"),
      appointmentDate: selectedDate.format("YYYY-MM-DD"),
    };

    AppointmentService.createAppointment(appointment)
      .then((resp) => resp.data)
      .then((resp) => {
        console.log(resp);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className="body_font_a">
      <select
        style={{ marginLeft: 100 }}
        className="test"
        aria-invalid="false"
        value={selectedCity}
        onChange={(e) => setSelectedCity(e.target.value)}
      >
        <option value="">ŞEHİR</option>
        {cities.map((city) => (
          <option key={city.id} value={city.name}>
            {city.name}
          </option>
        ))}
      </select>
      <select
        style={{ marginLeft: 100 }}
        className="select_a"
        aria-invalid="false"
        value={selectedHospital}
        onChange={(e) => setSelectedHospital(e.target.value)}
      >
        <option value="">HASTANE</option>
        {hospitals.map((hospital) => (
          <option key={hospital.id} value={hospital.name}>
            {hospital.name}
          </option>
        ))}
      </select>

      <select
        style={{ marginLeft: 100 }}
        className="select_b"
        aria-invalid="false"
        value={selectedClinic}
        onChange={(e) => setSelectedClinic(e.target.value)}
      >
        <option value="">KLİNİK</option>
        {clinics.map((clinic) => (
          <option key={clinic.id} value={clinic.name}>
            {clinic.name}
          </option>
        ))}
      </select>

      <select
        style={{ marginLeft: 100 }}
        className="select_d"
        aria-invalid="false"
        value={selectedDoctor}
        onChange={(e) => setSelectedDoctor(e.target.value)}
      >
        <option value="">DOKTOR</option>
        {doctors.map((doctor) => (
          <option
            key={doctor.id}
            value={doctor.firstName + " " + doctor.lastName}
          >
            {doctor.firstName + " " + doctor.lastName}
          </option>
        ))}
      </select>

      <LocalizationProvider
        dateAdapter={AdapterDayjs}
        locale="tr"
        localeMap={localeMap}
      >
        <DemoContainer components={["MobileTimePicker", "DatePicker"]}>
          <DatePicker
            label="Takvim"
            value={selectedDate}
            onChange={(e) => setSelectedDate(e)}
          />
          <DemoItem>
            <MobileTimePicker
              label="Saat"
              ampm={false}
              value={selectedTime}
              onChange={(e) => setSelectedTime(e)}
            />
          </DemoItem>
        </DemoContainer>
      </LocalizationProvider>
      <Button
        onClick={(e) => appointmentSave()}
        variant="contained"
        color="error"
      >
        Randevu Oluştur
      </Button>
    </div>
  );
};

export default AppointmentForm;
