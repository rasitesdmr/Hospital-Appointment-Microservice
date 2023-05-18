import { Route, Routes } from "react-router-dom";
import CityList from "./components/city/CityList";
import Navbar from "./components/navbar/Navbar";
import NavbarRegister from "./components/navbar/NavbarRegister";
import NavbarLogin from "./components/navbar/NavbarLogin";
import HospitalList from "./components/hospital/HospitalList";
import CityCreate from "./components/city/CityCreate";
import AppointmentForm from "./components/appointment/AppointmentForm";
import About from "./components/profile/About";
import NavbarLoginWithDoctor from "./components/navbar/NavbarLoginWithDoctor";
import AbaoutDoctor from "./components/profile/AbaoutDoctor";
import Home from "./components/navbar/Home";

function App() {
  return (
    <div>
      {/* <CityList /> */}

      {/* <NavbarRegister /> */}

      <Routes>
        <Route path="" element={<Home />} />
        {/* <Route exact path="/api/login">
          {localStorage.getItem("tc") != null
            ? () => <Navigate to="/" />
            : () => <NavbarLogin />}
        </Route> */}
        <Route path="/api/login" element={<NavbarLogin />} />
        <Route path="/api/login/doctor" element={<NavbarLoginWithDoctor />} />
        <Route path="/api/register" element={<NavbarRegister />} />
        <Route path="/api/hospitals" element={<HospitalList />} />
        <Route path="/api/cities" element={<CityList />} />
        <Route path="/api/city/create" element={<CityCreate />} />
        <Route path="/api/selection" element={<AppointmentForm />} />
        <Route path="/api/about" element={<About />} />
        <Route path="/api/about/doctor" element={<AbaoutDoctor />} />
      </Routes>
    </div>
  );
}

export default App;
