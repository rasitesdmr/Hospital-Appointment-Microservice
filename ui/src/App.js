import { Route, Routes } from "react-router-dom";
import CityList from "./components/city/CityList";
import Navbar from "./components/navbar/Navbar";
import NavbarRegister from "./components/navbar/NavbarRegister";
import NavbarLogin from "./components/navbar/NavbarLogin";
import HospitalList from "./components/hospital/HospitalList";
import CityCreate from "./components/city/CityCreate";
import AppointmentForm from "./components/appointment/AppointmentForm";

function App() {
  return (
    <div>
      {/* <CityList /> */}

      {/* <NavbarRegister /> */}

      <Routes>
        <Route path="" element={<Navbar />} />
        {/* <Route exact path="/api/login">
          {localStorage.getItem("tc") != null
            ? () => <Navigate to="/" />
            : () => <NavbarLogin />}
        </Route> */}
        <Route path="/api/login" element={<NavbarLogin />} />
        <Route path="/api/register" element={<NavbarRegister />} />
        <Route path="/api/hospitals" element={<HospitalList />} />
        <Route path="/api/cities" element={<CityList />} />
        <Route path="/api/city/create" element={<CityCreate />} />
        <Route path="/api/selection" element={<AppointmentForm />} />
      </Routes>
    </div>
  );
}

export default App;
