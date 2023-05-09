import React, { useState, useEffect } from "react";
import CityService from "../../services/CityService";
import HospitalService from "../../services/HospitalService";
import ClinicService from "../../services/ClinicService";
import DoctorService from "../../services/DoctorService";
import "../../css/appointment.css";

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

  dayjs.locale("tr"); // Locale ayarla
  const localeMap = {
    tr: {
      date: "DD.MM.YYYY",
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
        .then((resp) => setDoctors(resp.data))
        .catch((error) => {
          console.log("Hata alındı", error);
        });
    }
  }, [selectedClinic]);

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
          <option key={doctor.id} value={doctor.fullName}>
            {doctor.fullName}
          </option>
        ))}
      </select>

      <LocalizationProvider
        dateAdapter={AdapterDayjs}
        locale="tr"
        localeMap={localeMap}
      >
        <DemoContainer components={["MobileTimePicker", "DatePicker"]}>
          <DatePicker label="Takvim" defaultValue={dayjs()} />
          <DemoItem>
            <MobileTimePicker
              label="Saat"
              ampm={false}
              defaultValue={dayjs()}
            />
          </DemoItem>
        </DemoContainer>
      </LocalizationProvider>
      <Button variant="contained" color="error">
        Randevu Oluştur
      </Button>
    </div>
  );
};

export default AppointmentForm;
